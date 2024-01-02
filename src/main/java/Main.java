import Core.Engine.Engine;
import Core.Engine.FlightObject.Point;
import Core.Engine.Mission;
import Core.SAM.SAM;
import Server.HttpServer.WebHttpServer;
import Server.ServerApplication;
import Server.WebSocketServer.WebSocketServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args) {
        new ServerApplication();




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

