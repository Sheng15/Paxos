package process;

import process.views.App;
import server.ProcessModel;
import shared.*;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;

public class MessageReceiver extends ProcessConnection {
    private final App app;
    private final String host;
    private final int port;

    private ObjectOutputStream os;

    public MessageReceiver(App app, String host, int port) {
        this.app = app;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = SocketFactory.getDefault().createSocket(host, port);
            os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Result result = CommunicationUtils.makeRequest(is, os, new JoinRequestType(app.getProcessName()));
            System.out.println(result);

            if (result instanceof Result.JoinDenied) {
                System.out.println("Join denied");
                app.showErrorMessage("The server did not accept your request", "Join denied");
                app.isProcess = false;
                return;
            } else if (result instanceof Result.Ok) {
                System.out.println("Join successful");
                app.setIndex(((Result.Ok) result).getIndex());
            }

            while (true) {
                // wait for events from the server
                try {
                    //System.out.println("beginning to read from server");
                    RequestType requestType = (RequestType) is.readObject();
                    handleRequestType(requestType);
                } catch (StreamCorruptedException x) {
                    System.out.println("StreamCorruptedException happened, but continuing");
                    new ObjectOutputStream(socket.getOutputStream()).reset();
                }
                // send messages to server
                if (messageQueue.size() > 0) {
                    CommunicationUtils.sendMessages(os, (ArrayList<MsgModel>) messageQueue.clone());
                    System.out.println("send message");
                    messageQueue.clear();
                }

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            app.showErrorMessage("Unkown host " + e.getMessage(), "Unkown Host");
        } catch (IOException e) {
            e.printStackTrace();
            String message = e.getMessage() != null ? e.getMessage() : "Connection lost";
            app.showErrorMessage(message, "Server Connection Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            app.showErrorMessage("Server crashed", "Server Error");
        }

        app.isProcess = false;
        app.peerListPanel.peerListModel.clear();
    }

    private void handleRequestType(RequestType requestType){ /////
        if (requestType instanceof SynchVoteNumRequest) {
            int voteNum=((SynchVoteNumRequest) requestType).getVoteNum();
            app.setVoteNum(voteNum);
            CommunicationUtils.keepTalking(os);
        } else if (requestType instanceof NotificationRequestType) {
            //notification of new peer
            NotificationRequestType type = (NotificationRequestType) requestType;
            ArrayList<String> peers = type.getPeerList();
            app.peerListPanel.peerListModel.clear();
            for (String peer : peers) {
                app.peerListPanel.peerListModel.addElement(peer);
            }
        }else if(requestType instanceof MessageRequestType){
            ArrayList<MsgModel> msgArray = ((MessageRequestType) requestType).getMessages();
            for(MsgModel message : msgArray){
                System.out.println("form: "+message.getOutSet().getProcessIndex());

                long delay = delay();

                Timer timer = new Timer();
                timer.schedule(new DelayTask(),delay);
                messageHandler(message);
            }
            msgArray.clear();
        } else {
            System.out.println("Request type not understood");
        }
    }

    private void handleRequestType1(RequestType requestType) { /////
        if (requestType instanceof SynchVoteNumRequest) {
            int voteNum=((SynchVoteNumRequest) requestType).getVoteNum();
            app.setVoteNum(voteNum);
            CommunicationUtils.keepTalking(os);
        } else if (requestType instanceof NotificationRequestType) {
            //notification of new peer
            NotificationRequestType type = (NotificationRequestType) requestType;
            ArrayList<String> peers = type.getPeerList();
            app.peerListPanel.peerListModel.clear();
            for (String peer : peers) {
                app.peerListPanel.peerListModel.addElement(peer);
            }
        }else if(requestType instanceof MessageRequestType){
            ArrayList<MsgModel> msgArray = ((MessageRequestType) requestType).getMessages();

            for(MsgModel message : msgArray){
                messageHandler(message);
            }

        } else {
            System.out.println("Request type not understood");
        }
    }


    public void messageHandler(MsgModel message){
        if (message instanceof PrepareRequestMessage) {
            int prepareNum = ((PrepareRequestMessage) message).getPrepareNum();
            if (prepareNum >= app.getPromiseNum()) {
                app.setPromiseNum(prepareNum);
                ProcessModel outSet = new ProcessModel(app.getProcessName(), app.getIndex());
                ProcessModel destination = message.getOutSet();
                PromiseMessage promiseMessage = new PromiseMessage(outSet, destination, prepareNum, app.getAcceptedProposal());
                app.processConnection.messageQueue.add(promiseMessage);
                System.out.println("you have my promise now");
            } else {
                ProcessModel from = message.getOutSet();
                String text = "Ingore prepare request from process " + from.getProcessName() + "(index: " + from.getProcessIndex() + ")" + "\n" + "\n";
                app.displayPanel.display(text);
            }
        } else if (message instanceof PromiseMessage) {
            ProcessModel outset = message.getOutSet();
            ProcessModel destination = message.getDestination();
            if (app.onPrepareFlag) {  //I am waiting for response of my prepare request!
                if (destination.equals(app.getProcessName(), app.getIndex()) &&
                        ((PromiseMessage) message).getPrepareNum() == app.onPrepareNum) {
                    app.promiseList.add(outset);
                    System.out.println("get a promise");
                } else {
                    //ignore it
                    System.out.println("ignore a promise of other process ");
                }
            }
        }else if (message instanceof AcceptRequestMessage) {
            Proposal proposal = ((AcceptRequestMessage) message).getProposal();
            int ballotNum = proposal.getBallotNum();

            if(ballotNum<app.getPromiseNum()){
                System.out.println("ignore a proposal!");
            }else{
                app.setPromiseNum(ballotNum);
                app.setAcceptedProposal(proposal);
                app.hasAcceptedProposalFlag = true;
                ProcessModel outSet = new ProcessModel(app.getProcessName(), app.getIndex());
                ProcessModel destination = message.getOutSet();
                AcceptRequestResponseMessage acceptRequestResponseMessage = new AcceptRequestResponseMessage(outSet,destination,proposal);
                app.processConnection.messageQueue.add(acceptRequestResponseMessage);
                System.out.println("I accept your proposal!");
            }
        }else if (message instanceof AcceptRequestResponseMessage) {
            ProcessModel outset = message.getOutSet();
            ProcessModel destination = message.getDestination();
            Proposal proposal = ((AcceptRequestResponseMessage) message).getProposal();
            if (app.onVoteFlag) {  //I am waiting for response of my accept request!
                if (destination.equals(app.getProcessName(), app.getIndex()) &&
                        proposal.equal(app.onVoteProposal)) {
                    app.acceptedList.add(outset);
                    System.out.println("accepted");
                } else {
                    //ignore it
                    System.out.println("ignore a accept response of other process ");
                }
            }
        }else {
            System.out.println("Message not recognized!");
        }
    }

    private long delay(){
        return Math.round(Math.random()*1000);
    }


}
