package shared;

import process.views.App;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MessageRequestType implements RequestType {
    private ArrayList<MsgModel> messages;

    public MessageRequestType(ArrayList<MsgModel> messages) {
        this.messages = messages;
    }

    public ArrayList<MsgModel> getMessages() {
        return messages;
    }

    @Override
    public Result executeRequest(ObjectInputStream is, ObjectOutputStream os, App manager) {
        return null;
    }
}
