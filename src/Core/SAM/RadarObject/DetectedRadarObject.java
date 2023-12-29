package Core.SAM.RadarObject;

import Core.Engine.FlightObject.BaseFlightObject;
import Core.Engine.FlightObject.Missile;

import java.util.Comparator;

public class DetectedRadarObject extends BaseRadarObject {
    public final boolean isMissile;
    private final BaseFlightObject flightObject;
    public DetectedRadarObject(BaseFlightObject flightObject) {
        super(new BaseRadarObjectRes(flightObject.id, flightObject.getCurrentPoint(), flightObject.getCurrentRotation(), flightObject.visibilityK));
        this.flightObject = flightObject;
        this.isMissile = flightObject instanceof Missile;
    }

    public BaseFlightObject getFlightObject() {
        return this.flightObject;
    }

    public static Comparator<BaseFlightObject> sortByVisibilityComparator = (BaseFlightObject enemy1, BaseFlightObject enemy2) -> {
        if (enemy1.visibilityK == enemy2.visibilityK) return 0;
        return enemy1.visibilityK < enemy2.visibilityK ? 1 : -1;
    };
}
