package pool;

import javax.swing.Timer;

public interface Field {
	public Timer letsGo();
	public Timer pause();
	public Object getField();
	public void right();
	public void left();
	public void go();
	public void up();
}
