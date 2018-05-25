package process.AppGUI.panel;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DisplayPanel extends JPanel  {


    private JTextArea text_message;

    public DisplayPanel() {
        Dimension preferredSize = new Dimension(335, 320);
        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setMaximumSize(preferredSize);
        setBackground(Color.white);

        text_message=new JTextArea(18,36);
        text_message.setEditable(false);
        text_message.setLineWrap(true);
        text_message.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(text_message);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10, 11, 300, 200);
        this.add(scroll);

        this.setBorder ( new TitledBorder( new EtchedBorder(), "Paxos Message" ) );

    }

    public void display(String string){
        this.text_message.append(string+"\n");
    }


}
