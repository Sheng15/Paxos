package process;

import process.views.App;
import server.ProcessModel;
import shared.MsgModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class OnPrepare extends TimerTask{

    private App app;

    public OnPrepare(App app){
        this.app = app;
        }

    @Override
    public void run() {
        int voteNum = app.getVoteNum();
        int quorum = Math.floorDiv(voteNum, 2) + 1;
        ArrayList<ProcessModel> promiseList = app.getPromiseList();
        String message = "Prepare Request Result:\n" +"Prepare Number "+app.getPrepareNum()+" get promise from processes:"+"\n";
        for(ProcessModel processModel : promiseList){
            String processName = processModel.getProcessName();
            int processIndex = processModel.getProcessIndex();
            message = message+"Process: "+processName+"(Index: "+processIndex+")."+"\n";
        }
        if(promiseList.size()>=quorum){
            System.out.println("Promise from quorum");
            app.onPrepareFlag = false;
            app.hasPromiseFromQuorumFlag = true;
            message = message+"You Get Promise from the ballot's quorum!"+"\n";
            JOptionPane.showMessageDialog(app,"You Get Promise from the Ballot's Quorum!","Congras",JOptionPane.INFORMATION_MESSAGE);
        }else{
            app.onPrepareFlag = false;
            message = message+"You Failed to Get Promise from the Ballot's Quorum!"+"\n";
            JOptionPane.showMessageDialog(app,"You Failed to Get Promise from the Ballot's Quorum!","Notice",JOptionPane.INFORMATION_MESSAGE);
        }
        app.displayPanel.display(message);
    }
}

