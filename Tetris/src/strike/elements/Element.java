package strike.elements;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;
import square.Member;

public class Element implements Member {

	protected int x;
	protected int y;
	public Element(int x, int y){
		this.x=x;
		this.y=y;
	}
	@Override
	public void moveUp() {
		y--;
	}

	@Override
	public void moveRight() {
		x++;
	}

	@Override
	public void moveLeft() {
		x--;
	}

	@Override
	public void moveDown() {
		y++;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	@Override
	public void insertCell(Graphics2D grf, int x, int y, int cellSize) {
		Color in = ColorSet.colorApple;
		Color out = ColorSet.colorBorder;
		int cellStep = cellSize*20/100;
		int cellSizeInner = cellSize-2*cellStep;
		grf.setColor(out);
		grf.fillRect(x, y, cellSize, cellSize);
		grf.setColor(in);
		grf.fillRect(x+cellStep, y+cellStep, cellSizeInner, cellSizeInner);	
	}

}
