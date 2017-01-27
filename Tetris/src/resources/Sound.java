/**
 * 
 */
package resources;

/**
 * @author NikitaNB
 *
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Cloneable{
	private boolean released = false;
	private Clip clip = null;
	private FloatControl volumeC = null;
	private boolean playing = false;
	private String path;
	
	public Sound(String path) {
		this.path=path;
		init(path);
	}
	private void init(String s){
		try {
			InputStream is= getClass().getResourceAsStream(s);
			AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.addLineListener(new Listener());
			volumeC = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			released = true;
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
			exc.printStackTrace();
			released = false;
		}
	}

	/**true ���� ���� ������� ��������, false ���� ��������� ������*/
	public boolean isReleased() {
		return released;
	}
	
	/**������������� �� ���� � ������ ������*/
	public boolean isPlaying() {
		return playing;
	}

	/**������
	  breakOld ���������� ���������, ���� ���� ��� ��������
	  ���� reakOld==true, � ���� ����� ������� � ������� ������
	  ����� ������ �� ���������
	*/
	public void play(boolean breakOld) {
		if (released) {
			if (breakOld) {
				clip.stop();
				clip.setFramePosition(0);
				clip.start();
				playing = true;
			} else if (!isPlaying()) {
				clip.setFramePosition(0);
				clip.start();
				playing = true;
			}
		}
	}
	
	/**�� �� �����, ��� � play(true)*/
	public void play() {
		play(true);
	}
	/**�� �� �����, ��� � play(false)*/
	public void playWithoutBreak(){
		play(false);
	}
	public void multyPlay(){
		Sound sound = new Sound (path);
		sound.setVolume(sound.getVolume());
		sound.play();
	}
	/**������������� ���������������*/
	public void stop() {
		if (playing) {
			clip.stop();
		}
	}

	/**��������� ���������
	  x ����� ���� � �������� �� 0 �� 1 (�� ������ ������ � ������ ��������)
	*/
	public void setVolume(float x) {
		if (x<0) x = 0;
		if (x>1) x = 1;
		float min = volumeC.getMinimum();
		float max = volumeC.getMaximum();
		volumeC.setValue((max-min)*x+min);
	}
	
	/**���������� ������� ��������� (����� �� 0 �� 1)*/
	public float getVolume() {
		float v = volumeC.getValue();
		float min = volumeC.getMinimum();
		float max = volumeC.getMaximum();
		return (v-min)/(max-min);
	}

	/**���������� ��������� ������������ �����*/
	public void join() {
		if (!released) return;
		synchronized(clip) {
			try {
				while (playing) clip.wait();
			} catch (InterruptedException exc) {}
		}
	}
	
	/**����������� �����, ��� ��������*/
	public static Sound playSound(String s) {
		Sound snd = new Sound(s);
		snd.play();
		return snd;
	}

	private class Listener implements LineListener {
		public void update(LineEvent ev) {
			if (ev.getType() == LineEvent.Type.STOP) {
				playing = false;
				synchronized(clip) {
					clip.notify();
				}
			}
		}
	}
}