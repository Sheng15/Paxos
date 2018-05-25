package server;

import process.views.App;
import shared.RequestType;
import shared.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {
    private Socket socket;
    private App server;

    public RequestHandler(Socket socket, App server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            RequestType reqType = (RequestType) objectInputStream.readObject();
            // perform the actual request
            Result result = reqType.executeRequest(objectInputStream, objectOutputStream, server);

            // give output back to process
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
