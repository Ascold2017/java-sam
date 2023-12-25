package Engine.LoopEngine.LoopThread;

import Engine.LoopEngine.LoopHandler;

import java.util.Date;


public class FixedLoopThread extends BaseLoopThread {
    private final double interval;
    public FixedLoopThread(String name, LoopHandler loopHandler, double interval) {
        super(name, loopHandler);
        this.interval = interval;
    }
    @Override
    void handle(double delta) {
        this.lastTime = new Date().getTime();

        this.time += delta;
        if (this.time >= this.interval) {
            this.loopHandler.handler(this.time / 1000);
            this.time = 0;
        }

    }
}
