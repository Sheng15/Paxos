package shared;

import server.ProcessModel;

public class PromiseMessage extends MsgModel {

    private final int prepareNum;
    private final Proposal acceptedProposal;

    public PromiseMessage(ProcessModel outset, ProcessModel destination,int prepareNum,Proposal acceptedProposal){
        super(outset,destination);
        this.prepareNum = prepareNum;
        this.acceptedProposal = acceptedProposal;
    }

    public int getPrepareNum(){return  prepareNum;}

    public Proposal getAcceptedProposal(){return acceptedProposal;}


}
