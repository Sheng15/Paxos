package shared;

import server.ProcessModel;

public class AcceptRequestMessage extends MsgModel {

    private final Proposal proposal;

    public AcceptRequestMessage(ProcessModel outSet, ProcessModel destination,Proposal proposal){
        super(outSet,destination);
        this.proposal = proposal;
    }

    public Proposal getProposal(){return proposal;}
}
