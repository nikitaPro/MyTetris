package snake.body;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;

public class Head extends Segment {
	private int direction=2;
	
	public Head(int x, int y, int dir) {
		super(x, y);
		direction = dir;
	}
	public Head(int x, int y) {
		super(x, y);
	}
	public Head(int x, int y, Segment nextSeg) {
		super(x, y, nextSeg);
	}
	@Override
	public void moveUp() {
		moveFollow(x, y);
		y--;
		direction=1;
	}

	@Override
	public void moveRight() {
		moveFollow(x, y);
		x++;
		direction=3;
	}

	@Override
	public void moveLeft() {
		moveFollow(x, y);
		x--;
		direction=4;
	}

	@Override
	public void moveDown() {
		moveFollow(x, y);
		y++;
		direction=2;
	}
	@Override
	public void insertCell(Graphics2D grf, int x, int y, int cellSize) {
		Color in = ColorSet.colorFigure;
		Color out = ColorSet.colorBorder;
		int cellStep = cellSize*20/100;
		int cellSizeInner = cellSize-2*cellStep;
		grf.setColor(out);
		grf.fillOval(x, y, cellSize, cellSize);
		grf.setColor(in);
		grf.fillOval(x+cellStep, y+cellStep, cellSizeInner, cellSizeInner);	
		grf.setColor(out);
		//eyes
		if(direction<=2){
			grf.fillOval(x+cellSize/2-5, y+cellSize/2-2, 4, 4);	
			grf.fillOval(x+cellSize/2+1, y+cellSize/2-2, 4, 4);	
		}
		else{
			grf.fillOval(x+cellSize/2-2, y+cellSize/2-5, 4, 4);	
			grf.fillOval(x+cellSize/2-2, y+cellSize/2+1, 4, 4);	
		}
		//tongue
		int x1=x+cellSize/2;
		int y1=y+cellSize/2;
		int length = cellSize/3;
		switch(direction){
		case 1://up
			grf.drawLine(x1, y, x1, y-length);
			grf.drawLine(x1+1, y, x1+2, y-length);
			break;
		case 2://down
			grf.drawLine(x1, y+cellSize, x1, y+cellSize+length);
			grf.drawLine(x1+1, y+cellSize, x1+2, y+cellSize+length);
			break;
		case 4://left
			grf.drawLine(x, y1, x-length, y1);
			grf.drawLine(x, y1+1, x-length, y1+2);
			break;
		case 3://right
			grf.drawLine(x+cellSize, y1, x+cellSize+length, y1);
			grf.drawLine(x+cellSize, y1+1, x+cellSize+length, y1+2);
			break;
		}
		
	}
}
