package talktalk;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static talktalk.Share.*;

public class StartClient {
    public static void main(String[] srgs) {

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream os = null;
        DataInputStream is = null;

        Socket socket = null;

        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Client connected to server");

            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());

            InputHandler ih = new InputHandler(is);
            ih.start();

            String data;

            while ((data = console.readLine()) != null) {
                os.writeUTF(data);
                os.flush();
            }

        } catch (Exception e) {e.printStackTrace();}
    }
}
