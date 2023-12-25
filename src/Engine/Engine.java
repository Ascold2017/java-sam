package Engine;
import Engine.LoopEngine.LoopEngine;
import Engine.FlightObject.BaseFlightObject;
import Engine.FlightObject.Enemy;
import Engine.LoopEngine.LoopHandler;

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
        this.addFPSLoop(flightObject.id, flightObjectHandler);
    }

    public void removeFlightObject(String id) {
        this.removeLoop(id);
        this.flightObjects.removeIf(fo -> Objects.equals(fo.id, id));
    }
    /*
    public List<BaseFlightObject> getFlightObject(String id) {
        return findByProperty(this.flightObjects, fo -> Objects.equals(fo.id, id));
    }
     */

    public ArrayList<BaseFlightObject> getFlightObjects() {
        return new ArrayList<>(this.flightObjects);
    }

    public void printFlightObjects() {
        System.out.println(this.flightObjects);
    }
}