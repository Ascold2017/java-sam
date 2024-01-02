package Server.HttpServer;

public interface RequestHandler {
    String handle(String payload);
}
