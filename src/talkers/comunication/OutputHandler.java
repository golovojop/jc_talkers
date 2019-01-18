package talkers.comunication;

import talkers.iomanager.Manager;

import java.io.BufferedWriter;

public class OutputHandler extends Thread {
    private BufferedWriter output;
    private Manager manager;

    public OutputHandler(BufferedWriter output, Manager manager) {
        this.manager = manager;
        this.output = output;
        // Для автоматического закрытия потока, если он остался один
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            while(true) {
                String message = manager.extract();

                if (message != null) {
                    output.write(message + System.lineSeparator());
                    output.flush();
                } else break;
            }

            System.out.println(this.getClass().getSimpleName() + " stopped");
        } catch (Exception e) {
            System.out.println(this.getClass().getSimpleName() + " interrupted");
        }
    }
}
