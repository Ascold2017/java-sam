package Server.WebSocketServer;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketThread extends Thread {
    private final Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private final ConcurrentHashMap<String, SocketThread> socketThreads;
    SocketThread(Socket socket, ConcurrentHashMap<String, SocketThread> socketThreads) {
        super(String.valueOf(socket.getPort()));
        this.socket = socket;
        this.socketThreads = socketThreads;
    }

    @Override
    public void run() {
        super.run();
        try {
            this.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws IOException {
        try {
            this.inputStream = this.socket.getInputStream();
            this.outputStream = this.socket.getOutputStream();
            this.doHandShakeToInitializeWebSocketConnection();
            System.out.println("Client connected: " + this.socket.getPort());
            this.ping();
        } catch (DisconnectException ex) {
            this.socket.close();
            this.interrupt();
            this.socketThreads.remove(this.getName());
        }
    }

    private void ping() throws DisconnectException {
        long intervals = 1000;
        long lastTime = new Date().getTime();
        do {
            if (new Date().getTime() >= lastTime + intervals) {
                lastTime = new Date().getTime();
                try {
                    this.sendMessage("PING|" + threadId());
                } catch (IOException e) {
                    System.out.println("Socket " + this.socket.getPort() + " disconnected");
                    throw new DisconnectException();
                }
            }
        } while (!Thread.interrupted());
    }


    //Source for encoding and decoding:
    //https://stackoverflow.com/questions/8125507/how-can-i-send-and-receive-websocket-messages-on-the-server-side
    private byte[] encode(String mess) {
        byte[] rawData = mess.getBytes();

        int frameCount  = 0;
        byte[] frame = new byte[10];

        frame[0] = (byte) 129;

        if(rawData.length <= 125){
            frame[1] = (byte) rawData.length;
            frameCount = 2;
        }else if(rawData.length >= 126 && rawData.length <= 65535){
            frame[1] = (byte) 126;
            int len = rawData.length;
            frame[2] = (byte)((len >> 8 ) & (byte)255);
            frame[3] = (byte)(len & (byte)255);
            frameCount = 4;
        }else{
            frame[1] = (byte) 127;
            int len = rawData.length;
            frame[2] = (byte)((len >> 56 ) & (byte)255);
            frame[3] = (byte)((len >> 48 ) & (byte)255);
            frame[4] = (byte)((len >> 40 ) & (byte)255);
            frame[5] = (byte)((len >> 32 ) & (byte)255);
            frame[6] = (byte)((len >> 24 ) & (byte)255);
            frame[7] = (byte)((len >> 16 ) & (byte)255);
            frame[8] = (byte)((len >> 8 ) & (byte)255);
            frame[9] = (byte)(len & (byte)255);
            frameCount = 10;
        }

        int bLength = frameCount + rawData.length;

        byte[] reply = new byte[bLength];

        int bLim = 0;
        for(int i=0; i<frameCount;i++){
            reply[bLim] = frame[i];
            bLim++;
        }
        for(int i=0; i<rawData.length;i++){
            reply[bLim] = rawData[i];
            bLim++;
        }

        return reply;
    }

    public void sendMessage(String message) throws IOException {
        this.outputStream.write(this.encode(message));
        this.outputStream.flush();
    }

    private void doHandShakeToInitializeWebSocketConnection() {
        String data = new Scanner(this.inputStream, StandardCharsets.UTF_8).useDelimiter("\\r\\n\\r\\n").next();

        Matcher get = Pattern.compile("^GET").matcher(data);

        if (get.find()) {
            Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
            match.find();

            byte[] response = null;
            try {
                response = ("HTTP/1.1 101 Switching Protocols\r\n"
                        + "Connection: Upgrade\r\n"
                        + "Upgrade: websocket\r\n"
                        + "Sec-WebSocket-Accept: "
                        + DatatypeConverter.printBase64Binary(
                        MessageDigest
                                .getInstance("SHA-1")
                                .digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                                        .getBytes(StandardCharsets.UTF_8)))
                        + "\r\n\r\n")
                        .getBytes(StandardCharsets.UTF_8);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                assert response != null;
                this.outputStream.write(response, 0, response.length);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}


