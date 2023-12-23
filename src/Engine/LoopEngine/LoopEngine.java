package Engine.LoopEngine;

import java.util.*;

class InfiniteLoopThread extends Thread {
    final LoopEngineRes res;
    private final int fps = 10;
    private final int maxDelta = 100;
    private double lastTime = 0;

    InfiniteLoopThread(LoopEngineRes res) {
        super("InfiniteLoopThread");
        this.res = res;
        this.lastTime = new Date().getTime();
    }
    @Override
    public void run() {
        synchronized (res) {
            try {
                while (true) {
                    this.update(res);
                }
            } catch (Exception ex) {
                System.out.println("EXCEPTION IN INFINITE LOOP: " + ex);
            }
        }
    }

     void update(LoopEngineRes res) {
        if (res.isStop() || isInterrupted()) return;

        double delta = ((double) new Date().getTime() - this.lastTime) * res.acceleration();
        if (delta > this.maxDelta) delta = this.maxDelta;
        if (delta < (double) 1000 / this.fps) return;

        this.lastTime = new Date().getTime();
        for (Loop loop : res.loops()) {
            if (!loop.isPaused) {
                loop.time += delta;
                loop.lHandler.handler(loop.time / 1000);
            }
        }

        for (FixedLoop loop : res.fixedLoops()) {
            if (!loop.isPaused) {
                loop.time += delta;
                if (loop.time >= loop.interval) {
                    loop.lHandler.handler(loop.time / 1000);
                    loop.time = 0;
                }
            }
        }
    }
}

record LoopEngineRes (ArrayList<Loop> loops, ArrayList<FixedLoop> fixedLoops, boolean isStop, double acceleration) { };
public class LoopEngine {
    protected ArrayList<Loop> loops = new ArrayList<>();
    protected ArrayList<FixedLoop> fixedLoops = new ArrayList<>();

    protected boolean isStop = false;
    protected double acceleration = 1;
    private final InfiniteLoopThread infiniteLoopThread;

    public LoopEngine() {
        this.infiniteLoopThread = new InfiniteLoopThread(
                new LoopEngineRes(this.loops, this.fixedLoops, this.isStop, this.acceleration)
        );
        this.infiniteLoopThread.start();
    }

    public void setAcceleration(double acc) {
        this.acceleration = acc;
        this.isStop = acc <= 0;
    }

    public void addLoop(String name, LoopHandler loopHandler) {
        try {
            loops.add(new Loop(name, loopHandler));
            this.loops.add(new Loop(name, loopHandler));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void removeLoop(String name) {
        try {
            this.loops.removeIf(l -> Objects.equals(l.name, name));
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void addFixedLoop(String name, LoopHandler loopHandler, double interval) {
        final ArrayList<FixedLoop> loops = new ArrayList<>(this.fixedLoops);
        loops.add(new FixedLoop(name, loopHandler, interval));
        this.fixedLoops = loops;
    }
    public void removeFixedLoop(String name) {

        this.fixedLoops = new ArrayList<FixedLoop>(this.fixedLoops.stream().filter(l -> !Objects.equals(l.name, name)).toList());
    }
}


