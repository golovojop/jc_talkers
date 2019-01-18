package talkers;

import talkers.server.ServerBob;

public class Main {

    public static final int PORT = 65000;

    public static void main(String[] args) {
        ServerBob server = new ServerBob(true, PORT);
        server.start();
    }
}
