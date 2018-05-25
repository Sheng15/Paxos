package process.AppGUI.panel;

import process.views.ConstantUI;
import process.views.IconButton;

import javax.swing.*;
import java.awt.*;

public class DecreePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public IconButton buttonDecree;//////////////
    public JTextField decreeBox;

    private static Dimension preferredSize = new Dimension(ConstantUI.MAIN_WINDOW_WIDTH, 55);

    public DecreePanel() {
        initialize();
        addButtons();
    }

    private void initialize() {
        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setBackground(ConstantUI.PANEL_COLOR);
        this.setLayout(new FlowLayout());
    }

    private void addButtons() {

        buttonDecree = new IconButton(ConstantUI.ICON_Decree, ConstantUI.ICON_Decree, "Your Decree");
        decreeBox = new JTextField(39);
        this.add(decreeBox);
        this.add(buttonDecree);
    }

}
