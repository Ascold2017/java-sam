import Core.Engine.Engine;
import Core.Engine.FlightObject.Point;
import Core.Engine.Mission;
import Core.SAM.SAM;
import Server.WebSocketServer.WebSocketServer;

public class Main {

    public static void main(String[] args) {
        try {
            Engine engine = new Engine();
            SAM sam = new SAM(engine);
            sam.setIsEnabled(true);

            Point[] points = {
                    new Point(-22400, -32800, 500, 13.9),
                    new Point(-20800, 26400, 500, 13.9),
                    new Point(-22400, 20000, 0, 13.9)
            };
            Mission[] missions = { new Mission("Test-1", points, 0.05, 0) };
            engine.startMission(missions);


            WebSocketServer server = new WebSocketServer(8000);

            engine.addFixedLoop("socketUpdate", (double time) -> {
                String message = sam.getRadarObjects().toString();
                System.out.println("MSG" + message);
                server.broadcastMessage(message);
            }, 2000);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
