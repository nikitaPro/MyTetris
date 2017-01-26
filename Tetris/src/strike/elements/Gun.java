package strike.elements;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;

public class Gun extends Element {

	public Gun(int x, int y) {
		super(x, y);
	}
	@Override
	public void draw(Graphics2D grf, int x, int y, int cellSize) {
		Color in = ColorSet.colorFigure;
		Color out = ColorSet.colorBorder;
		int cellStep = cellSize*20/100;
		int cellSizeInner = cellSize-2*cellStep;
		grf.setColor(out);
		grf.fillRect(x, y, cellSize, cellSize);
		grf.setColor(in);
		//tower
		grf.fillOval(x+cellStep, y+cellStep, cellSizeInner, cellSizeInner);	
		int x1=x+cellSize/2-1;
		int y1=y-cellSize/2;
		int length = cellSize;
		//trunk body
		grf.fillRect(x1, y1, 3, length);
		//tracks
		grf.fillRect(x+1, y+1, 2, cellSize-1);
		grf.fillRect(x+cellSize-3, y+1, 2, cellSize-1);
	}
}
