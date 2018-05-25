package process.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private IconButton loginButton;
    private TextField processNameField;
    private TextField hostField;

    private static Dimension preferredSize = new Dimension(400, 150);

    public LoginFrame() {
        super("Login");
        initialize();
        addPanel();
        run();

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initialize() {
        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setLocationRelativeTo(null); // center
        this.setBackground(ConstantUI.PANEL_COLOR);
    }

    private void addPanel() {
        loginButton = new IconButton(ConstantUI.ICON_LOGIN);
        hostField = new TextField();
        processNameField = new TextField();

        hostField.setBounds(100, 30, 150, 20);
        processNameField.setBounds(100, 70, 150, 20);
        loginButton.setBounds(290, 40, 40, 40);

        JLabel hostLabel = new JLabel("Host:", JLabel.RIGHT);
        hostLabel.setLabelFor(hostField);
        hostLabel.setBounds(10,30,80,18);
        hostField.setText("localhost");

        JLabel usernameLabel = new JLabel("Process Name:", JLabel.RIGHT);
        usernameLabel.setLabelFor(processNameField);
        usernameLabel.setBounds(10,70,80,18);


        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(ConstantUI.PANEL_COLOR);
        loginPanel.setLayout(null);

        loginPanel.add(hostLabel);
        loginPanel.add(usernameLabel);

        loginPanel.add(hostField);
        loginPanel.add(processNameField);
        loginPanel.add(loginButton);
        this.add(loginPanel);

        SwingUtilities.invokeLater(() -> processNameField.requestFocus());
    }

    private void run() {

        ActionListener loginAction = e -> {
            String userName = processNameField.getText();
            String host = hostField.getText();
            if (host.equals("")) {
                JOptionPane.showMessageDialog(this, "Enter a hostname!");
                System.out.println("Enter your hostname!");
            } else if (userName.equals("")) {
                JOptionPane.showMessageDialog(this, "Enter your username!");
                System.out.println("Enter your processName!");
            } else {
                new App(userName, host);
                this.dispose();
            }
        };

        loginButton.addActionListener(loginAction);
        processNameField.addActionListener(loginAction);
    }
}
