package UI;

import com.sun.lwuit.Button;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.AlertType;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.MultipartMessage;
import javax.wireless.messaging.TextMessage;

public class SMS_receive extends Form implements ActionListener {

    private Button send;
    private Label l, l2;

    public SMS_receive() {

        init();
    }

    private void init() {

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        send = new Button("SEND");
        send.addActionListener(this);

        try {
            MessageConnection mc = (MessageConnection) Connector.open("sms://:1234");
            mc.setMessageListener(new MessageListener() {

                public void notifyIncomingMessage(MessageConnection mc) {
                    try {
                        Message msgSuper = mc.receive();

                        if (msgSuper instanceof TextMessage) {
                            TextMessage tm = (TextMessage) mc.receive();
                            Label l = new Label(tm.getPayloadText());
                            Label l2 = new Label(tm.getTimestamp() + "");

                            addComponent(l);
                            addComponent(l2);
                        } else if (msgSuper instanceof BinaryMessage) {
                            BinaryMessage bm = (BinaryMessage) mc.receive();
                            byte[] msg = bm.getPayloadData();
                            String s = new String(msg);

                            Label l = new Label(s);
                            Label l2 = new Label(bm.getTimestamp() + "");

                            addComponent(l);
                            addComponent(l2);

                        } else if (msgSuper instanceof MultipartMessage) {
                            MultipartMessage mms = (MultipartMessage) mc.receive();
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        addComponent(send);
    }

    public void actionPerformed(ActionEvent evt) {

        if (evt.getComponent() == send) {
            new SMS_send().show();
        }

    }
}
