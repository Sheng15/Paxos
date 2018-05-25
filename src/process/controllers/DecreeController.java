package process.controllers;


import process.AppGUI.panel.DecreePanel;
import process.views.App;


import java.awt.event.ActionListener;

public class DecreeController {
    private App app;
    private DecreePanel decreePanel;

    public DecreeController(App app, DecreePanel decreePanel) {
        this.app = app;
        this.decreePanel = decreePanel;
        init();
    }

    private void init() {
        ActionListener decreeAction = e -> {
            String decree = app.decreePanel.decreeBox.getText();
            if (app.hasAcceptedProposalFlag) {
                String message =  "You have already accepted a proposal!\n      You cannot write a new decree now!";
                app.showErrorMessage(message,null);
            }else{
                app.setDecree(decree);
                app.StatePanel.showDecree(decree);
            }
        };

        decreePanel.buttonDecree.addActionListener(decreeAction);
        decreePanel.decreeBox.addActionListener(decreeAction);
    }

}
