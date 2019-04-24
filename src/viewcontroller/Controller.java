package viewcontroller;

import model.RsaEncryption;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private RsaEncryption rsaEncryption;
    private View view;

    public Controller(RsaEncryption rsaEncryption) {
        this.rsaEncryption = rsaEncryption;
        this.view = new View(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == view.getEncryptMessageButton()) {
                    rsaEncryption.setMessage(view.getTextField().getText());
                    view.getTextArea().setText(rsaEncryption.encryptMessage().replaceAll(",",""));
                } else if (e.getSource() == view.getDecryptMessageButton()) {
                    view.getTextArea().setText(rsaEncryption.decryptMessage());
                }
            }
        });
    }


}
