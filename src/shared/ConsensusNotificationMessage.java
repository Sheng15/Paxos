package shared;

import process.Process;
import server.ProcessModel;

public class ConsensusNotificationMessage extends MsgModel{
    private final Proposal proposal;

    public ConsensusNotificationMessage(ProcessModel outset,ProcessModel destnation,Proposal proposal){
        super(outset,destnation);
        this.proposal = proposal;
    }

    public Proposal getProposal(){return proposal;}
}
