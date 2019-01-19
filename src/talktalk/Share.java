package talktalk;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Share {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 65000;
    public static final String CMD_STOP = "^\\\\stop.*";
    public static final String CMD_RESET = "^\\\\reset.*";

    enum Mode {
        CLIENT, SERVER
    }

    public static class HostDisconnectException extends Exception {}
    public static class CommandToStopException extends Exception {}

    public static void CloseResources(InputStream i, OutputStream o, Socket socket) {
        try {
            if(i != null) i.close();
        } catch (Exception e) {e.printStackTrace();}
        try {
            if(o != null) o.close();
        } catch (Exception e) {e.printStackTrace();}
        try {
            if(socket != null) socket.close();
        } catch (Exception e) {e.printStackTrace();}
    }


}

