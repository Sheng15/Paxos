package process.controllers;

import process.AppGUI.panel.HeadPanel;
import process.AppGUI.panel.PeerListPanel;
import process.views.App;
import server.MessageBroadcaster;
import server.State;

public class PeerListController {
    private HeadPanel headPanel;
    private PeerListPanel peerListPanel;
    private State state;
    private App app;

    public PeerListController(HeadPanel headPanel, PeerListPanel peerListPanel, State state, App app) {
        this.headPanel = headPanel;
        this.peerListPanel = peerListPanel;
        this.state = state;
        this.app = app;
        addController();
    }

    public void addController() {
        headPanel.buttonKick.addActionListener(e -> {

            int peerNameIndex = peerListPanel.peerList.getSelectedIndex();
            // cannot remove server, which is at index 0
            if (peerNameIndex != -1 && peerNameIndex != 0) {
                String peerName = (String) peerListPanel.peerList.getSelectedValue();
                System.out.println(peerName + " removed");
                peerListPanel.peerListModel.remove(peerNameIndex);
                state.removeProcess(peerName);
                ((MessageBroadcaster)app.processConnection).updatePeerList();
            }

        });
    }
}
