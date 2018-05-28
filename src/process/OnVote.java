package process;

import process.views.App;
import server.ProcessModel;
import shared.CommunicationUtils;
import shared.ConsensusNotificationMessage;
import shared.MsgModel;
import shared.Proposal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class OnVote extends TimerTask{

    private App app;

    public OnVote(App app){
        this.app = app;
    }

    @Override
    public void run() {
        int voteNum = app.getVoteNum();
        int quorum = Math.floorDiv(voteNum, 2) + 1;
        ArrayList<ProcessModel> acceptedList = app.getAcceptedList();
        String message = "Accept Request Result:\n" +"Your Decree "+app.onVoteProposal.getDecree()+" is accepted by processes:"+"\n";
        for(ProcessModel processModel : acceptedList){
            String processName = processModel.getProcessName();
            int processIndex = processModel.getProcessIndex();
            message = message+"Process: "+processName+"(Index: "+processIndex+")."+"\n";
        }
        if(acceptedList.size()>=quorum){
            System.out.println("Accepted by quorum");
            app.onVoteFlag = false;
            app.consensusFlag= true;
            Proposal consensus = new Proposal(app.getVoteNum(),app.getDecree());
            ProcessModel outSet = new ProcessModel(app.getProcessName(), app.getIndex());
            ProcessModel destination = new ProcessModel();
            ConsensusNotificationMessage consensusNotificationMessage = new ConsensusNotificationMessage(outSet,destination,consensus);
            for (int i=0;i<3;i++){
                app.processConnection.messageQueue.add(consensusNotificationMessage);
            }
            this.app.setAcceptedProposal(consensus);
            message = message+"Your Proposal is Accepted by the Ballot's Quorum!"+"\n";
            JOptionPane.showMessageDialog(app,"We reach an consensus now!","Congras",JOptionPane.INFORMATION_MESSAGE);
        }else{
            app.onVoteFlag = false;
            message = message+"Your Proposal Failed to get acception from the Ballot's Quorum!"+"\n";
            JOptionPane.showMessageDialog(app,"Your Proposal Failed to Get Acception from the Ballot's Quorum!!","Notice",JOptionPane.INFORMATION_MESSAGE);
        }
        app.displayPanel.display(message);
    }
}

