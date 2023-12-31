package Server.WebSocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketServer extends Thread {
    private final ConcurrentHashMap<String, SocketThread> socketThreads = new ConcurrentHashMap<>();
    public WebSocketServer(int port) throws IOException {
        super("websocketServer");
        this.init(port);
    }

    private void init(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server started at port: " + port);
        while (true) {
            Socket socket = server.accept();
            SocketThread t = new SocketThread(socket, (name) -> {
                System.out.println(this.socketThreads);
                this.socketThreads.remove(name);
            });
            this.socketThreads.put(t.getName(), t);
        }
    }

    public void broadcastMessage(String message) {
        this.socketThreads.forEach((key, st) -> {
            try {
                st.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
