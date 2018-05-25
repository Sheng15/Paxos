package shared;


import process.views.App;
import server.ProcessModel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JoinRequestType implements RequestType {
    private String processName;
    private int index;

    public JoinRequestType(String processName) {
        this.processName = processName;
    }

    @Override
    public Result executeRequest(ObjectInputStream is, ObjectOutputStream os, App server) {
        if (server.addProcess(processName)) {

            // process is accepted
            // all communication can now be made on the socket

            server.state.processCount+=1;
            index = server.state.processCount;
            server.state.addProcess(new ProcessModel(processName, index, is, os));
            return new Result.Ok(index);
        } else {
            return new Result.JoinDenied();
        }
    }
}
