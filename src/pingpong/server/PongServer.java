package pingpong.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class PongServer {
    private int port;
    private int clientId;
    private Vector<ConversationHandler> clients;

    public PongServer(int port) {
        this.port = port;
        this.clientId = 1000;
        this.clients = new Vector<>();
    }

    public void start() {

        try(ServerSocket server = new ServerSocket(port)) {

            while (true) {
                try {
                    Socket client = server.accept();
                    ConversationHandler handler = new ConversationHandler(clientId++, client);
                    clients.add(handler);
                    handler.start();
                }
                catch (IOException e){e.printStackTrace();}
            }

        } catch (IOException e) {e.printStackTrace();}
    }
}
