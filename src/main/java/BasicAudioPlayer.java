import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class BasicAudioPlayer {

    private final AudioMediaPlayerComponent mediaPlayerComponent;
    private static final String testAudioMP3 = "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3";

    public static void main(String[] args) {
    	new NativeDiscovery().discover();
        BasicAudioPlayer BasicAudioPlayer = new BasicAudioPlayer();
        BasicAudioPlayer.start(testAudioMP3);
        try {
            Thread.currentThread().join();
        }
        catch(InterruptedException e) {
        }
    }

    private BasicAudioPlayer() {
        mediaPlayerComponent = new AudioMediaPlayerComponent();
        mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void stopped(MediaPlayer mediaPlayer) {
                exit(0);
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                exit(0);
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                exit(1);
            }
        });
    }

    private void start(String mrl) {
        mediaPlayerComponent.getMediaPlayer().playMedia(mrl);
    }

    private void exit(int result) {
        mediaPlayerComponent.release();
        System.exit(result);
    }
}