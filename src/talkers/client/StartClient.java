package talkers.client;

import talkers.server.ServerBob;

public class StartClient {

    public static void main(String[] args) {
        ClientAlice client = new ClientAlice("127.0.0.1", ServerBob.PORT);
        client.start();
    }
}
