package Engine.LoopEngine;

class Loop {
    public final String name;
    final LoopHandler lHandler;
    public boolean isPaused = false;
    public double time = 0;
    Loop(String name, LoopHandler handler) {
        this.name = name;
        this.lHandler = handler;
    }
}