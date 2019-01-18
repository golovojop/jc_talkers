package talkers.comunication;

import talkers.iomanager.Manager;

import java.io.BufferedReader;

public class InputHandler extends Thread {
    BufferedReader input;
    Manager manager;

    public InputHandler(BufferedReader input, Manager manager) {
        this.manager = manager;
        this.input = input;
        // Для автоматического закрытия потока, если он остался один
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            String data;
            while ((data = input.readLine()) != null) {
                System.out.println(data);
            }

            System.out.println(this.getClass().getSimpleName() + " stopped");
            manager.stopSignal();
        } catch (Exception e) {
            System.out.println(this.getClass().getSimpleName() + " interrupted");
        }
    }

    // Это если потребуется отправлять эхо-ответы
    private void echo(String message) {
        manager.insert("echo: " + message + System.lineSeparator());
    }
}
