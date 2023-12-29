package Core.Engine.LoopEngine.LoopThread;

import Core.Engine.LoopEngine.LoopHandler;

import java.util.Date;

public class BaseLoopThread extends Thread {
    public boolean isStop = false;
    final LoopHandler loopHandler;
    double lastTime = 0;
    double time = 0;
    public double acceleration = 1;

    public BaseLoopThread(String name, LoopHandler loopHandler) {
        super(name);
        this.loopHandler = loopHandler;
        this.lastTime = new Date().getTime();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            this.update();
        }
    }

    private void update() {
        if (this.isStop) return;

        double delta = ((double) new Date().getTime() - this.lastTime) * this.acceleration;
        int maxDelta = 100;
        if (delta > maxDelta) delta = maxDelta;

        this.handle(delta);
    }

    void handle(double delta) {}
}