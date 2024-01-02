package Server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServer extends Thread {
    private final int port;
    private final ConcurrentHashMap<String, SocketThread> socketThreads = new ConcurrentHashMap<>();
    public WebSocketServer(int port) {
        super("websocketServerThread");
        this.port = port;
    }

    @Override
    public void run() {
        super.run();
        try {
            KillPort.killProcess(this.port);
            this.init(this.port);
        } catch (IOException e) {
            System.out.println(e);
            this.interrupt();
        }
    }


    private void init(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server started at port: " + port);
        while (!isInterrupted()) {
            Socket socket = server.accept();
            SocketThread t = new SocketThread(socket, this.socketThreads);
            t.start();
            this.socketThreads.put(t.getName(), t);
        }

        this.cleanup();
    }

    public void broadcastMessage(String message) {
        this.socketThreads.forEach((key, st) -> {
            try {
                st.sendMessage(message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void cleanup() {
        this.socketThreads.forEach((key, st) -> st.interrupt());
        this.socketThreads.clear();
    }
}
