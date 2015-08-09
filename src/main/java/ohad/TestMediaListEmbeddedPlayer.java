package ohad;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

/**
 * Example showing how to combine a media list player with an embedded media
 * player.
 */
public class TestMediaListEmbeddedPlayer {

	
    public static void main(String[] args) throws Exception {
    	
    	DetectVLC.detection();
    	
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);
        CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);

        final EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(videoSurface);

        final MediaListPlayer mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();

        mediaListPlayer.addMediaListPlayerEventListener(new MediaListPlayerEventAdapter() {
            @Override
            public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item, String itemMrl) {
                System.out.println("nextItem()");
            }
        });

        mediaListPlayer.setMediaPlayer(mediaPlayer); // <--- Important, associate the media player with the media list player
                
        JPanel cp = new JPanel();
        cp.setBackground(Color.black);
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);

        JFrame f = new JFrame("vlcj embedded media list player test");
//        f.setIconImage(new ImageIcon(TestMediaListEmbeddedPlayer.class.getResource("/icons/vlcj-logo.png")).getImage());
        f.setContentPane(cp);
        f.setSize(800, 600);
        /**
         * unsure if both exits are required.
         */
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayer.release();
                mediaListPlayer.release();
                System.exit(0);
            }
        });
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initButtons(mediaPlayer, mediaListPlayer, f);
        
        f.setVisible(true);

        MediaList mediaList = mediaPlayerFactory.newMediaList();
        String[] options = {};
        mediaList.addMedia("C:\\Users\\Public\\Videos\\Sample Videos\\Wildlife.wmv", options);
        mediaList.addMedia("C:\\Users\\Public\\Videos\\Sample Videos\\SampleVideo_1080x720_1mb.mp4", options);

        mediaListPlayer.setMediaList(mediaList);
        mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

        mediaListPlayer.play();
                
        // This looping is just for purposes of demonstration, ordinarily you would
        // not do this of course
//        for(;;) {
//            Thread.sleep(500);
//            mediaPlayer.setChapter(3);
//
//            Thread.sleep(5000);
//            mediaListPlayer.playNext();
//        }

        //    mediaList.release();
        //    mediaListPlayer.release();
        //    mediaPlayer.release();
        //    mediaPlayerFactory.release();
    }

	private static void initButtons(final EmbeddedMediaPlayer mediaPlayer, final MediaListPlayer mediaListPlayer,
			JFrame f) {
		JButton pauseButton;
        JButton nextButton;
        JButton prevButton;
        
        JPanel controlsPane = new JPanel();
        pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton);
        nextButton = new JButton("Next");
        controlsPane.add(nextButton);
        prevButton = new JButton("Previous");
        controlsPane.add(prevButton);
        f.add(controlsPane, BorderLayout.SOUTH);
        
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaListPlayer.pause();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mediaListPlayer.playNext();
            }
        });
        
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mediaListPlayer.playPrevious();
            }
        });
	}
}