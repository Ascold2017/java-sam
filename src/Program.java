import Core.Engine.*;
import Core.Engine.FlightObject.GuidanceMethod;
import Core.Engine.FlightObject.Point;
import Core.SAM.SAM;

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
                sam.launchMissile("FlightObject01", 0, GuidanceMethod.ThreePoint);
            }
        };
        new Timer(true).schedule(task,  1000);
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("LAUNCH!!!!");
                sam.launchMissile("FlightObject01", 1, GuidanceMethod.ThreePoint);
            }
        };
        new Timer(true).schedule(task2,  2000);
    }
}
