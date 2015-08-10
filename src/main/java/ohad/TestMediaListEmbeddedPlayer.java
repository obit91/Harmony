package ohad;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

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
public class TestMediaListEmbeddedPlayer extends DetectVLC {
	
	private static String folderPath = "C:\\temp";
    private static EmbeddedMediaPlayer mediaPlayer;
    private static MediaListPlayer mediaListPlayer;
    private static JFrame f = new JFrame("vlcj embedded media list player test");
    private static JSlider positionSlider;
    private static boolean mousePressedPlaying = false;
    private static JLabel timeLabel;
    
    public static void main(String[] args) throws Exception {
    	
    	detection();
    	    	
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer =  mediaPlayerFactory.newEmbeddedMediaPlayer();
        mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();;
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);
        CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);

        mediaPlayer.setVideoSurface(videoSurface);

        mediaListPlayer.setMediaPlayer(mediaPlayer); // <--- Important, associate the media player with the media list player
                
        initPanels(canvas);
        
        MediaList mediaList = mediaPlayerFactory.newMediaList();
        Stream(folderPath,mediaList);
        
        EmbeddedMediaListPlayerComponent test = new EmbeddedMediaListPlayerComponent();
        mediaListPlayer.setMediaList(mediaList);
        mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
    }
    
    /**
     * Initializes GUI.
     */
	private static void initPanels(Canvas canvas) {
		JPanel cp = new JPanel();
		JPanel positionPanel = new JPanel();
		JPanel topPanel = new JPanel();
				
		positionSlider = new JSlider();
		positionSlider.setMinimum(0);
		positionSlider.setMaximum(1000);
		positionSlider.setValue(0);
		positionSlider.setToolTipText("Position");
		
		timeLabel = new JLabel("hh:mm:ss");
        
        
        cp.setBackground(Color.black);
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);

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
        
        initButtons();
//        positionPanel.setLayout(new GridLayout(1, 1));
        positionPanel.add(positionSlider);
        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(positionPanel, BorderLayout.CENTER);
        f.add(topPanel, BorderLayout.NORTH);
        
        f.setVisible(true);
	}	
	/**
	 * Initializes buttons.
	 */
	private static void initButtons() {
		JButton pauseButton;
        JButton nextButton;
        JButton prevButton;
        JButton playButton;
        JButton stopButton;
        
        JPanel controlsPane = new JPanel();
        playButton = new JButton("Play");
        controlsPane.add(playButton);
        stopButton = new JButton("Stop");
        controlsPane.add(stopButton);
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
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mediaListPlayer.play();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mediaListPlayer.stop();
            }
        });
        positionSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mediaPlayer.isPlaying()) {
                    mousePressedPlaying = true;
                    mediaPlayer.pause();
                }
                else {
                    mousePressedPlaying = false;
                }
                setSliderBasedPosition();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setSliderBasedPosition();
                updateUIState();
            }
        });
	}
	/**
	 * Streams a folder into the media player.
	 */
	private static void Stream(String FOLDER_PATH, MediaList mediaList){        

	    File myDir = new File(FOLDER_PATH);
	    File[] files = myDir.listFiles(); 

	    if(myDir.exists() && myDir.isDirectory())
	    	for (int i=0; i < files.length ; i++)                                 
	    	   mediaList.addMedia(files[i].getPath());     
	}
	/**
     * Broken out position setting, handles updating mediaPlayer
     */
    private static void setSliderBasedPosition() {
        if(!mediaPlayer.isSeekable()) {
            return;
        }
        float positionValue = positionSlider.getValue() / 1000.0f;
        // Avoid end of file freeze-up
        if(positionValue > 0.99f) {
            positionValue = 0.99f;
        }
        mediaPlayer.setPosition(positionValue);
    }

    private static void updateUIState() {
        if(!mediaPlayer.isPlaying()) {
            // Resume play or play a few frames then pause to show current position in video
            mediaPlayer.play();
            if(!mousePressedPlaying) {
                try {
                    // Half a second probably gets an iframe
                    Thread.sleep(500);
                }
                catch(InterruptedException e) {
                    // Don't care if unblocked early
                }
                mediaPlayer.pause();
            }
        }
        long time = mediaPlayer.getTime();
        int position = (int)(mediaPlayer.getPosition() * 1000.0f);
        int chapter = mediaPlayer.getChapter();
        int chapterCount = mediaPlayer.getChapterCount();
        updateTime(time);
        updatePosition(position);
    }
    
    private static void updateTime(long millis) {
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        timeLabel.setText(s);
    }
    private static void updatePosition(int value) {
        // positionProgressBar.setValue(value);
        positionSlider.setValue(value);
    }
}