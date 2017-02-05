package frames;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import pool.Field;
import square.Member;
import tanks.tankField.TankField;

public class TankFrame extends SnakeFrame {

	protected int coubSize = 26;
	
	public TankFrame(Field<Member [][]> field) {
		super(field);
		this.setTitle("JTank");
		this.setVisible(true);
		Theme3();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch(code){
				case KeyEvent.VK_CONTROL:
					if(!isPause())
						((TankField)field).fire();
					break;
				}
			}
		});
	}
}
