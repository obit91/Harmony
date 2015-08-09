package ohad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;


/**
* Test demonstrating the {@link EmbeddedMediaListPlayerComponent}.
* <p>
* Leaving aside the standard Swing initialisation code, there are only <em>three</em> lines of vlcj
* code required to create a media player, add a media item to the play list, and play the media.
*/
public class BasicEmbeddedMediaListPlayerComponentTest extends VlcjTest {

    /**
     * Media player component.
     */
    private final EmbeddedMediaListPlayerComponent mediaListPlayerComponent;
    
    /**
     * buttons declarations.
     */
    
    private final JButton pauseButton;

    private final JButton rewindButton;

    private final JButton skipButton;
    
    private final JButton nextButton;

    /**
     * Application entry point.
     *
     * @param args
     */
    public static void main(String[] args) {
//        if(args.length != 1) {
//            System.out.println("Specify an mrl to add to the play-list");
//            System.exit(1);
////        }
//
//        final String mrl = args[0];

        setLookAndFeel();


      final String mrl = "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3";
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BasicEmbeddedMediaListPlayerComponentTest().start(mrl);
            }
        });
    }

    /**
     * Create a new test.
     */
    private BasicEmbeddedMediaListPlayerComponentTest() {
	
//        final String mrl2 = "C:\\Users\\Public\\Music\\Sample Music\\Maid with the Flaxen Hair.mp3";
//        final String mrl = "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3";
    	
        JFrame frame = new JFrame("vlcj Media Player Component Test");

        mediaListPlayerComponent = new EmbeddedMediaListPlayerComponent();
        
        /**
         * testing new songs in the list.. (the next/prev button).
         */
      
        frame.setContentPane(mediaListPlayerComponent);

        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        JPanel controlsPane = new JPanel();
        pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton);
        rewindButton = new JButton("Rewind");
        controlsPane.add(rewindButton);
        skipButton = new JButton("Skip");
        controlsPane.add(skipButton);
        nextButton = new JButton("Next");
        controlsPane.add(nextButton);
        frame.add(controlsPane, BorderLayout.SOUTH);
        
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaListPlayerComponent.getMediaPlayer().pause();
            }
        });

        rewindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mediaListPlayerComponent.getMediaPlayer().skip(-10000);
            }
        });

        skipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mediaListPlayerComponent.getMediaPlayer().skip(10000);
            }
        });
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mediaListPlayerComponent.getMediaPlayer().playNextSubItem();
            }
        });
    }

    /**
     * Start playing a movie.
     *
     * @param mrl mrl
     */
    private void start(String mrl) {
        // One line of vlcj code to add the media to the play-list...
        mediaListPlayerComponent.getMediaList().addMedia(mrl);
        // Another line of vlcj code to play the media...
        mediaListPlayerComponent.getMediaListPlayer().play();
    }
}