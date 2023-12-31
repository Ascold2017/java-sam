package Core.SAM.RadarObject;

import Core.Engine.FlightObject.BaseFlightObject;

public class UndetectedRadarObject extends BaseRadarObject {
    public UndetectedRadarObject(BaseFlightObject flightObject) {
        super(new BaseRadarObjectRes(flightObject.id, flightObject.getCurrentPoint(), flightObject.getCurrentRotation(), flightObject.visibilityK));
    }
}
