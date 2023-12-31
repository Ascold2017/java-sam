package Server.WebSocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebSocketServer extends ServerSocket {
    private final ArrayList<SocketThread> socketThreads = new ArrayList<>();
    public WebSocketServer(int port) throws IOException {
        super(port);

        this.init(port);

        this.close();
    }
    private List<SocketThread> getSocketThreads() {
        return this.socketThreads.stream().toList();
    }
    private void init(int port) throws IOException {
        System.out.println("Server started at port: " + port);
        while (!Thread.interrupted()) {
            Socket clientSocket = this.accept();
            System.out.println(this.getSocketThreads());

            SocketThread st = new SocketThread(clientSocket, (String name) -> {
                this.socketThreads.removeIf(socketThread -> Objects.equals(socketThread.getName(), name));
            });
            this.socketThreads.add(st);
            st.start();
        }
    }

    public void broadcastMessage() {

    }
}
