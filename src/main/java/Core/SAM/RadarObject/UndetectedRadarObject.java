package Core.SAM.RadarObject;

import Core.Engine.FlightObject.BaseFlightObject;
import com.google.gson.annotations.Expose;

public class UndetectedRadarObject extends BaseRadarObject {
    @Expose
    public final String type = "UNDETECTED_RADAR_OBJECT";
    public UndetectedRadarObject(BaseFlightObject flightObject) {
        super(new BaseRadarObjectRes(flightObject.id, flightObject.getCurrentPoint(), flightObject.getCurrentRotation(), flightObject.visibilityK));
    }
}
