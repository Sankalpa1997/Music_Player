
package Midlet;

import UI.MediaPlayer;
import UI.SMS_send;
import com.sun.lwuit.Display;
import javax.microedition.midlet.*;

public class Midlet extends MIDlet {

    private MediaPlayer mediaPlayer;
    private SMS_send sms;
  

    private void init() {
        Display.init(this);
        mediaPlayer = new MediaPlayer("");
        sms = new SMS_send();
    }
    
    public void startApp() {
        init();
//        mediaPlayer.show();
        sms.show();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
