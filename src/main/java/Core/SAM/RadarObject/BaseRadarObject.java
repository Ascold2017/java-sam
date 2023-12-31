package Core.SAM.RadarObject;

import Core.Engine.FlightObject.Point;
import Core.SAM.SAM_PARAMS;


public class BaseRadarObject {
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

    protected BaseRadarObject(BaseRadarObjectRes res) {
        this.id = res.id();
        Point currentPoint = res.currentPoint();
        double distance = BaseRadarObject.getDistance(currentPoint);
        this.distance = distance;
        double azimuth = this.getAzimuth(currentPoint);
        this.azimuth = azimuth < 0 ? 2 * Math.PI + azimuth : azimuth;
        this.elevation = this.getTargetElevation(distance, currentPoint.z);
        this.velocity = currentPoint.v;
        this.radialVelocity = this.getRadialVelocity(azimuth, res.currentRotation(), currentPoint);
        this.height = currentPoint.z;
        this.param = this.getTargetParam(azimuth, distance, res.currentRotation());
        this.x = currentPoint.x;
        this.y = currentPoint.y;
        this.rotation = res.currentRotation();
        this.size = 2 * Math.sqrt(res.visibilityK() / Math.PI);
        this.visibilityK = res.visibilityK() > 1 ? 1 : res.visibilityK();

        boolean inAllowedElevation = this.elevation > SAM_PARAMS.MIN_ELEVATION && this.elevation < SAM_PARAMS.MAX_ELEVATION;
        this.isVisible = this.isInVision(distance, currentPoint.z) && inAllowedElevation && distance < SAM_PARAMS.MAX_DISTANCE;

    }

    private boolean isInVision(double distance, double height) {
        return Math.sqrt(2 * 6371009 * SAM_PARAMS.RADAR_HEIGHT) + Math.sqrt(2 * 6371009 * height) > distance;
    }

    public static double getDistance(Point currentPoint) {
        return Math.hypot(currentPoint.x, currentPoint.y);
    }

    private double getAzimuth(Point currentPoint) {
        return Math.atan2(currentPoint.y, currentPoint.x);
    }

    private double getTargetElevation(double distance, double height) {
        final double targetHeightOffset = height - SAM_PARAMS.RADAR_HEIGHT;
        // Vertical angle from SNR to target
        return (targetHeightOffset / distance);
    }

    private double getRadialVelocity(double targetAzimuth, double currentRotation, Point currentPoint) {
        // Angle between azimut to flight object and rotation of flight object
        final double targetAngle = (targetAzimuth > currentRotation
                ? targetAzimuth - currentRotation
                :currentRotation - targetAzimuth) - Math.PI;

        // Radial velocity
        return currentPoint.v * Math.cos(targetAngle);
    }

    private double getTargetParam(double targetAzimuth, double targetDistance, double currentRotation) {
        // Angle between azimut to flight object and rotation of flight object
        final double targetAngle = (targetAzimuth > currentRotation
                ? targetAzimuth - currentRotation
                : currentRotation - targetAzimuth) - Math.PI;
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
                Math.toDegrees(this.azimuth) +
                "|Elevation:" +
                Math.toDegrees(this.elevation) +
                "|Param:" +
                this.param +
                "|Visible:" +
                (this.isVisible ? "+" : "-");
    }
}
