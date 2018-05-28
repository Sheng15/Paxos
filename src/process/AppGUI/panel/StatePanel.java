package process.AppGUI.panel;

import process.views.ConstantUI;
import shared.Proposal;

import javax.swing.*;
import java.awt.*;

public class StatePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField indexField;
    private JTextField voteNumField;
    private JTextField prepareNumField;
    private JTextField promiseNumField;
    private JTextField ballotNumField;
    private JTextField decreeField;
    private JLabel stateLabel;
    private JLabel acceptedLabel;
    private JLabel indexLabel;
    private JLabel voteNumLabel;
    private JLabel prepareNumLabel;
    private JLabel promiseNumLabel;
    private JLabel ballotNumLabel;
    private JLabel decreeLabel;



    private static Dimension preferredSize = new Dimension(85, 320);

    public StatePanel() {
        initialize();
        addPanels();
    }

    public void initialize() {
        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setBackground(ConstantUI.PANEL_COLOR);
        //this.setLayout(new FlowLayout(FlowLayout.LEADING));
        //this.setLayout(new GridLayout(3,2));

    }

    public void addPanels() {

        indexField= new JTextField();
        voteNumField= new JTextField();
        prepareNumField = new JTextField();
        promiseNumField = new JTextField();
        ballotNumField = new JTextField();
        decreeField = new JTextField();

        indexField.setEditable(false);
        voteNumField.setEditable(false);
        prepareNumField.setEditable(false);
        promiseNumField.setEditable(false);
        ballotNumField.setEditable(false);
        decreeField.setEditable(false);

        indexField.setHorizontalAlignment(JTextField.CENTER);
        voteNumField.setHorizontalAlignment(JTextField.CENTER);
        prepareNumField.setHorizontalAlignment(JTextField.CENTER);
        promiseNumField.setHorizontalAlignment(JTextField.CENTER);
        ballotNumField.setHorizontalAlignment(JTextField.CENTER);
        decreeField.setHorizontalAlignment(JTextField.CENTER);


        stateLabel = new JLabel("Current State", JLabel.RIGHT);
        stateLabel.setBounds(0,00,80,18);

        acceptedLabel = new JLabel("Accepted Proposal", JLabel.RIGHT);
        acceptedLabel.setBounds(0,00,80,18);

        indexLabel = new JLabel("Index:", JLabel.RIGHT);
        indexLabel.setLabelFor(indexField);
        indexLabel.setBounds(0,00,80,18);

        voteNumLabel = new JLabel("VoteNum:", JLabel.RIGHT);
        voteNumLabel.setLabelFor(voteNumField);
        voteNumLabel.setBounds(0,00,80,18);

        prepareNumLabel = new JLabel("PrepareNum:", JLabel.RIGHT);
        prepareNumLabel.setLabelFor(indexField);
        prepareNumLabel.setBounds(10,30,80,18);

        promiseNumLabel = new JLabel("PromiseNum:", JLabel.RIGHT);
        promiseNumLabel.setLabelFor(promiseNumField);
        promiseNumLabel.setBounds(10,30,80,18);

        ballotNumLabel = new JLabel("BallotNum:", JLabel.RIGHT);
        ballotNumLabel.setLabelFor(ballotNumField);
        ballotNumLabel.setBounds(10,30,80,18);

        decreeLabel = new JLabel("Decree:", JLabel.RIGHT);
        decreeLabel.setLabelFor(decreeField);
        decreeLabel.setBounds(10,30,80,18);

        JPanel statePanel = new JPanel();
        statePanel.setBackground(ConstantUI.PANEL_COLOR);
        statePanel.setLayout(new GridLayout(13, 1));

        statePanel.add(stateLabel);

        statePanel.add(indexLabel);
        statePanel.add(indexField);

        statePanel.add(voteNumLabel);
        statePanel.add(voteNumField);

        statePanel.add(prepareNumLabel);
        statePanel.add(prepareNumField);

        statePanel.add(promiseNumLabel);
        statePanel.add(promiseNumField);

        //statePanel.add(acceptedLabel);

        statePanel.add(ballotNumLabel);
        statePanel.add(ballotNumField);

        statePanel.add(decreeLabel);
        statePanel.add(decreeField);


        this.add(statePanel);

    }

    public void showDecree(String decree){
        this.decreeField.setText(decree);
    }

    public void showIndex(int index){
        this.indexField.setText(String.valueOf(index));
    }

    public void showVoteNum(int voteNum){
        this.voteNumField.setText(String.valueOf(voteNum));
    }

    public void showPrepareNum(int prepareNum){
        this.prepareNumField.setText(String.valueOf(prepareNum));
    }

    public void showPromiseNum(int promiseNum){
        this.promiseNumField.setText(String.valueOf(promiseNum));
    }

    public void showAcceptProposal(Proposal proposal){
        this.ballotNumField.setText(String.valueOf(proposal.getBallotNum()));
        this.decreeField.setText(proposal.getDecree());
    }

    public void setInvisiable(){
        indexField.setVisible(false);
        voteNumField.setVisible(false);
        prepareNumField.setVisible(false);
        promiseNumField.setVisible(false);
        ballotNumField.setVisible(false);
        decreeField.setVisible(false);
        stateLabel.setVisible(false);
        acceptedLabel.setVisible(false);
        indexLabel.setVisible(false);
        voteNumLabel.setVisible(false);
        prepareNumLabel.setVisible(false);
        promiseNumLabel.setVisible(false);
        ballotNumLabel.setVisible(false);
        decreeLabel.setVisible(false);
    }
}
