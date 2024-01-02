package Server.HttpServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WebHttpServer {
    private final int port;
    private HttpServer server;
    public WebHttpServer (int port) {
        this.port = port;
        this.init();
    }

    private void init() {
        try {
            this.server = HttpServer.create(new InetSocketAddress("localhost", this.port), 0);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

            this.server.setExecutor(threadPoolExecutor);
            this.server.start();
            System.out.println("Http server started at port: " + this.port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addEndpoint(String path, RequestHandler handler) {
        this.server.createContext(path, new WebHttpHandler(handler));
    }
}

