import Engine.*;
import Engine.FlightObject.Point;
import SAM.SAM;

import java.util.Timer;
import java.util.TimerTask;

public class Program {
    public static void main(String[] args) {
        System.out.println("_____SAM_____");
        Engine engine = new Engine();
        Point[] points = {new Point(-80, -80, 50, 10), new Point(80, 80, 1, 10) };
        Mission[] missions = {
                new Mission("FlightObject01", points, 0.5, 0)
        };

        engine.startMission(missions);

        SAM sam = new SAM(engine);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("LAUNCH!!!!");
                sam.launchMissile("FlightObject01");
            }
        };
        new Timer(true).schedule(task,  1000);




    }
}
