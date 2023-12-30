package WebSocketServer;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
@ServerEndpoint(value = "/demoApp")
public class WebSocketServerEndpoint {
    @OnOpen
    public void onOpen (Session session) {
        System.out.println("[SERVER]: Handshake successful!!!!! - Connected!!!!! - Session ID: " + session.getId());
    }
    @OnMessage
    public String onMessage (String message, Session session) {
        if (message.equalsIgnoreCase("terminate")) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Successfully session closed....."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
    @OnClose
    public void onClose (Session session, CloseReason closeReason) {
        System.out.println("[SERVER]: Session " + session.getId() + " closed, because " + closeReason);
    }
}