package Core.SAM;

import Core.Engine.FlightObject.Missile;
import Core.SAM.RadarObject.DetectedRadarObject;
import com.google.gson.annotations.Expose;

class MissileChannel {
    @Expose
    public int id;

    @Expose
    public boolean isBusy = false;

    public DetectedRadarObject target = null;

    public Missile missile = null;

    MissileChannel(int id) {
        this.id = id;
    }


    public void set(DetectedRadarObject target, Missile missile) {
        this.target = target;
        this.missile = missile;
        this.isBusy = true;
    }

    public void reset() {
        this.target = null;
        this.missile.destroy();
        this.missile = null;
        this.isBusy = false;
    }
}

