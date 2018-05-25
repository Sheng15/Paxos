package shared;

import server.ProcessModel;

public class PrepareRequestMessage extends MsgModel {
    private final int prepareNum;

    public PrepareRequestMessage(ProcessModel outset,ProcessModel destination,int prepareNum){
        super(outset,destination);
        this.prepareNum = prepareNum;
    }

    public PrepareRequestMessage(){
        this.prepareNum = -1;
    }


    public int getPrepareNum( ){
        return prepareNum;
    }

}
