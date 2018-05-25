package shared;

import server.ProcessModel;

import java.io.Serializable;

public class MsgModel implements Serializable {
    private final ProcessModel outSet;
    private final ProcessModel destination;

    public MsgModel(ProcessModel outSet,ProcessModel destination) {
        this.outSet=outSet;
        this.destination = destination;
    }

    public MsgModel() {
        this.outSet=null;
        this.destination = null;
    }

    public ProcessModel getOutSet() {
        return outSet;
    }

    public ProcessModel getDestination() {
        return destination;
    }



}
