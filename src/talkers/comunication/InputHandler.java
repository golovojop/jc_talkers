package talkers.comunication;

import talkers.iomanager.Manager;

import java.io.BufferedReader;

public class InputHandler extends Thread {
    BufferedReader input;
    Manager manager;

    public InputHandler(BufferedReader input, Manager manager) {
        this.manager = manager;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String data;
            while ((data = input.readLine()) != null && manager.isActive()) {
                System.out.println(data);
            }
            System.out.println(this.getClass().getSimpleName() + "stopped");
        } catch (Exception e) {
            System.out.println(this.getClass().getSimpleName() + "interrupted");
        }
    }

    // Это если потребуется отправлять эхо-ответы
    private void echo(String message) {
        manager.push("echo: " + message + System.lineSeparator());
    }
}
