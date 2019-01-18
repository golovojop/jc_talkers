package talktalk;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static talktalk.Share.*;

public class StartServer {
    public static void main(String[] srgs) {

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream os = null;
        DataInputStream is = null;

        ServerSocket server = null;
        Socket client = null;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server listens on port " + PORT);


            client = server.accept();
            System.out.println("Client connected. Type \"\\stop\" to terminate");

            is = new DataInputStream(client.getInputStream());
            os = new DataOutputStream(client.getOutputStream());

            InputHandler ih = new InputHandler(is);
            ih.start();

            String data;

            while ((data = console.readLine()) != null) {

                if(data.matches(CMD_STOP)){
                    throw new Exception("Server stopped");
                }
                else {
                    os.writeUTF(data);
                    os.flush();
                }
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
}
