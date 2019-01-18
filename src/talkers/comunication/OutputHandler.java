package talkers.comunication;

import talkers.iomanager.Manager;

import java.io.BufferedWriter;
import java.io.DataOutputStream;

public class OutputHandler extends Thread {
    private DataOutputStream output;
    private Manager manager;

    public OutputHandler(DataOutputStream output, Manager manager) {
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
                    output.writeUTF(message + System.lineSeparator());
                    output.flush();
                } else break;
            }

            System.out.println(this.getClass().getSimpleName() + " stopped");
        } catch (Exception e) {
            System.out.println(this.getClass().getSimpleName() + " interrupted");
        }
    }
}
