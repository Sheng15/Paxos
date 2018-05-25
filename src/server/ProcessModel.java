package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ProcessModel implements Serializable{
    private final String processName;
    private final int index;
    private final ObjectInputStream is;
    private final ObjectOutputStream os;

    public ObjectInputStream getIs() {
        return is;
    }

    public ObjectOutputStream getOs() {
        return os;
    }

    public ProcessModel(String processName, int index, ObjectInputStream is, ObjectOutputStream os) {

        this.processName = processName;
        this.index = index;
        this.is = is;
        this.os = os;

    }

    public ProcessModel(String processName, int index) {

        this.processName = processName;
        this.index = index;
        this.is = null;
        this.os = null;
    }

    public ProcessModel(){
        this.processName = null;
        this.index = -1;
        this.is = null;
        this.os = null;
    }

    public String getProcessName() {
        return processName;
    }

    public int getProcessIndex() {
        return index;
    }

    public Boolean equals(String processName,int index){
        return (this.getProcessName().equals(processName)&&
                    this.getProcessIndex()==index);
    }

}
