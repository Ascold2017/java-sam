package Engine.LoopEngine.LoopThread;

import Engine.LoopEngine.LoopHandler;

import java.util.Date;


public class FPSLoopThread extends BaseLoopThread {
    private final double fps = 10;
    public FPSLoopThread(String name, LoopHandler loopHandler) {
        super(name, loopHandler);
    }
    @Override
    void handle(double delta) {
        if (delta < this.fps / 1000) return;
        this.lastTime = new Date().getTime();
        this.time += delta;
        this.loopHandler.handler(this.time / 1000);

    }
}
