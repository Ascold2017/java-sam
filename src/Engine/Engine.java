package Engine;
import Engine.LoopEngine.Loop;
import Engine.LoopEngine.LoopEngine;
import Engine.FlightObject.BaseFlightObject;
import Engine.FlightObject.Enemy;

import java.util.ArrayList;
import java.util.Objects;
import static Tools.SearchTools.findByProperty;

public class Engine extends LoopEngine {
    ArrayList<BaseFlightObject> flightObjects = new ArrayList<>();

    public Engine() {
        super();
        class LoggerLoop extends Loop {
            ArrayList<BaseFlightObject> flightObjects;
            LoggerLoop(ArrayList<BaseFlightObject> fos) {
                this.flightObjects = fos;
            }
            @Override
            public void handler(double time) {
                System.out.println(this.flightObjects);
            }
        }
        this.addLoop("TestLoop", new LoggerLoop(this.flightObjects));
    }
    public void startMission(Mission[] flightMissions) {
        for(Mission mission : flightMissions) {
            final Enemy enemy = new Enemy(this, mission.identifier, mission.points, mission.rcs, false);
            this.addFlightObject(enemy);
        }
    }
    private void addFlightObject(BaseFlightObject flightObject) {
        class FlightObjectLoop extends Loop {
            final BaseFlightObject flightObject;
            FlightObjectLoop (BaseFlightObject flightObject) {
                this.flightObject = flightObject;
            }
            public void handler(double time) {
                flightObject.update(time);
            }
        }

        this.flightObjects.add(flightObject);
        this.addLoop(flightObject.id, new FlightObjectLoop(flightObject));
    }

    public void removeFlightObject(String id) {
        this.removeLoop(id);
        this.flightObjects.removeIf(fo -> Objects.equals(fo.id, id));
    }

    public BaseFlightObject getFlightObject(String id) {
        return (BaseFlightObject) findByProperty(this.flightObjects, fo -> Objects.equals(fo.id, id));
    }

}