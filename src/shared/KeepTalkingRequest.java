package shared;

import process.views.App;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class KeepTalkingRequest implements RequestType {

    public KeepTalkingRequest(){}

    @Override
    public Result executeRequest(ObjectInputStream is, ObjectOutputStream os, App manager) {
        return null;
    }
}
