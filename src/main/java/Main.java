import Core.Engine.Engine;
import Core.Engine.FlightObject.Point;
import Core.Engine.Mission;
import Core.SAM.SAM;
import Server.WebSocketServer.WebSocketServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args) {

        Engine engine = new Engine();
        SAM sam = new SAM(engine);
        sam.setIsEnabled(true);

        Point[] points = {
                new Point(-22400, -32800, 500, 13.9),
                new Point(-20800, 26400, 500, 13.9),
                new Point(-22400, 20000, 0, 13.9)
        };
        Mission[] missions = {new Mission("Test-1", points, 0.05, 0)};
        engine.startMission(missions);


        WebSocketServer server = new WebSocketServer(8000);
        server.start();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Gson gson2 = new Gson();

        String selectedTargetIdsJson = gson2.toJson(sam.getSelectedObjectIds());
        String missileChannelsJson = gson.toJson(sam.getMissileChannels());
        server.broadcastMessage("SELECTED_TARGET_IDS_UPDATE|" + selectedTargetIdsJson);
        server.broadcastMessage("MISSILE_CHANNELS_UPDATE|" + missileChannelsJson);
        server.broadcastMessage("MISSILES_LEFT_UPDATE|" + sam.getMissilesLeft());

        engine.addFixedLoop("socketFixedUpdate", (double time) -> {
            String radarObjectsJson = gson.toJson(sam.getRadarObjects());
            server.broadcastMessage("RADAR_OBJECTS_UPDATE|" + radarObjectsJson);

        }, 2000);
/*
        engine.addFPSLoop("socketFPSUpdate", (double time) -> {
            String selectedTargetIdsJson = gson2.toJson(sam.getSelectedObjectIds());
            String missileChannelsJson = gson.toJson(sam.getMissileChannels());
            server.broadcastMessage("SELECTED_TARGET_IDS_UPDATE|" + selectedTargetIdsJson);
            server.broadcastMessage("MISSILE_CHANNELS_UPDATE|" + missileChannelsJson);
            server.broadcastMessage("MISSILES_LEFT_UPDATE|" + sam.getMissilesLeft());
        }, 40);

 */
    }
}

