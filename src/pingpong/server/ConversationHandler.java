package pingpong.server;

import java.io.*;
import java.net.Socket;

public class ConversationHandler {
    private PongServer server;
    private Socket client;
    private int clientId;
    private boolean active;

    DataOutputStream streamOut  = null;
    BufferedReader console      = null;
    BufferedReader streamIn     = null;

    public ConversationHandler(PongServer server, Socket client, int clientId) {
        this.clientId = clientId;
        this.server = server;
        this.client = client;
        this.active = true;
    }

    public void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("receive() ThreadId:ClientId " + Thread.currentThread().getId() + ":" + clientId + " started");
                receive();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("transmit() ThreadId:ClientId " + Thread.currentThread().getId() + ":" + clientId + " started");
                transmit();
            }
        }).start();
    }

    // Обрабатываем сообщения от клиента и выводим на консоль
    private void receive() {
        try {
            streamIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String data;

            while ((data = streamIn.readLine()) != null && active) {
                System.out.println(data);
            }
        } catch (Exception e) {
            System.out.println("Exception in receive()");
        } finally {
            System.out.println("Hello after exception !!!");
            closeResources();
            server.removeClient(this);
        }
    }

    /**
     * end of the stream:
     * - 1 for read()
     * IOException for write
     */

    // Передаем сообщения клиенту
    private void transmit() {

        try {
            // Поток к клиенту
            streamOut = new DataOutputStream(client.getOutputStream());

            // Локальная консоль
            console = new BufferedReader(new InputStreamReader(System.in));

            // Приветствуем клиента
            streamOut.writeUTF("Server is ready to talk with client " + clientId + System.lineSeparator());
            streamOut.flush();

            String data;

            // Ожидаем ввод пользователя
            while ((data = console.readLine()) != null && active) {
                streamOut.writeUTF(data + System.lineSeparator());
                streamOut.flush();
            }
        } catch (Exception e) {
            System.out.println("[transmit] Client " + clientId + " disconnected");
        } finally {

            closeResources();
            server.removeClient(this);
        }
    }

    public void closeResources() {
        active = false;
        System.out.println("Hello from [closeResources()] !!!");
        try {
            streamIn.close();
        } catch (Exception e) { e.printStackTrace(); }
        System.out.println("streamIn closed");
        try {
            streamOut.close();
        } catch (Exception e) { e.printStackTrace(); }
        System.out.println("streamOut closed");
//        try {
//            console.close();
//        } catch (Exception e) { e.printStackTrace(); }
//        System.out.println("console closed");
        try {
            client.close();
        } catch (Exception e) { e.printStackTrace(); }
        System.out.println("Socket closed");

    }
}
