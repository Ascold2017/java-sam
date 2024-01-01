package Core.SAM;

import Core.Engine.FlightObject.Missile;
import Core.SAM.RadarObject.DetectedRadarObject;

class MissileChannel {
    public int id;
    public DetectedRadarObject target = null;
    public Missile missile = null;

    MissileChannel(int id) {
        this.id = id;
    }

    public void set(DetectedRadarObject target, Missile missile) {
        this.target = target;
        this.missile = missile;
    }

    public void reset() {
        this.target = null;
        this.missile.destroy();
        this.missile = null;
    }
}

