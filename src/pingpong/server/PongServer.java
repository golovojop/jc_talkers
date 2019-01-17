package pingpong.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PongServer {
    private int port;
    private int clientId;
    private List<ConversationHandler> clients;

    public PongServer(int port) {
        this.port = port;
        this.clientId = 1000;
        this.clients = new LinkedList<>();
    }

    public void start() {

        try(ServerSocket server = new ServerSocket(port)) {

            while (true) {
                try {
                    Socket client = server.accept();
                    if(isBussy()) {
                        rejectConnection(client);
                        client.close();
                    }
                    else {
                        ConversationHandler handler = new ConversationHandler(this, client, clientId++);
                        addClient(handler);
                        handler.start();
                    }
                }
                catch (IOException e){e.printStackTrace();}
            }
        } catch (IOException e) {e.printStackTrace();}
    }

    public void rejectConnection(Socket client) {
        try(PrintStream out = new PrintStream(client.getOutputStream())) {
            out.println("\nThe server is busy. Try later");
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public void addClient(ConversationHandler ch) {
        clients.add(ch);
        System.out.println("[addClient] Clients qty " + clients.size());
    }

    public void removeClient(ConversationHandler ch){
        System.out.println("Hello from [removeClient()] !!!");
        clients.remove(ch);
        System.out.println("[removeClient] Clients qty " + clients.size());
    }

    public  boolean isBussy() {
        System.out.println("{isBussy} Clients qty " + clients.size());
        return clients.size() != 0;
    }

}
