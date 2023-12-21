package Engine.FlightObject;

import Engine.Engine;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends BaseFlightObject {
    private final ArrayList<Point> points;
    private int currentPointIndex = 0;
    final double visibilityK;
    final boolean isUnion;
    private boolean isKilled = false;
    private double currentRotation = 0;

    public Enemy(Engine engine, String id, ArrayList<Point> points, double visibilityK, boolean isUnion) {
        super(engine, id);
        this.points = points;
        this.visibilityK = visibilityK;
        this.isUnion = isUnion;
    }

    double getCurrentRotation() {
        return this.currentRotation;
    }

    public void update(double time) {
        super.update(time);
        if (this.currentPointIndex == this.points.size() - 1) {
            this.destroy();
            return;
        }
        final Point prevPoint = this.currentPoint.copy();
        this.currentPoint = this.calcCurrentPoint();
        this.currentRotation = Math.atan2(
                this.currentPoint.y - prevPoint.y,
                this.currentPoint.x - prevPoint.x
        );
    }

    private Point calcCurrentPoint() {
      final Point prevPoint = this.points.get(this.currentPointIndex);
      final Point nextPoint = this.points.get(this.currentPointIndex + 1);
      final double flightTimeToCurrentPoint = this.getFlightTimeBetweenPoints(0, this.currentPointIndex);
      final double flightTimeToNextPoint = this.getFlightTimeBetweenPoints(this.currentPointIndex, this.currentPointIndex + 1);
      final double timeOverCurrentPoint = this.timeInAir - flightTimeToCurrentPoint;
      final double K = timeOverCurrentPoint / flightTimeToNextPoint;
      if (timeOverCurrentPoint >= flightTimeToNextPoint) {
          this.currentPointIndex++;
      }
      return this.getPositionBetweenPoints(prevPoint, nextPoint, K);
    }

    private double getFlightTimeBetweenPoints(int fromIndex, int toIndex) {
      final List<Point> points = this.points.subList(fromIndex, toIndex + 1);
      double timeSum = 0;
      for (int index = 0; index < points.size(); index++) {
          Point currentPoint = points.get(index);
          Point prevPoint = index == 0 ? currentPoint : points.get(index - 1);
          double distance = Math.hypot(currentPoint.x - prevPoint.x, currentPoint.y - prevPoint.y);
          double timeMs = ((distance * 1000) / prevPoint.v) * 1000;
          timeSum += timeMs;
      }
      return  timeSum;
    }

    private Point getPositionBetweenPoints(Point currentPoint, Point nextPoint, double K) {
        return new Point(
                currentPoint.x - (currentPoint.x - nextPoint.x) * K,
                currentPoint.y - (currentPoint.y - nextPoint.y) * K,
                currentPoint.z - (currentPoint.z - nextPoint.z) * K,
                currentPoint.v
        );
    }

    void kill() {
        this.isKilled = true;
        this.destroy();
    }
}
