package Engine.LoopEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class LoopEngine {
    protected ArrayList<Loop> loops = new ArrayList<>();
    protected ArrayList<FixedLoop> fixedLoops = new ArrayList<>();
    protected double maxDelta = 100; // ms
    protected double fps = 10;
    protected double lastTime;
    protected boolean isStop = false;
    protected double acceleration = 1;

    public LoopEngine() {
        this.lastTime = new Date().getTime();
        this.infiniteLoop();
    }

    void infiniteLoop() {
        Runnable r = () -> {
            while (true) {
                this.update();
            }
        };
        Thread t = new Thread(r, "LoopThread");
        t.start();
    }

    public void setAcceleration(double acc) {
        this.acceleration = acc;
        this.isStop = acc <= 0;
    }

    public void addLoop(String name, LoopHandler loopHandler) {
        final ArrayList<Loop> loops = new ArrayList<>(this.loops);
        loops.add(new Loop(name, loopHandler));
        this.loops = loops;
    }

    public void addFixedLoop(String name, LoopHandler loopHandler, double interval) {
        final ArrayList<FixedLoop> loops = new ArrayList<>(this.fixedLoops);
        loops.add(new FixedLoop(name, loopHandler, interval));
        this.fixedLoops = loops;
    }

    protected void removeLoop(String name) {

        this.loops = new ArrayList<Loop>(this.loops.stream().filter(l -> !Objects.equals(l.name, name)).toList());
    }

    public void removeFixedLoop(String name) {

        this.fixedLoops = new ArrayList<FixedLoop>(this.fixedLoops.stream().filter(l -> !Objects.equals(l.name, name)).toList());
    }

    private void update() {
        if (this.isStop) return;
        double delta = ((double) new Date().getTime() - this.lastTime) * this.acceleration;
        if (delta > this.maxDelta) delta = this.maxDelta;
        if (delta < 1000 / this.fps) return;

        this.lastTime = new Date().getTime();


        for (Loop loop : this.loops) {
            if (!loop.isPaused) {
                loop.time += delta;
                loop.lHandler.handler(loop.time / 1000);
            }
        }
        for (FixedLoop loop : this.fixedLoops) {
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


