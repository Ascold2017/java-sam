import DesktopUI.*;
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

    private DesktopUI desktopUI;

    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Количество доступных ядер: " + cores);

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initEngine();
        this.desktopUI = new DesktopUI(stage, this.engine, this.sam);
    }

    @Override
    public void stop() {
        this.desktopUI = null;
    }

    private void initEngine() {
        this.engine = new Engine();
        this.sam = new SAM(this.engine);
        Point[] points = {new Point(-80, -80, 50, 5), new Point(500, 50, 1, 0)};
        Mission[] missions = {
                new Mission("FlightObject01", points, 0.5, 0)
        };

        engine.startMission(missions);
        // engine.stop();

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