package shared;

import process.views.App;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NotificationRequestType implements RequestType {
    private ArrayList<String> peerList;

    public NotificationRequestType(ArrayList<String> peerList){
        this.peerList = peerList;
    }

    public ArrayList<String> getPeerList(){
        return peerList;
    }

    @Override
    public Result executeRequest(ObjectInputStream is, ObjectOutputStream os, App manager) {
        return null;
    }
}
