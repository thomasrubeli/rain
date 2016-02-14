package menu_screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionPanel extends JPanel implements ActionListener {
	private JSlider slider;
	private BlocksGame main;
	private JLabel label;
	private JButton stop;
	private JButton ok;
	private boolean play;
	private JSlider pan;
	private JButton effects;
	private boolean soundOn;
	private boolean userChoice;

	public OptionPanel(BlocksGame main) {
		this.main = main;
		setBackground(Color.gray);
		setFocusable(false);
		setPreferredSize(new Dimension(500, 500));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		slider = new JSlider(-20, 20);
		slider.setMaximumSize(new Dimension(400, 60));
		label = new JLabel();
		label.setText("Options");
		stop = new JButton("Music On");
		stop.addActionListener(this);
		effects = new JButton("Sound Effects On");
		effects.addActionListener(this);
		ok = new JButton("OK");
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		slider.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createVerticalStrut(45));
		stop.setAlignmentX(Component.CENTER_ALIGNMENT);
		effects.setAlignmentX(Component.CENTER_ALIGNMENT);
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		slider.setBorder(BorderFactory.createTitledBorder("Volume"));
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		pan = new JSlider(-20, 20);
		pan.setBorder(BorderFactory.createTitledBorder("Pan"));
		pan.setMaximumSize(new Dimension(400, 60));
		pan.setMajorTickSpacing(2);
		pan.setMinorTickSpacing(1);
		pan.setPaintTicks(true);
		pan.setPaintLabels(true);
		pan.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double pans = 0;
				JSlider slider = (JSlider) arg0.getSource();

				if (slider.getValueIsAdjusting()) {
					if (ClipMusic.getVolumeControl() != null) {
						ClipMusic.getPanControl().setValue(
								((float) (slider.getValue() * 0.05)));
					} else {
						if (MusicThread.getPanControl() != null) {
							MusicThread.getPanControl().setValue(
									((float) (slider.getValue() * 0.05)));
						}
					}
				}
			}
		});

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				JSlider slider = (JSlider) evt.getSource();

				if (slider.getValueIsAdjusting()) {
					if (!(((float) (slider.getValue() * 0.5)) > 6)) {
						if (ClipMusic.getVolumeControl() != null) {
							ClipMusic.getVolumeControl().setValue(
									((float) (slider.getValue() * 0.5)));
						} else {
							if (MusicThread.getVolumeControl() != null) {
								MusicThread.getVolumeControl().setValue(
										((float) (slider.getValue() * 0.5)));
							}
						}
					}
				}
			}
		});
		ok.addActionListener(this);
		add(label);
		add(Box.createVerticalStrut(45));
		add(slider);
		add(Box.createVerticalStrut(45));
		add(pan);
		add(Box.createVerticalStrut(45));
		add(stop);
		add(Box.createVerticalStrut(45));
		add(effects);
		add(Box.createVerticalStrut(45));
		add(ok);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == stop) {
			userChoice=true;
			main.toggleMusic();
			if (play) {
				stop.setText("Music On");

				play = false;
			} else {
				stop.setText("Music Off");
				play = true;
			}
		}
		if (e.getSource() == ok) {
			main.setMain();
		} else if (e.getSource() == effects) {
			if (soundOn) {
				main.turnEffectsOff();
				soundOn = false;
				effects.setText("Sound Effects On");
			} else {
				main.turnEffectsOn();
				soundOn = true;
				effects.setText("Sound Effects Off");
			}

		}

	}
	public void setMusicOn(){
		stop.setText("Music Off");
		play = true;
	}

	public void setdeadScreen() {
		label.setText("You are dead! Try again :)");
	}
	public boolean getMusicPlaying(){
		return play;
	}
	public boolean userChoice(){
		return userChoice;
	}

}