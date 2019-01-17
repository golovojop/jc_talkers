package pingpong.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PongServer {
    private int port;
    private int clientId;

    public PongServer(int port) {
        this.port = port;
        this.clientId = 1000;
    }

    public void start() {

        try(ServerSocket server = new ServerSocket(port)) {

            while (true) {
                try(Socket client = server.accept()) {
                    ConversationHandler ch = new ConversationHandler(clientId++, client);
                    ch.start();
                }
                catch (IOException e){e.printStackTrace();}
            }
        } catch (IOException e) {}
    }
}
