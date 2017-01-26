package snake.body;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;

public class Apple extends Segment implements Soft {

	public Apple(int x, int y) {
		super(x, y);
	}
	@Override
	public void draw(Graphics2D grf, int x, int y, int cellSize){
		Color in = ColorSet.colorApple;
		Color out = ColorSet.colorBorder;
		int cellStep = cellSize*20/100;
		int cellSizeInner = cellSize-2*cellStep;
		grf.setColor(out);
		grf.fillRoundRect(x, y, cellSize, cellSize,cellSize,cellSize);
		grf.setColor(in);
		grf.fillRoundRect(x+cellStep, y+cellStep, cellSizeInner, cellSizeInner,cellSizeInner,cellSizeInner);		
		grf.setColor(out);
		int x1=x+cellSize/2;
		int length = cellSize/3;
		grf.drawLine(x1, y, x1, y-length);
		grf.drawLine(x1+1, y-2, x1+1, y-length);
	}
}
