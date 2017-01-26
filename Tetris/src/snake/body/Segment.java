package snake.body;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;
import square.Member;

public class Segment implements Member {
	protected int x;
	protected int y;
	protected Segment nextSegment;
	public Segment(int x, int y){
		this.x=x;
		this.y=y;
		nextSegment=null;
	}
	public Segment(int x, int y, Segment nextSeg){
		this.x=x;
		this.y=y;
		nextSegment=nextSeg;
	}
	
	public Segment getNextSegment() {
		return this.nextSegment;
	}
	public void setNextSegment(Segment nextSegment) {
		this.nextSegment = nextSegment;
	}
	public void moveFollow(int x, int y){
		if(nextSegment!=null)
			nextSegment.moveFollow(this.x,this.y);
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
		Color in = ColorSet.colorFigure;
		Color out = ColorSet.colorBorder;
		int cellStep = cellSize*20/100;
		int cellSizeInner = cellSize-2*cellStep;
		grf.setColor(out);
		grf.fillOval(x, y, cellSize, cellSize);
		grf.setColor(in);
		grf.fillOval(x+cellStep, y+cellStep, cellSizeInner, cellSizeInner);	
	}

}
