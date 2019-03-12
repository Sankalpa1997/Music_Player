package UI;

import com.sun.lwuit.Button;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MultipartMessage;
import javax.wireless.messaging.TextMessage;

public class SMS_send extends Form implements ActionListener {

    private TextField no;
    private RadioButton r1;
    private RadioButton r2;
    private RadioButton r3;
    private Button send,receive;
    private Label l1, l2;
    private TextArea msg;
    private BoxLayout layout;
    private int mobNo;
    private Vector v;
    private Alert al;

    public SMS_send() {
        init();
    }

    private void init() {
        layout = new BoxLayout(BoxLayout.Y_AXIS);
        setLayout(layout);

        l1 = new Label("Mobile NO");
        l2 = new Label("Message");

        no = new TextField();
        no.setConstraint(TextField.NUMERIC);

        msg = new TextArea();
        msg.setRows(5);

        r1 = new RadioButton("text");
        r2 = new RadioButton("binary");
        r3 = new RadioButton("mms");

        r1.setGroup("x");
        r2.setGroup("x");
        r3.setGroup("x");

        send = new Button("SEND");
        receive = new Button("RECEIVE");

        addComponent(l1);
        addComponent(no);
        addComponent(l2);
        addComponent(msg);

        addComponent(r1);
        addComponent(r2);
        addComponent(r3);

        addComponent(send);
        addComponent(receive);
        send.addActionListener(this);
        receive.addActionListener(this);
        v = new Vector();
        al = new Alert(null);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getComponent() == send) {
            System.out.println("sending...");
            try {
                //send message
                String message = msg.getText();
                String number = no.getText();
                MessageConnection msgconnetion = (MessageConnection) Connector.open("sms://" + number + ":1234");

                if (r1.isSelected()) {
                    TextMessage tm = (TextMessage) msgconnetion.newMessage(MessageConnection.TEXT_MESSAGE);
                    tm.setPayloadText(message);
                    msgconnetion.send(tm);
                    msgconnetion.close();

                    System.out.println("txt send");
                } else if (r2.isSelected()) {

                    BinaryMessage bm = (BinaryMessage) msgconnetion.newMessage(MessageConnection.BINARY_MESSAGE);
                    bm.setPayloadData(message.getBytes());
                    msgconnetion.send(bm);
                    msgconnetion.close();

                    System.out.println("bin send");
                } else if (r3.isSelected()) {

                    MultipartMessage mpm = (MultipartMessage) msgconnetion.newMessage(MessageConnection.MULTIPART_MESSAGE);

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if (evt.getComponent()==receive) {
            
            new SMS_receive().show();
        }
    }

}
