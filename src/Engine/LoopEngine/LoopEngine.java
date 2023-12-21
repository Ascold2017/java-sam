package Engine.LoopEngine;

import java.util.HashMap;
import java.util.Date;

public class LoopEngine {
    protected HashMap<String, Loop> loops = new HashMap<String, Loop>();
    protected HashMap<String, FixedLoop> fixedLoops = new HashMap<String, FixedLoop>();
    protected double maxDelta = 100; // ms
    protected double fps = 10;
    protected double lastTime = 0;
    protected boolean isStop = false;
    protected double acceleration = 1;

    public LoopEngine() {
        this.lastTime = new Date().getTime();
        this.infiniteLoop();
    }

    void infiniteLoop () {
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

    public void addLoop(String name, Loop loop) {
        this.loops.put(name, loop);
    }

    public void addFixedLoop(String name, FixedLoop loop) {
        this.fixedLoops.put(name, loop);
    }
    public void removeLoop(String name) {
        this.loops.remove(name);
    }
    public void removeFixedLoop(String name) {
        this.fixedLoops.remove(name);
    }

    private void update() {
        if (this.isStop) return;
        double delta = ((double) new Date().getTime() - this.lastTime) * this.acceleration;
        if (delta > this.maxDelta) delta = this.maxDelta;
        if (delta < 1000 / this.fps) return;

        this.lastTime = new Date().getTime();

        for (Loop loop : this.loops.values()) {
            if (!loop.isPaused) {
                loop.time += delta;
                loop.handler(loop.time);
            }
        }
        for (FixedLoop loop : this.fixedLoops.values()) {
            if (!loop.isPaused) {
                loop.time += delta;
                if (loop.time >= loop.interval) {
                    loop.handler(loop.time);
                    loop.time = 0;
                }
            }
        }
    }
}


