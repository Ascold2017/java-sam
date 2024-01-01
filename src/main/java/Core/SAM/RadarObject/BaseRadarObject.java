package Core.SAM.RadarObject;

import Core.Engine.FlightObject.Point;
import Core.SAM.SAM_PARAMS;
import com.google.gson.annotations.Expose;


public class BaseRadarObject {
    @Expose
    public final String id;
    @Expose
    public final double distance;
    @Expose
    public final double azimuth;
    @Expose
    public final double elevation;
    @Expose
    public final double radialVelocity;
    @Expose
    public final double velocity;
    @Expose
    public final double height;
    @Expose
    public final double param;
    @Expose
    public final double x;
    @Expose
    public final double y;
    @Expose
    public final double rotation;
    @Expose
    public final double size;
    @Expose
    public final double visibilityK;
    @Expose
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
