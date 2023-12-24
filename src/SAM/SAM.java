package SAM;

import Engine.Engine;
import Engine.FlightObject.BaseFlightObject;
import Engine.FlightObject.Enemy;
import Engine.FlightObject.Missile;
import Engine.LoopEngine.LoopHandler;

import java.util.ArrayList;
import java.util.List;


public class SAM {
    final Engine engine;
    private final ArrayList<DetectedFlightObject> detectedFlightObjects = new ArrayList<>();
    private final ArrayList<Missile> launchedMissiles = new ArrayList<>();
    private boolean isEnabled = true;

    public SAM(Engine engine) {
        this.engine = engine;
        LoopHandler updateRadar = this::updateRadar;
        this.engine.addLoop("updateRadar", updateRadar);
    }


    void setIsEnabled(boolean value) {
        this.isEnabled = value;
    }

    void updateRadar(double time) {

        this.detectedFlightObjects.clear();
        if (!this.isEnabled) return;
        ArrayList<BaseFlightObject> flightObjects = this.engine.getFlightObjects();
        for (BaseFlightObject flightObject : flightObjects) {
            detectedFlightObjects.add(new DetectedFlightObject(flightObject));
        }
        // this.printFlightObjects();
    }

    void printFlightObjects() {
        System.out.println("DetectedFlightObjects: " + this.detectedFlightObjects.size());
        for (DetectedFlightObject fo : this.detectedFlightObjects) {
            System.out.println(fo);
            System.out.println("_________________________________");
        }
    }

    public List<DetectedFlightObject> getDetectedFlightObjects() {
        return this.detectedFlightObjects.stream().toList();
    }



    public void launchMissile(String targetId) {
        final List<DetectedFlightObject> target = this.detectedFlightObjects.stream().filter(f -> f.id.equals(targetId)).toList();
        System.out.println(target);
        if (target.isEmpty()) return;
        if (target.getFirst().isMissile) return;
        final Missile missile = new Missile(this.engine, (Enemy) target.getFirst().getFlightObject());
        System.out.println(missile);
        //Sounds.missileStart();
        this.engine.addFlightObject(missile);
    }



}
