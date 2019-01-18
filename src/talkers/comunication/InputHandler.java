package talkers.comunication;

import talkers.iomanager.Manager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;

public class InputHandler extends Thread {
    DataInputStream input;
    Manager manager;

    public InputHandler(DataInputStream input, Manager manager) {
        this.manager = manager;
        this.input = input;

        // Для автоматического закрытия потока, если он остался один
        setDaemon(true);
    }

    @Override
    public void run() {

        try {
            String data;

            while ((data = input.readUTF()) != null) {
                System.out.println(data);
            }

            System.out.println(this.getClass().getSimpleName() + " stopped");
            manager.stopSignal();
        } catch (Exception e) {
            System.out.println("Remote host disconnected");
        }
    }

    // Это если потребуется отправлять эхо-ответы
    private void echo(String message) {
        manager.insert("echo: " + message + System.lineSeparator());
    }
}
