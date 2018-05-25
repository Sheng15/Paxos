package shared;

import server.ProcessModel;

public class AcceptRequestResponseMessage extends MsgModel {

    private final Proposal proposal;

    public AcceptRequestResponseMessage(ProcessModel outSet, ProcessModel destination,Proposal proposal){
        super(outSet,destination);
        this.proposal = proposal;
    }

    public Proposal getProposal(){return proposal;}

}
