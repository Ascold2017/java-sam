package Core.SAM.RadarObject;

import Core.Engine.FlightObject.Point;
import Core.SAM.SAM_PARAMS;

public class SnowRadarObject extends BaseRadarObject {
    public SnowRadarObject() {
        super(new BaseRadarObjectRes(
                Math.random() + "",
                new Point(
                        -(SAM_PARAMS.MAX_DISTANCE/2) + SAM_PARAMS.MAX_DISTANCE * Math.random(),
                        -(SAM_PARAMS.MAX_DISTANCE/2) + SAM_PARAMS.MAX_DISTANCE * Math.random(),
                        Math.random() * 100,
                        Math.random() * 200
                        ),
                2 * Math.PI * Math.random(),
                Math.random()
        ));
    }
}
