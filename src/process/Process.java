package process;

import process.views.LoginFrame;

import javax.swing.*;

public class Process {
    public static void main(String arg[]) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
