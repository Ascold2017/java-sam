package Engine.LoopEngine;

class FixedLoop extends Loop {
    final double interval;
    FixedLoop(String name, LoopHandler handler, double interval) {
        super(name, handler);
        this.interval = interval;
    }
}