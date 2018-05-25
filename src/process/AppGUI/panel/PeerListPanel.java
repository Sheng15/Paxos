package process.AppGUI.panel;

import process.views.ConstantUI;

import javax.swing.*;
import java.awt.*;

public class PeerListPanel extends JPanel {

    public JList peerList;
    public DefaultListModel<String> peerListModel;

    private static Dimension preferredSize = new Dimension(130, 320);

    public PeerListPanel() {
        initialize();
        addPeerList();
    }

    public void initialize() {
        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setBackground(ConstantUI.PANEL_COLOR);
    }

    public void addPeerList() {
        peerListModel = new DefaultListModel<>();
        peerList = new JList<String>();
        peerList.setModel(peerListModel);
        peerList.setPreferredSize(new Dimension(110, 300));
        peerList.setBackground(ConstantUI.MESSAGEBOX_COLOR);
        this.add(peerList);
    }
}
