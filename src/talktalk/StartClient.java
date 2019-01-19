package talktalk;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static talktalk.Share.*;

public class StartClient {
    public static void main(String[] args) {

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream os = null;
        DataInputStream is = null;
        InputHandler ih = null;

        Socket socket = null;

        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Client connected to server " + HOST + ":" + PORT);

            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());

            ih = new InputHandler(is, Mode.CLIENT, true);
            ih.start();

            String message;

            while ((message = console.readLine()) != null) {
                os.writeUTF(message);
                os.flush();
            }

        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            ih.setActive(false);
            CloseResources(is, os, socket);
        }
    }
}
