package Engine.FlightObject;

import Engine.Engine;

public class BaseFlightObject {
    public final String id;
    protected Point currentPoint = new Point();
    protected boolean isDestroyed = false;
    protected double timeInAir = 0;
    protected final Engine engine;

    BaseFlightObject(Engine engine, String id) {
        this.id = id;
        this.engine = engine;
    }

    Point getCurrentPoint() {
        return this.currentPoint.copy();
    }

    public void update(double time) {
        this.timeInAir = time;
    }

    void destroy() {
        this.engine.removeFlightObject(this.id);
    }

    @Override
    public String toString() {
        return this.id + "|" + this.getCurrentPoint() + "|" + this.timeInAir + "|" + this.isDestroyed;
    }
}