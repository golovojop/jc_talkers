package pingpong.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConversationHandler {
    private Socket client;
    private int clientId;

    public ConversationHandler(int clientId, Socket client) {
        this.client = client;
        this.clientId = clientId;
    }

    public void start() {

        System.out.println("Server is ready to talk with client " + clientId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                receive();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                transmit();
            }
        }).start();
    }

    // Обрабатываем сообщения от клиента
    private void receive() {
        try (DataInputStream in = new DataInputStream(client.getInputStream())) {

            while (true) {
                String str = in.readUTF();
                System.out.println(str);
            }
        } catch (Exception e) {}
    }

    // Передаем сообщения клиенту
    private void transmit() {
        try (DataOutputStream out = new DataOutputStream(client.getOutputStream());
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            out.writeUTF("Server is ready to talk with client " + clientId);

            while(true) {
                String message = console.readLine();
                out.writeUTF(message);
            }
        }
        catch (Exception e) {}
        }
    }
