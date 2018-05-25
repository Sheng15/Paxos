package process.AppGUI.panel;

import process.views.ConstantUI;
import process.views.IconButton;

import javax.swing.*;
import java.awt.*;

public class HeadPanel extends JPanel {

    public IconButton buttonCreateRoom;
    public IconButton buttonJoin;
    public IconButton buttonKick;
    public IconButton buttonPrepare;
    public IconButton buttonVote;
    public IconButton buttonPatch1;
    public IconButton buttonPatch2;
    public JList peerList;


    private static Dimension preferredSize = new Dimension(ConstantUI.MAIN_WINDOW_WIDTH, 60);

    public HeadPanel() {
        initialize();
        addButtons();
    }

    private void initialize() {
        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setBackground(ConstantUI.PANEL_COLOR);
        this.setLayout(new GridLayout(1, 2));
    }

    private void addButtons() {
        JPanel panelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLeft.setBackground(ConstantUI.PANEL_COLOR);
        buttonPatch1 = new IconButton(ConstantUI.ICON_PATCH);
        buttonCreateRoom = new IconButton(ConstantUI.ICON_CREAT);
        buttonJoin = new IconButton(ConstantUI.ICON_JOIN);

        buttonPrepare = new IconButton(ConstantUI.ICON_PREPARE);///////////////////////
        buttonVote = new IconButton(ConstantUI.ICON_VOTE);

        panelLeft.add(buttonPatch1);
        panelLeft.add(buttonCreateRoom);
        panelLeft.add(buttonJoin);
        panelLeft.add(buttonPrepare);
        panelLeft.add(buttonVote);


        JPanel panelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRight.setBackground(ConstantUI.PANEL_COLOR);
        buttonPatch2 = new IconButton(ConstantUI.ICON_PATCH2);
        buttonKick = new IconButton(ConstantUI.ICON_KICK);
        DefaultListModel<String> peerListModel = new DefaultListModel<>();
        peerList = new JList();
        peerList.setModel(peerListModel);
        peerList.setVisible(true);
        panelRight.add(peerList);
        panelRight.add(buttonKick);
        panelRight.add(buttonPatch2);

        this.add(panelLeft);
        this.add(panelRight);

    }

    public void setButtonPrepareInvisiable(){
        this.buttonPrepare.setVisible(false);
    }

    public void setButtonVoteInvisiable(){
        this.buttonVote.setVisible(false);
    }
}
