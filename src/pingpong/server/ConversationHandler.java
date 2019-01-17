package pingpong.server;

import java.io.*;
import java.net.Socket;

public class ConversationHandler {
    private Socket client;
    private int clientId;

    public ConversationHandler(int clientId, Socket client) {
        this.client = client;
        this.clientId = clientId;
    }

    public void start() {

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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            while (true) {
                String str = br.readLine();
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Передаем сообщения клиенту
    private void transmit() {
        try (PrintStream out = new PrintStream(client.getOutputStream());
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            out.println("Server is ready to talk with client " + clientId);

            while (true) {
                String message = console.readLine();
                out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
