package shared;

import java.io.Serializable;

public class Proposal implements Serializable{
    private final int ballotNum;
    private final String decree;

    public Proposal(int ballotNum, String decree) {
        this.ballotNum = ballotNum;
        this.decree = decree;
    }

    public int getBallotNum(){return ballotNum;}

    public String getDecree() {return decree; }

    public boolean equal(Proposal proposal){
        return(this.ballotNum==proposal.getBallotNum()&&this.decree.equals(proposal.getDecree()));
    }
}
