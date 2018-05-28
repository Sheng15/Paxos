package server;

import process.views.App;
import shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MessageBroadcaster extends ProcessConnection {
    private final App app;
    private final State state;

    private boolean sendNotificationFlag = false;

    public MessageBroadcaster(App app, State state) {
        this.app = app;
        this.state = state;
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<ProcessModel> connectedProcesses = (ArrayList<ProcessModel>) state.getConnectedProcesses().clone();
            if (messageQueue.size() > 0) {
                ArrayList<MsgModel> msgs = (ArrayList<MsgModel>) messageQueue.clone();
                String message;
                // do not send message  back to origin
                //msgs.removeIf(m -> m.getOutSet().getProcessIndex()==process.getProcessIndex());

                for (int i = 0; i < connectedProcesses.size(); i++) {
                    ProcessModel process = connectedProcesses.get(i);
                    if (Math.random() > 0.5) {
                        CommunicationUtils.sendMessages(process.getOs(), msgs);
                        message = "send message to " + process.getProcessName() + "(index: " + process.getProcessIndex() + ").\n";
                        app.displayPanel.display(message);
                    } else {
                        message = "error when sending message to process " + process.getProcessName() + "(index: " + process.getProcessIndex() + ").\n";
                        app.displayPanel.display(message);
                    }
                    if (i == connectedProcesses.size() - 1) {
                        messageQueue.clear();
                        //System.out.println("Clear!");
                    }
                }
            }
            for (int i = 0; i < connectedProcesses.size(); i++) {
                ProcessModel process = connectedProcesses.get(i);
                try {
                    CommunicationUtils.sendVoteNum(process.getOs(),connectedProcesses.size());

                    // send notification about peers
                    if (sendNotificationFlag) {
                        System.out.println(sendNotificationFlag);
                        ArrayList<String> peers = new ArrayList<>();
                        peers.add("Server: " + app.getProcessName());
                        for (ProcessModel p : connectedProcesses)
                            peers.add(p.getProcessName());

                        CommunicationUtils.sendNotification(process.getOs(), peers);
                        System.out.println("notice "+ process.getProcessName());
                        if (i == connectedProcesses.size() - 1) {
                            sendNotificationFlag = false;
                            System.out.println(sendNotificationFlag);
                        }
                    }



                    // get data from processes
                    // wait for events from the server
                    ObjectInputStream is = process.getIs();
                    RequestType requestType = (shared.RequestType) is.readObject();
                    if (requestType instanceof MessageRequestType) {
                        ArrayList<MsgModel> msgArray = ((MessageRequestType) requestType).getMessages();
                        if(msgArray.size()>0){
                            messageQueue.addAll(msgArray);
                        }
                    }

                } catch (IOException e) {
                    // assume the process is dead, so remove from connected
                    try {
                        state.removeProcess(process);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

            alive();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePeerList() {
        sendNotificationFlag = true;
    }

}
