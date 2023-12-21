package Engine.LoopEngine;

public abstract class Loop {
    public boolean isPaused = false;
    public double time = 0;

    abstract public void handler(double time);
}