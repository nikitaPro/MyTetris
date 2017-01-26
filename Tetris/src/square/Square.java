/**
 * 
 */
package square;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;

/**
 * @author NikitaNB
 *
 */
public class Square implements Member {
	protected int x=-1;
	protected int y=-1;
	
	public Square(int x, int y){
		this.x=x;
		this.y=y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void transpon(){
		int k=x;
		x=y;
		y=k;
	}
	public void rotateOnClock(){
		int x1=x;
		x=3-y;
		y=x1;
	}
	public void rotateConClock(){
		int y1=y;
		y=3-x;
		x=y1;
	}
	@Override
	public void moveUp() {
		y++;
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
		throw new UnsupportedOperationException();
	}
	@Override
	public void draw(Graphics2D grf, int x, int y, int cellSize ){
		Color in = ColorSet.colorFigure;
		Color out = ColorSet.colorBorder;
		int cellStep = cellSize*20/100;
		int cellSizeInner = cellSize-2*cellStep;
		grf.setColor(out);
		grf.fillRect(x, y, cellSize, cellSize);
		grf.setColor(in);
		grf.fillRect(x+cellStep, y+cellStep, cellSizeInner, cellSizeInner);			
	}
}
