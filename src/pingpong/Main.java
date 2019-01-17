package pingpong;

import pingpong.server.PongServer;

public class Main {

    public static int PORT = 65000;

    public static void main(String[] args) {
        PongServer ps = new PongServer(PORT);
        ps.start();
    }
}
