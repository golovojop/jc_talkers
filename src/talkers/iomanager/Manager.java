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
    public synchronized void push(String message) {
        queue.add(message);
        notify();
    }

    // Поучить из очереди
    public synchronized String pop() {
        String message;

        while ((message = queue.poll()) == null) {
            try {
                wait();
            } catch (Exception e) {e.printStackTrace();}
        }
        notify();
        return message;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }
}
