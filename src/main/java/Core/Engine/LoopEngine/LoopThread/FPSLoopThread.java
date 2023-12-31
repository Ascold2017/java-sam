package Core.Engine.LoopEngine.LoopThread;

import Core.Engine.LoopEngine.LoopHandler;

import java.util.Date;


public class FPSLoopThread extends BaseLoopThread {
    private final double fps;
    public FPSLoopThread(String name, LoopHandler loopHandler, double fps) {
        super(name, loopHandler);
        this.fps = fps;
    }
    @Override
    void handle(double delta) {
        if (delta < this.fps / 1000) return;
        this.lastTime = new Date().getTime();
        this.time += delta;
        this.loopHandler.handler(this.time / 1000);

    }
}