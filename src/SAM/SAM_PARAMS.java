package SAM;

public record SAM_PARAMS() {
    public static final double RADAR_HEIGHT = 25; // 25 meters
    public static final double MIN_ELEVATION = -5 * (Math.PI / 180);
    public static final double MAX_ELEVATION =  50 * (Math.PI / 180);
    public static final double MAX_DISTANCE =  50;
    public static final double MIN_CAPTURE_RANGE =  2;
    public static final double RADAR_AZIMUT_DETECT_ACCURACY = 4 * (Math.PI / 180);
    public static final double RADAR_ELEVATION_DETECT_ACCURACY = 18 * (Math.PI / 180);
    public static final double RADAR_DISTANCE_DETECT_ACCURACY = 0.1;
    public static final double RADAR_SPOT_AZIMUT_GAIN = 1000;
    public static final double RADAR_DISTANCE_WINDOW = 4; // 4 km
    public static final double TARGET_RADAR_RAY_WIDTH = 4 * (Math.PI / 180);
    public static final double TARGET_RADAR_RAY_HEIGHT = 18 * (Math.PI / 180);
    public static final double RADAR_UPDATE_INTERVAL = 2000;
    public static final double MISSILE_VELOCITY = 20;
    public static final double MISSILE_MAX_DISTANCE = 100;
    public static final double MISSILE_KILL_RADIUS = 1.5;
    public static final double MISSILE_MAX_DELTA_ROTATION = 1;
    public static final double BIP_SIDE = 400;
    public static final double DESIGNATION_ANGLE_ACCURACY = 1 * (Math.PI / 180);
    public static final double DESIGNATION_DISTANCE_ACCURACY = 1;
}
