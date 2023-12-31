import Core.Engine.Engine;
import Core.SAM.SAM;
import Server.WebSocketServer.WebSocketServer;

public class Main {

    public static void main(String[] args) {
        try {
            Engine engine = new Engine();
            SAM sam = new SAM(engine);

            WebSocketServer server = new WebSocketServer(8000);

            engine.addFixedLoop("socketUpdate", (double time) -> {
                server.broadcastMessage();
            }, 2000);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
