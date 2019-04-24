package viewcontroller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JTextArea currentMessageStateTextArea;
    private JTextField messageInputTextField;
    private JButton encryptMessageButton;
    private JButton decryptMessageButton;

    public boolean ENCRYPT_PRESSED = false;

    public View(ActionListener al) {
        init(al);
    }

    private void init(ActionListener al) {
        // Components
        currentMessageStateTextArea = new JTextArea("Message", 10, 20);
        messageInputTextField = new JTextField("Enter Message",2);
        encryptMessageButton = new JButton("Encrypt");
        encryptMessageButton.addActionListener(al);
        decryptMessageButton = new JButton("Decrypt");
        decryptMessageButton.addActionListener(al);

        // Frame
        frame = new JFrame("RSA Encryption");
        frame.setSize(new Dimension(800, 400));
        frame.setLayout(new BorderLayout());
        frame.add(messageInputTextField, BorderLayout.CENTER);
        frame.add(encryptMessageButton, BorderLayout.WEST);
        frame.add(decryptMessageButton, BorderLayout.EAST);
        frame.add(currentMessageStateTextArea, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getTextField() {
        return messageInputTextField;
    }

    public JTextArea getTextArea() {
        return currentMessageStateTextArea;
    }

    public JButton getEncryptMessageButton() {
        return encryptMessageButton;
    }

    public JButton getDecryptMessageButton() {
        return decryptMessageButton;
    }
}
