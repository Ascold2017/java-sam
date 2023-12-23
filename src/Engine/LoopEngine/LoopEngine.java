package Engine.LoopEngine;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

class InfiniteLoopThread extends Thread {
    final LoopEngineRes res;
    private double lastTime = 0;

    InfiniteLoopThread(LoopEngineRes res) {
        super("InfiniteLoopThread");
        this.res = res;
        this.lastTime = new Date().getTime();
    }

    @Override
    public void run() {
        synchronized (res) {
            while (true) {
                this.update(res);
            }
        }
    }

    void update(LoopEngineRes res) {
        if (res.isStop()) return;

        double delta = ((double) new Date().getTime() - this.lastTime) * res.acceleration();
        int maxDelta = 100;
        int fps = 10;
        if (delta > maxDelta) delta = maxDelta;

        if (delta < (double) 1000 / fps) return;

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

record LoopEngineRes(CopyOnWriteArrayList<Loop> loops, CopyOnWriteArrayList<FixedLoop> fixedLoops, boolean isStop, double acceleration) { }

public class LoopEngine {
    private final InfiniteLoopThread infiniteLoopThread;
    protected CopyOnWriteArrayList<Loop> loops = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<FixedLoop> fixedLoops = new CopyOnWriteArrayList<>();
    protected boolean isStop = false;
    protected double acceleration = 1;

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
        this.loops.add(new Loop(name, loopHandler));
    }

    protected void removeLoop(String name) {
        this.loops.removeIf(l -> Objects.equals(l.name, name));
    }

    public void addFixedLoop(String name, LoopHandler loopHandler, double interval) {
        this.fixedLoops.add(new FixedLoop(name, loopHandler, interval));
    }

    public void removeFixedLoop(String name) {
        this.fixedLoops.removeIf(l -> Objects.equals(l.name, name));
    }
}


