package talkers;

import talkers.server.ServerBob;

public class Main {

    public static void main(String[] args) {
        ServerBob server = new ServerBob(true, 65000);
        server.start();
    }
}
