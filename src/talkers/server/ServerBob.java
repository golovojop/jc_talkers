package talkers.server;

import talkers.comunication.InputHandler;
import talkers.comunication.OutputHandler;
import talkers.iomanager.Manager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBob {

    public static final int PORT = 65000;

    private Manager manager;
    private int port;

    public ServerBob() {
        this.port = PORT;
        this.manager = new Manager(true);
        this.manager.insert("Server ready" + System.lineSeparator());
    }

    public void start() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            Socket client = server.accept();

            // Приложение расчитано на работу с одним клиентом.
            // Поэтому решил здесь закрыть серверный сокет.
            server.close();

            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            InputHandler ih = new InputHandler(input, manager);
            OutputHandler oh = new OutputHandler(output, manager);

            ih.start();
            oh.start();

            // Чтение из консоли в основном потоке
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;

            System.out.println("Connection received. Type \"\\stop\" to terminate");
            while ((message = console.readLine()) != null) {

                if(message.matches("^\\\\stop.*")){
                    manager.stopSignal();
                    throw new Exception("Server was stoped");
                }
                else {
                    manager.insert(message);
                }
            }
        }
        catch (Exception e) { System.out.println(e.getMessage());}
    }

    // Отклонить коннект (не используется)
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
