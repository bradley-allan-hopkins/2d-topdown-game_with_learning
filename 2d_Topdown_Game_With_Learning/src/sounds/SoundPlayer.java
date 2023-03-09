/**
 * Title: SoundPlayer.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 18, 2022
 * Used from geekforgeeks.org
 */
package sounds;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
/**
 * The Class SoundPlayer.
 */
public class SoundPlayer {

	/** The file path. */
	static String filePath;




	/** The current frame. */
	Long currentFrame;

	/** The time to stop. */
	Long timeToStop;

	/** The clip. */
	public Clip clip;

	/** The status. */
	// current status of clip
	String status;

	/** The audio input stream. */
	AudioInputStream audioInputStream;

	/**
	 * Instantiates a new sound player().
	 */
	public SoundPlayer() {}

	/**
	 * Sound player.
	 *
	 * @param filePath the file path
	 * @throws UnsupportedAudioFileException the unsupported audio file exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LineUnavailableException the line unavailable exception
	 */
	// constructor to initialize streams and clip
	public SoundPlayer(String filePath)
			throws UnsupportedAudioFileException,
			IOException, LineUnavailableException
	{
		SoundPlayer.filePath = filePath;
		// create AudioInputStream object
		audioInputStream =
				AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);
		
	}

	/**
	 * Finalize. Make sure clip is closed properly
	 */
	@Override
	public void finalize() {

		try {
			clip.close();
			audioInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Gets the variable "CurrentTime".
	 *
	 * @return long - CurrentTime
	 */
	public long getCurrentTime() {
		return clip.getMicrosecondPosition();
	}

	/**
	 * Checks if is loaded.
	 *
	 * @return true, if is loaded
	 */
	public boolean isLoaded() {
		if (this.clip == null) return false;
		if (this.clip.getFrameLength() > 0) return true;
		return false;
	}

	/**
	 * Checks if is time reached.
	 *
	 * @param time the time
	 * @return true, if is time reached
	 */
	public boolean isTimeReached(Long time) {
		if (this.clip.getMicrosecondPosition() >= time)return true;
		return false;
	}

	/**
	 * Jump.
	 *
	 * @param c the c
	 * @throws UnsupportedAudioFileException the unsupported audio file exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LineUnavailableException the line unavailable exception
	 */
	// Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException,
	LineUnavailableException
	{
		if (c > 0 && c < clip.getMicrosecondLength())
		{
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}


	/**
	 * Load the file from location 'file'
	 *
	 * @param file the file
	 */
	public void load(String file) {
		try {
			audioInputStream =
					AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());
			// create clip reference
			clip = AudioSystem.getClip();

			// open audioInputStream to the clip
			clip.open(audioInputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}



	}

	/**
	 * Load convert.
	 *
	 * @param file the file
	 */
	public void loadConvert(String file) {
		//Clip clip;
		try {
			InputStream in = new FileInputStream(file);
			System.out.println(in);
			//InputStream in = SoundPlayer.class.getResourceAsStream(file);
			//InputStream in = getClass().getResourceAsStream(file);
			InputStream bin = new BufferedInputStream(in);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bin);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false
					);
			audioInputStream = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			//dais.close();
			bin.close();
			ais.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will loop the clip for 'i' number of times
	 *
	 * @param i the i
	 */
	public void loopUntil(int i) {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.setLoopPoints(0, i);
		clip.start();
	}

	/**
	 * Pause.
	 */
	// Method to pause the audio
	public void pause()
	{
		if (status.equals("paused"))
		{
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame =
				this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	/**
	 * Play.
	 */
	public void play()
	{
		//start the clip
		clip.start();
		SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                // A GUI element to prevent the Clip's daemon Thread
                // from terminating at the end of the main()
                //JOptionPane.showMessageDialog(null, "Close to exit!");
            }
        });

		status = "play";
	}

	/**
	 * Play continuous.
	 */
	public void playContinuos() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
		SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                // A GUI element to prevent the Clip's daemon Thread
                // from terminating at the end of the main()
                //JOptionPane.showMessageDialog(null, "Close to exit!");
            }
        });
		status = "play";
	}

	/**
	 *This method will play the sound for a certain 'time'
	 * @param time the time
	 */
	public void playUntil(Long time) {
		//start the clip
		clip.start();
		for (Long i = (long) 0; i < this.clip.getMicrosecondLength();i++) {
			if (this.clip.getMicrosecondPosition() >=
					time) {
				clip.stop();
			}
		}
		status = "play";
	}

	/**
	 * Reset audio stream.
	 *
	 * @throws UnsupportedAudioFileException the unsupported audio file exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LineUnavailableException the line unavailable exception
	 */
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
	LineUnavailableException
	{
		audioInputStream = AudioSystem.getAudioInputStream(
				new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Restart.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LineUnavailableException the line unavailable exception
	 * @throws UnsupportedAudioFileException the unsupported audio file exception
	 */
	public void restart() throws IOException, LineUnavailableException,
	UnsupportedAudioFileException
	{
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}

	/**
	 * Resume audio.
	 *
	 * @throws UnsupportedAudioFileException the unsupported audio file exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LineUnavailableException the line unavailable exception
	 */
	public void resumeAudio() throws UnsupportedAudioFileException,
	IOException, LineUnavailableException
	{
		if (status.equals("play"))
		{
			System.out.println("Audio is already "+
					"being played");
			return;
		}
		//clip.close();
		//resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}

	/**
	 * Sets the volume.
	 *
	 * @param volume the new volume
	 */
	public void setVolume(float volume) {
		if(clip == null) return;
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = control.getMinimum();
		float maximum = control.getMaximum();
		float result = (range - maximum) * (1 - volume / 100.0f) + maximum;
		control.setValue(result);
	}

	/**
	 * Stop.
	 *
	 * @throws UnsupportedAudioFileException the unsupported audio file exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LineUnavailableException the line unavailable exception
	 */
	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException,
	IOException, LineUnavailableException
	{
		currentFrame = 0L;
		clip.stop();
		clip.close();
		clip = null;
	}
	
	/**
	 * The main method. Used for testing
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		try
		{
			//filePath = "src/resources/sounds/drop.wav";
			SoundPlayer audioPlayer =
					new SoundPlayer("src/resources/sounds/drop.wav");
			SoundPlayer audioPlayer1 = new SoundPlayer();
			audioPlayer1.load("src/resources/sounds/antsMarching.wav");
			//audioPlayer.playUntil(1700000L);
			//audioPlayer.play();
			audioPlayer.play();
			audioPlayer1.play();
			
			//audioPlayer.playContinuos();
			System.out.println("hell0");
		}

		catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();

		}
	}

}
