package Core.Engine;

import Core.Engine.FlightObject.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Mission {
    ArrayList<Point> points = new ArrayList<>();
    double rcs;
    double time;
    String identifier;
    // int flightObjectTypeId = 0;
    public Mission(String identifier, Point[] points, double rcs, double time) {
        this.identifier = identifier;
        this.points.addAll(Arrays.asList(points));
        this.rcs = rcs;
        this.time = time;

    }

}
