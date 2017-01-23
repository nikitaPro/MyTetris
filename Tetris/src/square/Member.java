package square;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Member {
	public void moveUp();
	public void moveRight();
	public void moveLeft();
	public void moveDown();
	public int getX();
	public int getY();
	public void insertCell(Graphics2D grf, int x, int y, int cellSize);
}
