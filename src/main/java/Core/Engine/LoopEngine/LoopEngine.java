package Core.Engine.LoopEngine;

import Core.Engine.LoopEngine.LoopThread.BaseLoopThread;
import Core.Engine.LoopEngine.LoopThread.FPSLoopThread;
import Core.Engine.LoopEngine.LoopThread.FixedLoopThread;

import java.util.ArrayList;
import java.util.Objects;


public class LoopEngine {
    private final ArrayList<BaseLoopThread> loopThreads = new ArrayList<>();

    public LoopEngine() {}

    public void start() {
        for (BaseLoopThread loopThread : this.loopThreads) {
            loopThread.isStop = false;
            loopThread.acceleration = 1;
        }
    }

    public void stop() {
        for (BaseLoopThread loopThread : this.loopThreads) {
            loopThread.isStop = true;
        }
    }

    public void setAcceleration(double acc) {
        for (BaseLoopThread loopThread : this.loopThreads) {
            loopThread.isStop = acc <= 0;;
            loopThread.acceleration = acc;
        }
    }

    public void addFPSLoop(String name, LoopHandler loopHandler, double fps) {
        FPSLoopThread thread = new FPSLoopThread(name, loopHandler, fps);
        thread.start();
        this.loopThreads.add(thread);
    }
    public void addFixedLoop(String name, LoopHandler loopHandler, double interval) {
        FixedLoopThread thread = new FixedLoopThread(name, loopHandler, interval);
        thread.start();
        this.loopThreads.add(thread);
    }

    public void removeLoop(String name) {
        this.loopThreads.stream().filter(t -> t.getName().equals(name)).forEach(Thread::interrupt);
        this.loopThreads.removeIf(l -> Objects.equals(l.getName(), name));
    }
}