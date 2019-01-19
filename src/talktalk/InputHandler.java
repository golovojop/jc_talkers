package talktalk;

import static talktalk.Share.*;


import java.io.DataInputStream;
import java.io.IOException;

public class InputHandler extends Thread {

    DataInputStream input;
    Mode mode;
    boolean processActive;

    public InputHandler(DataInputStream input, Mode mode, boolean active) {
        this.processActive = active;
        this.input = input;
        this.mode = mode;

        // Для автоматического закрытия потока, если он остался один
        setDaemon(true);
    }

    public void setActive(boolean active) {
        this.processActive = active;
    }

    @Override
    public void run() {

        String data;
        try {
            while((data = input.readUTF()) != null) {
                System.out.print(data + System.lineSeparator());
            }
        } catch (IOException e) {
            if(processActive){
                switch (mode) {
                    case CLIENT:
                        System.out.println("Server closed connection. Press ENTER twice to exit.");
                        break;
                    case SERVER:
                        System.out.println("Client closed connection. Press ENTER twice to accept a new client.");
                }

            }
        }
    }
}
