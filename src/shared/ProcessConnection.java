package shared;


import java.util.ArrayList;

public abstract class ProcessConnection implements Runnable {

    public ArrayList<MsgModel> messageQueue = new ArrayList<>();

    protected void alive() {

    }
}
