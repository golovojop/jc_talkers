package talktalk;

import talkers.iomanager.Manager;

import java.io.DataInputStream;
import java.io.IOException;

public class InputHandler extends Thread {

    DataInputStream input;

    public InputHandler(DataInputStream input) {
        this.input = input;

        // Для автоматического закрытия потока, если он остался один
        setDaemon(true);
    }

    @Override
    public void run() {

        String data;
        try {
            while((data = input.readUTF()) != null) {
                System.out.println(data);
            }
        } catch (IOException e) {System.out.println(e.getMessage());}
    }
}
