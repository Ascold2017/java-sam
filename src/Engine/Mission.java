package Engine;

import Engine.FlightObject.Point;

import java.util.ArrayList;

public class Mission {
    ArrayList<Point> points = new ArrayList<>();
    double rcs = 0;
    double time = 0;
    String identifier = "";
    int flightObjectTypeId = 0;
    public Mission(String identifier, Point[] points, double rcs, double time) {
        this.identifier = identifier;
        for (Point p : points) {
            this.points.add(p);
        }
        this.rcs = rcs;
        this.time = time;

    }

}
