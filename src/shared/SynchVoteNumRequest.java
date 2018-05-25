package shared;

import process.views.App;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SynchVoteNumRequest implements RequestType {

    private int voteNum;

    public SynchVoteNumRequest(int voteNum){
        this.voteNum = voteNum;
    }

    public int getVoteNum(){return voteNum;}

    @Override
    public Result executeRequest(ObjectInputStream is, ObjectOutputStream os, App manager) {
        return null;
    }
}
