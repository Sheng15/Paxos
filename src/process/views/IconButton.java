package process.views;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {


    private static final long serialVersionUID = 1L;
    private ImageIcon iconEnable, iconDisable;
    private String tip;

    public IconButton(ImageIcon iconDefault) {
        super(iconDefault);

        initialize();
    }


    public IconButton(ImageIcon iconDefault, ImageIcon iconEnable, String tip) {
        super(iconDefault);

        this.iconEnable = iconEnable;
        this.tip = tip;

        initialize();
        setup();
    }

    private void initialize() {
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setFocusable(false);
        this.setContentAreaFilled(false);
    }

    private void setup() {
        this.setPressedIcon(iconEnable);
        this.setRolloverIcon(iconEnable);

        if (!tip.equals("")) {
            this.setToolTipText(tip);
        }
    }


}
