package process.views;

import process.MessageReceiver;
import process.AppGUI.panel.*;
import process.controllers.*;
import server.MessageBroadcaster;
import server.ProcessModel;
import server.SocketListener;
import server.State;
import shared.ProcessConnection;
import shared.Proposal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class App extends JFrame {

    public HeadPanel headPanel;
    public StatePanel StatePanel;
    public DecreePanel decreePanel;
    public DisplayPanel displayPanel;
    public PeerListPanel peerListPanel;

    public boolean isServer;
    public boolean isProcess;

    private int index;
    private String processName;
    private int voteNum;// the count of who can cast votes for the degree.
    private int prepareNum;
    private int promiseNum;
    private String decree;
    private Proposal acceptedProposal;

    //process flags
    public boolean notProposerFlag;
    public boolean hasPromiseFromQuorumFlag;
    public boolean hasAcceptedProposalFlag;
    public boolean hasMadePromise;
    public boolean onPrepareFlag;
    public boolean onVoteFlag;
    public boolean consensusFlag;

    public int onPrepareNum;
    public ArrayList<ProcessModel> promiseList;

    public Proposal onVoteProposal;
    public ArrayList<ProcessModel> acceptedList;

    public State state;



    private String host;
    private int port = 3000;

    public ProcessConnection processConnection;

    public App(String processName,  String host) {
        super("Process " + processName);
        this.processName = processName;
        this.host = host;

        this.setBounds(ConstantUI.MAIN_WINDOW_X, ConstantUI.MAIN_WINDOW_Y,
                ConstantUI.MAIN_WINDOW_WIDTH, ConstantUI.MAIN_WINDOW_HEIGHT);
        this.getContentPane().setLayout(new BorderLayout());
        headPanel = new HeadPanel();
        StatePanel = new StatePanel();
        decreePanel = new DecreePanel();
        displayPanel = new DisplayPanel();
        peerListPanel = new PeerListPanel();



        this.getContentPane().add(headPanel, BorderLayout.NORTH);
        this.getContentPane().add(StatePanel, BorderLayout.WEST);
        this.getContentPane().add(decreePanel, BorderLayout.SOUTH);
        this.getContentPane().add(peerListPanel, BorderLayout.EAST);
        this.getContentPane().add(displayPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        new HeadPanelController(headPanel, this);
        new DecreeController(this,decreePanel);
    }

    public void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, "Error: " + message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    public void runAsServer() {
        state = new State();
        new PeerListController(headPanel, peerListPanel, state, this);
        setTitle("Server");
        this.headPanel.setButtonPrepareInvisiable();
        this.headPanel.setButtonVoteInvisiable();

        peerListPanel.peerListModel.addElement("Server: " + processName);

        SocketListener socketListener = new SocketListener(port, this);
        new Thread(socketListener).start();

        // start a thread that makes sure to broadcast the whiteboard state to all connected clients
        //clearWhiteBoard();//clear WhiteBoard before run as server, especially for any opened picture
        startConnectionThread(new MessageBroadcaster(this, state));
        System.out.println("Im Server now!");
    }

    public void runAsProcess() {

        index = -2;
        voteNum = -1;
        prepareNum =-1;
        promiseNum=-1;
        decree = null;

        onPrepareNum = -1;
        promiseList = new ArrayList<ProcessModel>();

        onVoteProposal =new Proposal(-1,null);
        acceptedList = new ArrayList<ProcessModel>();


        notProposerFlag = false;
        hasPromiseFromQuorumFlag=false;
        hasAcceptedProposalFlag=false;
        hasMadePromise=false;

        onPrepareFlag=false;
        onVoteFlag=false;

        this.decreePanel.decreeBox.setText("Write Your Decree Here!");

        acceptedProposal = new Proposal(-1,null);

        //clearWhiteBoard();//clear WhiteBoard before join, especially for any opened picture
        startConnectionThread(new MessageReceiver(this, host, port));
        System.out.println("Im Process now！");
    }

    private void startConnectionThread(ProcessConnection pc) {
        processConnection = pc;
        new Thread(pc).start();
    }

    public boolean addProcess(String name) {
        if(!isServer) {
            System.out.println("Only Server can add Process");
            return false;
        }

        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Peer " + name + " wants to join", "Join",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == 0) {
            System.out.println("Yes option");
            peerListPanel.peerListModel.addElement(name);
            ((MessageBroadcaster)processConnection).updatePeerList();
            return true;
        } else {
            System.out.println("No Option");
            return false;
        }
    }

    public String getProcessName(){return processName;}

    public void  setIndex(int index){
        this.index = index;
        this.StatePanel.showIndex(index);
    }

    public int getIndex(){return  index;}

    public void  setVoteNum(int voteNum){
        this.voteNum = voteNum;
        this.StatePanel.showVoteNum(voteNum);
    }

    public int getVoteNum(){ return voteNum; }

    public void  setPrepareNum(int prepareNum){
        this.prepareNum=prepareNum;
        this.StatePanel.showPrepareNum(prepareNum);
    }

    public int getPrepareNum(){return prepareNum;}

    public void  setPromiseNum(int promiseNum){
        this.promiseNum=promiseNum;
        this.StatePanel.showPromiseNum(promiseNum);
    }

    public int getPromiseNum(){return  promiseNum;}

    public void  setAcceptedProposal(Proposal proposal){
        this.acceptedProposal = proposal;
        this.StatePanel.showAcceptProposal(proposal);
        this.setDecree(proposal.getDecree());
    }

    public Proposal getAcceptedProposal(){return acceptedProposal;}

    public void  setDecree(String decree){
        this.decree = decree;
    }

    public String getDecree(){return decree;}

    public ArrayList<ProcessModel> getPromiseList(){return promiseList;}

    public ArrayList<ProcessModel> getAcceptedList(){return acceptedList;}

    //guaranteeing that no two proposals are ever issued with the same number
    public int nextProposalNumber(int promiseNum,int prepareNum, int index){
        int nextProNum=-1;
        if(promiseNum>2000*index-1){
            JOptionPane.showMessageDialog(this,"You cannot be a proposer now!","Notice",JOptionPane.INFORMATION_MESSAGE);
            notProposerFlag = true;
            return prepareNum;
        }else if(prepareNum>=promiseNum){
            nextProNum = epochNum(prepareNum,index);
            return nextProNum;
        }else{
            while(nextProNum<promiseNum){
                nextProNum = epochNum(nextProNum,index);
            }
            return nextProNum;
        }
    }

    public int epochNum(int prepareNum , int index){
        if(prepareNum == -1){
            return index;
        }else if(prepareNum == index){
           return  1000*index;
        }else{
            int j = prepareNum - 1000*index;
            return 1000*index+j+1;
        }
    }
}

