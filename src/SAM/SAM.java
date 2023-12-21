package SAM;

import Engine.Engine;
import Engine.FlightObject.Enemy;
import Engine.FlightObject.Missile;
import Engine.LoopEngine.Loop;

import java.util.ArrayList;

class RecalculateTargetsLoop extends Loop {
    final Engine engine;
    RecalculateTargetsLoop(Engine engine) {
        this.engine = engine;
    }
    public void handler(double time) {
            /*
            this.recognizedFlightObjects = this.sam.recaclulateRecognizedFlightObjects(this.flightObjects.filter(fo => fo instanceof Enemy) as Enemy[]);
            this.launchedMissiles = this.flightObjects.filter(fo => fo instanceof Missile)
        .map(m => ({
                    identifier: m.name,
                    x: m.getCurrentPoint().x,
                    y: m.getCurrentPoint().y,
                    z: m.getCurrentPoint().z,
                    velocity: m.getCurrentPoint().v
        }));
*/
        // System.out.println(this.engine.flightObjects);
        // this.engine.eventListener.handler("update", this.engine.recognizedFlightObjects, this.engine.launchedMissiles);
    }
}

public class SAM {
    final Engine engine;
    private boolean isEnabled = false;
    private ArrayList<Enemy> recognizedFlightObjects = new ArrayList<>();
    private ArrayList<Missile> launchedMissiles = new ArrayList<>();
    SAM(Engine engine) {
        this.engine = engine;
    }


    void setIsEnabled(boolean value) {
        this.isEnabled = value;
    }


/*
    void launchMissile(String targetId) {
        final BaseFlightObject target = findByProperty(this.flightObjects, f -> f.id.equals(targetId));
        if (!target) return;
        final Missile missile = new Missile(this, target);
        //Sounds.missileStart();
        this.addFlightObject(missile);
    }

 */

    /*
    String getTargetOnAzimutAndElevation(double azimuth, double elevation) {


        final Enemy flightObject =  findByProperty(recognizedFlightObjects, fo -> {
            return  (Math.abs(fo.azimuth - azimuth) <= SAM_PARAMS.RADAR_AZIMUT_DETECT_ACCURACY / 2) &&
                    (Math.abs(elevation - fo.elevation) <= SAM_PARAMS.RADAR_ELEVATION_DETECT_ACCURACY / 2);
        });
        return flightObject != null ? flightObject.identifier : null;
    }

    double getTargetOnAzimutElevationAndDistance(double azimuth, double elevation, double distance) {

        final String id = this.getTargetOnAzimutAndElevation(azimuth, elevation);
        if (id == null) return 0;
        final Enemy fo = (Enemy) findByProperty(this.recognizedFlightObjects, f -> f.id.equals(id));
        return Math.abs(fo.distance - distance) <= SAM_PARAMS.RADAR_DISTANCE_DETECT_ACCURACY / 2 ? fo.id : null;

        return 0;
    }

     */
}
