package talkers.comunication;

import talkers.iomanager.Manager;

import java.io.BufferedWriter;

public class OutputHandler extends Thread {
    private BufferedWriter output;
    private Manager manager;

    public OutputHandler(BufferedWriter output, Manager manager) {
        this.manager = manager;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            while(manager.isActive()) {
                String message = manager.pop();
                output.write(message + System.lineSeparator());
                output.flush();
            }
            System.out.println(this.getClass().getSimpleName() + "stopped");
        } catch (Exception e) {
            System.out.println(this.getClass().getSimpleName() + "interrupted");
        }
    }
}
