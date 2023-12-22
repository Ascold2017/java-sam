package SAM;

import Engine.FlightObject.BaseFlightObject;
import Engine.FlightObject.Missile;
import Engine.FlightObject.Point;

public class DetectedFlightObject {
    public final String id;
    public final double distance;
    public final double azimuth;
    public final double elevation;
    public final double radialVelocity;
    public final double velocity;
    public final double height;
    public final double param;
    public final double x;
    public final double y;
    public final double rotation;
    public final double size;
    public final double visibilityK;
    public final boolean isVisible;

    public final boolean isMissile;
    private final BaseFlightObject flightObject;

    public DetectedFlightObject(BaseFlightObject flightObject) {
        this.flightObject = flightObject;

        this.id = flightObject.id;
        final double distance = this.getDistance();
        this.distance = distance;
        final double azimuth = this.getAzimuth();
        this.azimuth = azimuth < 0 ? 2 * Math.PI + azimuth : azimuth;
        this.elevation = this.getTargetElevation(distance);
        this.velocity = flightObject.getCurrentPoint().v;
        this.radialVelocity = this.getRadialVelocity(azimuth);
        this.height = flightObject.getCurrentPoint().z;
        this.param = this.getTargetParam(azimuth, distance);
        this.x = flightObject.getCurrentPoint().x;
        this.y = flightObject.getCurrentPoint().y;
        this.rotation = flightObject.getCurrentRotation();
        this.size = 2 * Math.sqrt(flightObject.visibilityK / Math.PI) / 1000;
        this.visibilityK = flightObject.visibilityK > 1 ? 1 : flightObject.visibilityK;

        final boolean inAllowedElevation = this.elevation > SAM_PARAMS.MIN_ELEVATION && this.elevation < SAM_PARAMS.MAX_ELEVATION;
        this.isVisible = this.isInVision(distance) && inAllowedElevation && distance < SAM_PARAMS.MAX_DISTANCE;

        this.isMissile = flightObject instanceof Missile;
    }

    public BaseFlightObject getFlightObject() {
        return this.flightObject;
    }

    private boolean isInVision(double distance) {

        final double height = this.flightObject.getCurrentPoint().z;
        return Math.sqrt(2 * 6371009 * SAM_PARAMS.RADAR_HEIGHT) + Math.sqrt(2 * 6371009 * height) > distance;

    }

    private double getDistance() {
        final Point currentPoint = this.flightObject.getCurrentPoint();
        return Math.hypot(currentPoint.x, currentPoint.y);
    }

    private double getAzimuth() {
        final Point currentPoint = this.flightObject.getCurrentPoint();
        return Math.atan2(currentPoint.y, currentPoint.x);
    }

    private double getTargetElevation(double distance) {
        final double targetHeightOffset = this.flightObject.getCurrentPoint().z - SAM_PARAMS.RADAR_HEIGHT;
        // Vertical angle from SNR to target
        return (targetHeightOffset / distance);
    }

    private double getRadialVelocity(double targetAzimuth) {
        // Angle between azimut to flight object and rotation of flight object
        final double targetAngle = (targetAzimuth > this.flightObject.getCurrentRotation()
                ? targetAzimuth - this.flightObject.getCurrentRotation()
                : this.flightObject.getCurrentRotation() - targetAzimuth) - Math.PI;

        // Radial velocity
        return this.flightObject.getCurrentPoint().v * Math.cos(targetAngle);
    }

    private double getTargetParam(double targetAzimuth, double targetDistance) {
        // Angle between azimut to flight object and rotation of flight object
        final double targetAngle = (targetAzimuth > this.flightObject.getCurrentRotation()
                ? targetAzimuth - this.flightObject.getCurrentRotation()
                : this.flightObject.getCurrentRotation() - targetAzimuth) - Math.PI;
        return Math.abs(targetDistance * Math.tan(targetAngle));
    }

    @Override
    public String toString() {
        return "|" +
                this.id +
                "|X:" +
                this.x +
                "|Y:" +
                this.y +
                "|H:" +
                this.height +
                "|V:" +
                this.velocity +
                "|Dist:" +
                this.distance +
                "|Azimuth:" +
                this.azimuth * (180 / Math.PI) +
                "|Elevation:" +
                this.elevation * (180 / Math.PI) +
                "|Param:" +
                this.param +
                "|Visible:" +
                (this.isVisible ? "+" : "-");
    }
}
