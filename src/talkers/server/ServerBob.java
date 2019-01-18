package talkers.server;

import pingpong.server.ConversationHandler;
import talkers.comunication.InputHandler;
import talkers.comunication.OutputHandler;
import talkers.iomanager.Manager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBob {
    private Manager manager;
    private int port;

    public ServerBob(boolean active, int port) {
        this.manager = new Manager(true);
        this.port = port;
        this.manager.push("Server ready" + System.lineSeparator());
    }

    public void start() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            Socket client = server.accept();

            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            InputHandler ih = new InputHandler(input, manager);
            OutputHandler oh = new OutputHandler(output, manager);

            ih.start();
            oh.start();

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while ((message = console.readLine()) != null && manager.isActive()) {

                if(message.matches("^\\\\stop.*")){
                    manager.setActive(false);
                    throw new Exception("Server stoped");
                }
                else {
                    manager.push(message);
                }
            }
        }
        catch (Exception e) { System.out.println(e.getMessage());}
        finally {
            try {
                server.close();
            } catch (Exception e){e.printStackTrace();}
        }
    }

    // Отклонить коннект
    private void rejectConnection(Socket socket){
        BufferedWriter output = null;

        try {
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.write("Server is bussy. Try to connect later" + System.lineSeparator());
            output.flush();

        } catch (Exception e) {

        } finally {
            try {
                output.close();
            } catch (Exception e) {e.printStackTrace();}
            try {
                socket.close();
            } catch (Exception e) {e.printStackTrace();}
        }
    }
}
