import Engine.*;
import Engine.FlightObject.Point;
import Engine.LoopEngine.Loop;

import java.util.Date;



public class Program {
    public static void main(String[] args) {
        System.out.println("_____SAM_____");
        Engine engine = new Engine();
        Point[] points = {new Point(0, 0, 0, 100), new Point(100, 100, 100, 100) };
        Mission[] missions = {
                new Mission("FlightObject01", points, 0.5, 0)
        };

        engine.startMission(missions);


    }
}
