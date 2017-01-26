package square;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;

public class BorderSquare implements Member{
	protected int x=-1;
	protected int y=-1;
	
	public BorderSquare(int x, int y){
		this.x=x;
		this.y=y;
	}
	@Override
	public void moveUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveRight() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveLeft() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveDown() {
		throw new UnsupportedOperationException();
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
	public void draw(Graphics2D grf, int x, int y, int cellSize) {
		Color in = ColorSet.colorFieldBorder;
		Color out = ColorSet.colorBorder;
		int cellStep = cellSize*20/100;
		int cellSizeInner = cellSize-2*cellStep;
		grf.setColor(out);
		grf.fillRect(x, y, cellSize, cellSize);
		grf.setColor(in);
		grf.fillRect(x+cellStep, y+cellStep, cellSizeInner, cellSizeInner);		
	}

}
