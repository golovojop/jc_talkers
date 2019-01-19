package talktalk;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static talktalk.Share.*;

public class StartServer {

    public static void main(String[] srgs) {

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream os = null;
        DataInputStream is = null;
        InputHandler ih = null;

        ServerSocket server = null;
        Socket client = null;
        boolean active = true;

        while(active){

            String message;

            try {
                server = new ServerSocket(PORT);
                System.out.println("\n *** Server listens on port " + PORT + " ***" + System.lineSeparator());

                client = server.accept();
                System.out.println("Client connected.\nType \"\\reset\" to close connection and \"\\stop\" to exit");

                // Закрываю серверный сокет, потому что сервер
                // расчитан на одного клиента (???)
                server.close();

                is = new DataInputStream(client.getInputStream());
                os = new DataOutputStream(client.getOutputStream());

                ih = new InputHandler(is, Mode.SERVER, active);
                ih.start();

                while ((message = console.readLine()) != null) {

                    if(message.matches(CMD_STOP)){
                        throw new CommandToStopException();
                    }
                    else if (message.matches(CMD_RESET)){
                        throw new HostDisconnectException();
                    } else {
                        os.writeUTF(message);
                        os.flush();
                    }
                }
            } catch (CommandToStopException cse) {
                message = "Server stopped";
                System.out.println(message);
                ih.setActive(active = false);
                try {
                    os.writeUTF(message);
                    os.flush();
                } catch (IOException e){e.printStackTrace();}
            } catch (HostDisconnectException hde) {
                ih.setActive(false);
            } catch (NullPointerException npe) {
                active = false;
            }
            catch (Exception e) {
                //e.printStackTrace();
            } finally {
                CloseResources(is, os, client);
            }
        }
    }
}
