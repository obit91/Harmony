package ohad;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener;

/**
* Encapsulation of an embedded media list player.
* <p>
* This component extends the {@link EmbeddedMediaPlayerComponent} to incorporate a
* {@link MediaListPlayer} and an associated {@link MediaList}.
*/
@SuppressWarnings("serial")
public class EmbeddedMediaListPlayerComponent extends EmbeddedMediaPlayerComponent implements MediaListPlayerEventListener {

    /**
     * Media list player.
     */
    private final MediaListPlayer mediaListPlayer;

    /**
     * Media list.
     */
    private final MediaList mediaList;

    /**
     * Construct a media list player component.
     */
    public EmbeddedMediaListPlayerComponent() {
        // Create the native resources
        MediaPlayerFactory mediaPlayerFactory = getMediaPlayerFactory();
        mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
        mediaList = mediaPlayerFactory.newMediaList();
        mediaListPlayer.setMediaList(mediaList);
        mediaListPlayer.setMediaPlayer(getMediaPlayer());
        // Register listeners
        mediaListPlayer.addMediaListPlayerEventListener(this);
        // Sub-class initialisation
        onAfterConstruct();
    }

    /**
     * Get the embedded media list player reference.
     * <p>
     * An application uses this handle to control the media player, add listeners and so on.
     *
     * @return media list player
     */
    public final MediaListPlayer getMediaListPlayer() {
        return mediaListPlayer;
    }

    /**
     * Get the embedded media list reference.
     *
     * @return media list
     */
    public final MediaList getMediaList() {
        return mediaList;
    }

    @Override
    protected final void onBeforeRelease() {
        onBeforeReleaseComponent();
        mediaListPlayer.release();
        mediaList.release();
    }

    /**
     * Template method invoked before the media list player is released.
     */
    protected void onBeforeReleaseComponent() {
    }

    // === MediaListPlayerEventListener =========================================

    public void played(MediaListPlayer mediaListPlayer) {
    }

    public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item, String itemMrl) {
    }

    public void stopped(MediaListPlayer mediaListPlayer) {
    }

    public void mediaMetaChanged(MediaListPlayer mediaListPlayer, int metaType) {
    }

    public void mediaSubItemAdded(MediaListPlayer mediaListPlayer, libvlc_media_t subItem) {
    }

    public void mediaDurationChanged(MediaListPlayer mediaListPlayer, long newDuration) {
    }

    public void mediaParsedChanged(MediaListPlayer mediaListPlayer, int newStatus) {
    }

    public void mediaFreed(MediaListPlayer mediaListPlayer) {
    }

    public void mediaStateChanged(MediaListPlayer mediaListPlayer, int newState) {
    }
}