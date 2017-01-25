package pool;

import javax.swing.Timer;

public interface Field <T>{
	public Timer letsGo();
	public Timer pause();
	public T getField();
	public void right();
	public void left();
	public void go();
	public void up();
	public void down();
	public int getScore();
	public String getBonusName();
}
