package Engine;
import Engine.FlightObject.Enemy;
import Engine.FlightObject.Missile;

import java.util.ArrayList;
public abstract class EventListener {
    public abstract void handler(String name, ArrayList<Enemy> targets, ArrayList<Missile> missiles);
}
