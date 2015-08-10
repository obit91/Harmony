package ohad;

import static uk.co.caprica.vlcj.player.Marquee.marquee;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;

public class MarqueeTest {
	
	private static EmbeddedMediaPlayer mediaPlayer;
    private static JFrame frame;
    private static JPanel controlsPanel;
    
    public static void main (String[] args) {
    	
    	DetectVLC.detection();
    	
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer =  mediaPlayerFactory.newEmbeddedMediaPlayer();
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);
        CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);

    	
    	initPanels2(canvas);
    }
	
	private static void initPanels2(Canvas canvas) {
		JPanel cp = new JPanel();
        cp.setBackground(Color.black);
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);

        MarqueeTest main = new MarqueeTest();
        controlsPanel = main.new ControlsPanel();
        cp.add(controlsPanel, BorderLayout.SOUTH);
        
        frame = new JFrame("vlcj Marquee Test");
        frame.setContentPane(cp);
        frame.setSize(800, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayer.release();
            }
        });
    }
	
	/**
	 * Playing with marequee.
	 */
	@SuppressWarnings("serial")
	private class ControlsPanel extends JPanel {

        JTextField xTextField = new JTextField(4);

        JTextField yTextField = new JTextField(4);

        @SuppressWarnings("rawtypes")
		JComboBox positionCombo = new JComboBox();

        JSlider opacitySlider = new JSlider(0, 255);

        JTextField textTextField = new JTextField(20);

        JCheckBox enableCheckBox = new JCheckBox("Enable");

        JButton applyButton = new JButton("Apply");

        JButton enableButton = new JButton("Enable");

        JButton disableButton = new JButton("Disable");

        @SuppressWarnings("unchecked")
		private ControlsPanel() {
            setBorder(new TitledBorder("Marquee Controls"));

            positionCombo.setModel(new PositionComboModel());

            JSeparator separator;

            add(new JLabel("X"));
            add(xTextField);
            add(new JLabel("Y"));
            add(yTextField);
            add(new JLabel("Position"));
            add(positionCombo);
            add(new JLabel("Opacity"));
            add(opacitySlider);
            add(new JLabel("Text"));
            add(textTextField);
            add(enableCheckBox);
            separator = new JSeparator(JSeparator.VERTICAL);
            separator.setPreferredSize(new Dimension(4, 20));
            add(separator);
            add(applyButton);
            separator = new JSeparator(JSeparator.VERTICAL);
            separator.setPreferredSize(new Dimension(4, 20));
            add(separator);
            add(enableButton);
            add(disableButton);

            opacitySlider.setPreferredSize(new Dimension(100, 16));

            opacitySlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                }
            });

            applyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    int x = -1;
                    int y = -1;
                    try {
                        x = Integer.parseInt(xTextField.getText());
                        y = Integer.parseInt(yTextField.getText());
                    }
                    catch(NumberFormatException e) {
                    }

                    marquee().text(textTextField.getText()).location(x, y).position((libvlc_marquee_position_e)positionCombo.getSelectedItem()).opacity(opacitySlider.getValue()).enable(enableCheckBox.isSelected()).apply(mediaPlayer);
                }
            });

            enableButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	mediaPlayer.enableMarquee(true);
                }
            });

            disableButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mediaPlayer.enableMarquee(false);
                }
            });
        }
    }
	@SuppressWarnings({ "serial", "rawtypes" })
	private class PositionComboModel extends DefaultComboBoxModel {

        @SuppressWarnings("unchecked")
		private PositionComboModel() {
            super(libvlc_marquee_position_e.values());
            insertElementAt(null, 0);
            setSelectedItem(null);
        }
    }
}
