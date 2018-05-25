package server;

import process.views.App;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketListener extends Thread {
    private final int port;
    private App server;

    public SocketListener(int port, App server) {
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // get socket
        ServerSocketFactory socketFactory = ServerSocketFactory.getDefault();
        try (ServerSocket serverSocket = socketFactory.createServerSocket(port)) {

            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                executorService.execute(new RequestHandler(acceptedSocket, server));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
