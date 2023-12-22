package Engine.FlightObject;

import Engine.Engine;

public class BaseFlightObject {
    public final String id;
    public final double visibilityK;
    protected Point currentPoint = new Point();
    protected double currentRotation = 0;
    protected boolean isDestroyed = false;
    protected double timeInAir = 0;
    protected final Engine engine;


    BaseFlightObject(Engine engine, String id, double visibilityK) {
        this.id = id;
        this.engine = engine;
        this.visibilityK = visibilityK;
    }
    BaseFlightObject(Engine engine, String id) {
        this.id = id;
        this.engine = engine;
        this.visibilityK = 1;
    }

    public Point getCurrentPoint() {
        return this.currentPoint.copy();
    }
    public double getCurrentRotation() {
        return this.currentRotation;
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