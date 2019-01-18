package talkers.client;

import talkers.comunication.InputHandler;
import talkers.comunication.OutputHandler;
import talkers.iomanager.Manager;
import talkers.server.ServerBob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;

public class ClientAlice {

    private Manager manager;
    private Socket socket;
    private String host;
    private int port;

    public ClientAlice(String host, int port) {
        this.manager = new Manager(true);
        this.port = port;
        this.host = host;
    }

    public void start(){

        DataInputStream input = null;
        DataOutputStream output = null;

        try {

            socket = new Socket(host, port);
            System.out.println("Client connected to " + host + ":" + port);

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            InputHandler ih = new InputHandler(input, manager);
            OutputHandler oh = new OutputHandler(output, manager);

            ih.start();
            oh.start();

            // Чтение из консоли в основном потоке
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while ((message = console.readLine()) != null) {

                if(message.matches("^\\\\stop.*")){
                    manager.stopSignal();
                    throw new Exception("Client was stoped");
                }
                else {
                    manager.insert(message);
                }
            }

        } catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            try {
                if(input != null) input.close();
            } catch (Exception e) {e.printStackTrace();}
            try {
                if(output != null) output.close();
            } catch (Exception e) {e.printStackTrace();}
            try {
                if(socket != null) socket.close();
            } catch (Exception e) {e.printStackTrace();}
        }
    }

}
