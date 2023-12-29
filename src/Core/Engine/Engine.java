package Core.Engine;
import Core.Engine.LoopEngine.LoopEngine;
import Core.Engine.FlightObject.BaseFlightObject;
import Core.Engine.FlightObject.Enemy;
import Core.Engine.LoopEngine.LoopHandler;

import java.util.ArrayList;
import java.util.Objects;

public class Engine extends LoopEngine {
    ArrayList<BaseFlightObject> flightObjects = new ArrayList<>();

    public Engine() {
        super();
    }
    public void startMission(Mission[] flightMissions) {
        for(Mission mission : flightMissions) {
            final Enemy enemy = new Enemy(this, mission.identifier, mission.points, mission.rcs, false);
            this.addFlightObject(enemy);
        }
    }
    public void addFlightObject(BaseFlightObject flightObject) {

        LoopHandler flightObjectHandler = flightObject::update;

        this.flightObjects.add(flightObject);
        this.addFPSLoop(flightObject.id, flightObjectHandler, 100);
    }

    public void removeFlightObject(String id) {
        this.removeLoop(id);
        this.flightObjects.removeIf(fo -> Objects.equals(fo.id, id));
    }


    public ArrayList<BaseFlightObject> getFlightObjects() {
        return this.flightObjects;
    }

}