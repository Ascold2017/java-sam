package Engine.FlightObject;

import Engine.*;
import SAM.SAM_PARAMS;
import Tools.Vector3D;
import java.util.Date;

public class Missile extends BaseFlightObject {
    private final Enemy target;
    private final double velocity;
    private final double maxDistance;
    private final double killRadius;
    private double traveledDistance = 0;
    public Missile(Engine engine, Enemy target) {
        super(engine, "Missile-" + new Date().getTime());

        this.target = target;
        this.maxDistance = SAM_PARAMS.MISSILE_MAX_DISTANCE;
        this.killRadius = SAM_PARAMS.MISSILE_KILL_RADIUS;
        this.velocity = SAM_PARAMS.MISSILE_VELOCITY;
    }

    @Override
    public void update(double time) {
        final double dTime = (time - this.timeInAir);
        // TODO if (!this.target) return this.destroy();
        super.update(time);
        final double dFlightDistance = dTime * this.velocity;
        this.traveledDistance += dFlightDistance;

        final Vector3D targetVector = new Vector3D(this.target.getCurrentPoint());
        final Vector3D prevMissileVector = new Vector3D(this.currentPoint.copy());
        final double targetDistance = targetVector.sub(prevMissileVector).r();
        final Vector3D currentPosition = this.calcMissilePosition(targetVector, prevMissileVector, targetDistance, dFlightDistance);
        this.currentPoint = new Point(currentPosition.x(), currentPosition.y(), currentPosition.z(), this.velocity);
        if (targetDistance <= this.killRadius) {
            this.target.kill();
            this.destroy();
        }
        if (this.traveledDistance >= this.maxDistance) {
            this.destroy();
        }
    }

    private Vector3D calcMissilePosition(Vector3D targetVector, Vector3D prevMissileVector, double targetDistance, double dFlightDistance) {
        final double distance = Math.min(dFlightDistance, targetDistance);
        // https://qna.habr.com/q/1189118
        final double a = targetVector.dot(targetVector);
        final double b = -2 * prevMissileVector.dot(targetVector);
        final double c = prevMissileVector.dot(prevMissileVector) - Math.pow(distance, 2);
        final double d = Math.pow(b, 2) - 4 * a * c;
        double sqrt = Math.sqrt(d >= 0 ? d : 0);
        final double t1 = (-b - sqrt) / (2 * a);
        final double t2 = (-b + sqrt) / (2 * a);
        return targetVector.scale(Math.max(t1, t2));
    }
}
