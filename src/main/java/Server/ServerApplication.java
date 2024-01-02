package Server;

import Core.Engine.Engine;
import Core.Engine.FlightObject.Point;
import Core.Engine.Mission;
import Core.SAM.SAM;
import Core.SAM.SAM_PARAMS;
import Server.HttpServer.WebHttpServer;
import Server.WebSocketServer.WebSocketServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class ServerApplication {
    Engine engine = new Engine();
    SAM sam = new SAM(this.engine);
    WebSocketServer webSocketServer;
    WebHttpServer webHttpServer;

    Gson annotationGson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    Gson simpleGson = new Gson();

    public ServerApplication () {
        this.webHttpServer = new WebHttpServer(8001);
        this.webSocketServer = new WebSocketServer(8000);
        this.webSocketServer.start();
        this.initHttpEndpoints();



        String selectedTargetIdsJson = this.simpleGson.toJson(this.sam.getSelectedObjectIds());
        String missileChannelsJson = this.annotationGson.toJson(this.sam.getMissileChannels());
        this.webSocketServer.broadcastMessage("SELECTED_TARGET_IDS_UPDATE|" + selectedTargetIdsJson);
        this.webSocketServer.broadcastMessage("MISSILE_CHANNELS_UPDATE|" + missileChannelsJson);
        this.webSocketServer.broadcastMessage("MISSILES_LEFT_UPDATE|" + this.sam.getMissilesLeft());

        this.engine.addFixedLoop("socketFixedUpdate", (double time) -> {
            String radarObjectsJson = this.annotationGson.toJson(this.sam.getRadarObjects());
            this.webSocketServer.broadcastMessage("RADAR_OBJECTS_UPDATE|" + radarObjectsJson);

        }, 2000);
    }

    private void initHttpEndpoints() {
        this.webHttpServer.addEndpoint("/enabled", payload -> {
            this.sam.setIsEnabled(Objects.equals(payload, "true"));
            return "";
        });

        this.webHttpServer.addEndpoint("/start", payload -> {
            Point[] points = {
                    new Point(-22400, -32800, 500, 13.9),
                    new Point(-20800, 26400, 500, 13.9),
                    new Point(-22400, 20000, 0, 13.9)
            };
            Mission[] missions = {new Mission("Test-1", points, 0.5, 0)};
            engine.startMission(missions);
            return "";
        });

        this.webHttpServer.addEndpoint("/settings", payload -> {
            return "";
        });

        this.webHttpServer.addEndpoint("/select-target", payload -> {
            this.sam.selectTarget(payload);
            return "";
        });
        this.webHttpServer.addEndpoint("/unselect-target", payload -> {
            this.sam.unselectTarget(payload);
            return "";
        });
        this.webHttpServer.addEndpoint("/reset-targets", payload -> {
            this.sam.resetTargets();
            return "";
        });

        this.webHttpServer.addEndpoint("/reset-target", payload -> {
            // TODO
            return "";
        });

        this.webHttpServer.addEndpoint("/launch-missile", payload -> {
            // this.sam.launchMissile();
            return "";
        });

        this.webHttpServer.addEndpoint("/reset-missile", payload -> {
            this.sam.resetMissile(Integer.parseInt(payload));
            return "";
        });
    }
}
