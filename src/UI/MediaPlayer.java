package UI;

import com.sun.lwuit.Button;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.CoordinateLayout;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class MediaPlayer extends Form implements ActionListener {

    private Button play, pause, next, stop;
    private Label lbl, backimg;
    private Player playerl;

    public MediaPlayer(String title) {
        super(title);
        init();
    }

    private void init() {
        setLayout(new CoordinateLayout(getWidth(), getHeight()));

        try {
            Image i = Image.createImage("/Img/backMusic.png");
            backimg = new Label(i);

            addComponent(backimg);

            play = new Button(Image.createImage("/Img/Play_20px.png"));
            pause = new Button(Image.createImage("/Img/Pause_20px.png"));
            stop = new Button(Image.createImage("/Img/Stop_20px.png"));
            next = new Button(Image.createImage("/Img/Next_20px.png"));

            play.setX(30);
            pause.setX(80);
            stop.setX(130);
            next.setX(180);

            play.setY(200);
            pause.setY(200);
            stop.setY(200);
            next.setY(200);

            addComponent(play);
            addComponent(pause);
            addComponent(stop);
            addComponent(next);

            play.addActionListener(this);
            pause.addActionListener(this);
            stop.addActionListener(this);
            next.addActionListener(this);

            lbl = new Label("Track Name......");
            lbl.setX(80);
            lbl.setY(150);

            addComponent(lbl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String[] songs = {"VOLARE.mp3", "HollaHolla.mp3", "SayJambo.mp3"};
    int playNo = 1;
    private boolean isPlay = false;

    public void actionPerformed(ActionEvent evt) {
        if (evt.getComponent() == play) {
            if (!isPlay) {

                try {
                    String songName = "";
                    if (playNo == 1) {
                        songName = songs[0];
                    } else if (playNo == 2) {
                        songName = songs[1];

                    } else if (playNo == 3) {
                        songName = songs[2];

                    }
                    System.out.println(songName + " is playing");
                    InputStream stream = getClass().getResourceAsStream("/Songs/" + songName);
                    playerl = Manager.createPlayer(stream, "audio/mpeg");

                    lbl.setText(songName);

                    try {
                        Thread thread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    playerl.prefetch();
                                    playerl.start();
                                } catch (MediaException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPlay = true;
            }
        } else if (evt.getComponent() == pause) {
            try {
                playerl.stop();
                isPlay = false;
            } catch (MediaException ex) {
                ex.printStackTrace();
            }
        } else if (evt.getComponent() == stop) {
            try {
                playerl.setMediaTime(0);
                playerl.stop();
                 isPlay = false;
            } catch (MediaException ex) {
                ex.printStackTrace();
            }
        } else if (evt.getComponent() == next) {
            if (playNo == 1) {
                playNo = 2;
            } else if (playNo == 2) {
                playNo = 3;
            } else if (playNo == 3) {
                playNo = 1;
            }
        }
    }

}
