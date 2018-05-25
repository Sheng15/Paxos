package process.controllers;

import process.AppGUI.panel.HeadPanel;
import process.OnPrepare;
import process.OnVote;
import process.views.App;
import server.ProcessModel;
import shared.AcceptRequestMessage;
import shared.PrepareRequestMessage;
import shared.Proposal;

import javax.swing.*;
import java.util.Timer;

public class HeadPanelController {

    private HeadPanel headPanel;
    private App app;

    public HeadPanelController(HeadPanel headPanel, App app) {
        this.headPanel = headPanel;
        this.app = app;

        addHeadPanelController();
    }

    private void addHeadPanelController() {
        headPanel.buttonCreateRoom.addActionListener(e -> {
            if (app.isServer || app.isProcess) {
                app.showErrorMessage("You can NOT start a Paxos demo now!", "ERROR!");
            } else {
                app.isServer = true;
                app.runAsServer();
            }
        });

        headPanel.buttonJoin.addActionListener(e -> {
            if (app.isServer || app.isProcess) {
                app.showErrorMessage("You can NOT join a Paxos demo now!", "ERROR!");
            } else {
                app.isProcess = true;
                app.runAsProcess();
            }
        });

        headPanel.buttonPrepare.addActionListener(e -> {
            if (app.isServer) {
                app.showErrorMessage("A server cannot make prepare request!", "ERROR!");
            } else if(app.onPrepareFlag||app.onVoteFlag){
                app.showErrorMessage("You are waiting for response!\n      You cannot make prepare request now!", "ERROR!");
            }else {
                int prepareNum = app.nextProposalNumber(app.getPromiseNum(), app.getPrepareNum(), app.getIndex());
                if (app.notProposerFlag) {
                    JOptionPane.showMessageDialog(app, "You cannot be a proposer now!", "Notice", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    app.promiseList.clear();
                    ProcessModel outSet = new ProcessModel(app.getProcessName(), app.getIndex());
                    ProcessModel destination = new ProcessModel();//index is -1,all process will accept this request,
                    app.setPrepareNum(prepareNum);
                    PrepareRequestMessage prepareRequestMessage = new PrepareRequestMessage(outSet, destination, app.getPrepareNum());
                    app.promiseList.add(new ProcessModel(app.getProcessName(), app.getIndex()));//of course you agree with yourself
                    app.setPromiseNum(prepareNum);
                    app.processConnection.messageQueue.add(prepareRequestMessage);
                    app.onPrepareFlag = true;//You have sent a prepare request and waite for response now!
                    app.onPrepareNum = prepareNum;

                    Timer timer = new Timer();
                    OnPrepare onPrepareTask = new OnPrepare(app);
                    timer.schedule(onPrepareTask, 5000);
                }
            }
        });

        headPanel.buttonVote.addActionListener(e -> {
            if (app.isServer ) {
                app.showErrorMessage("A server cannot make accept request!", "ERROR!");
            } else if(app.onPrepareFlag||app.onVoteFlag){
                app.showErrorMessage("You are waiting for response!\n      You cannot make anaccept request now!", "ERROR!");
            }else if(!app.hasPromiseFromQuorumFlag){
                app.showErrorMessage("You need promise from the ballot's quorum before you make an accept request!", "ERROR!");
            }else if(app.getDecree()==null) {
                app.showErrorMessage("Write your decree before you start your vote!", "ERROR!");
            }else{
                ProcessModel outSet = new ProcessModel(app.getProcessName(),app.getIndex());
                ProcessModel destination = new ProcessModel();//index is -1,all process will accrpt this request,
                Proposal proposal = new Proposal(-1,null);
                if(app.hasAcceptedProposalFlag){
                    proposal = app.getAcceptedProposal();
                } else {
                    proposal = new Proposal(app.getPrepareNum(),app.getDecree());
                }
                AcceptRequestMessage acceptRequestMessage = new AcceptRequestMessage(outSet,destination,proposal);
                app.processConnection.messageQueue.add(acceptRequestMessage);
                app.onVoteFlag = true;//You have sent a accept request and waite for response now!
                app.onVoteProposal = proposal;

                Timer timer = new Timer();
                OnVote onVoteTask = new OnVote(app);
                timer.schedule(onVoteTask, 5000);

            }
        });
    }
}
