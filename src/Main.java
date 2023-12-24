import DesktopUI.DesktopUI;
import Engine.Engine;
import Engine.FlightObject.Point;
import Engine.Mission;
import SAM.SAM;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private Engine engine;
    private SAM sam;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initEngine();
        new DesktopUI(stage, this.engine, this.sam);
    }

    private void initEngine() {
        this.engine = new Engine();
        this.sam = new SAM(this.engine);
        Point[] points = {new Point(-80, -80, 50, 10), new Point(500, 50, 1, 10)};
        Mission[] missions = {
                new Mission("FlightObject01", points, 0.5, 0)
        };

        engine.startMission(missions);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("LAUNCH!!!!");
                sam.launchMissile("FlightObject01");
            }
        };
        new Timer(true).schedule(task, 1000);
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("LAUNCH!!!!");
                sam.launchMissile("FlightObject01");
            }
        };
        new Timer(true).schedule(task2, 2000);
    }


}