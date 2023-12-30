import Core.Engine.*;
import Core.Engine.FlightObject.GuidanceMethod;
import Core.Engine.FlightObject.Point;
import Core.SAM.SAM;
import WebSocketServer.WebSocketServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.tyrus.server.Server;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("_____SAM_____");
        Engine engine = new Engine();
        SAM sam = new SAM(engine);
    }
}
        /*
        Point[] points = {new Point(-80, -80, 50, 10), new Point(80, 80, 1, 10) };
        Mission[] missions = {
                new Mission("FlightObject01", points, 0.5, 0)
        };

        engine.startMission(missions);



        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sam.selectTarget("FlightObject01");
                System.out.println("LAUNCH!!!!");
                sam.launchMissile("FlightObject01", 0, GuidanceMethod.ThreePoint);
            }
        };
        new Timer(true).schedule(task,  1000);
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                sam.selectTarget("FlightObject01");
                System.out.println("LAUNCH!!!!");
                sam.launchMissile("FlightObject01", 1, GuidanceMethod.ThreePoint);
            }
        };
        new Timer(true).schedule(task2,  2000);

         */

