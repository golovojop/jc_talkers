package talkers.iomanager;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс служит для обмена сообщениями между потоками.
 */

public class Manager {

    private Queue<String> queue;
    private boolean active;

    public Manager(boolean active) {
        this.queue = new LinkedList<>();
        this.active = active;
    }

    // Поместить в очередь
    public synchronized boolean insert(String message) {
        if(active) queue.add(message);
        notify();
        return active;
    }

    // Поучить из очереди
    public synchronized String extract() {
        String message;

        while ((message = queue.poll()) == null & active) {
            try {
                wait();
            } catch (Exception e) {e.printStackTrace();}
        }
        notify();
        return message;
    }

    public synchronized void stopSignal() {
        this.active = false;
        notifyAll();
    }
}
