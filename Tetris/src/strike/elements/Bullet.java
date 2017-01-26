/**
 * 
 */
package strike.elements;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ColorSet;

/**
 * @author NikitaNB
 *
 */
public class Bullet extends Element {

	public Bullet(int x, int y) {
		super(x, y);
	}
	@Override
	public void draw(Graphics2D grf, int x, int y, int cellSize) {
		Color in = ColorSet.colorApple;
		Color out = ColorSet.colorBorder;
		int[] xPoints = new int[6];
		int[] yPoints = new int[6];
		int xStartPoint = x+cellSize/2;
		int yStartPoint = y;
		xPoints[0]=xStartPoint;
		yPoints[0]=yStartPoint;
		xPoints[1]=xPoints[0]-3;
		yPoints[1]=yPoints[0]+3;
		xPoints[2]=xPoints[1];
		yPoints[2]=yPoints[1]+cellSize-3;
		xPoints[3]=xPoints[2]+7;
		yPoints[3]=yPoints[2];
		xPoints[4]=xPoints[3];
		yPoints[4]=yPoints[3]-cellSize+3;
		xPoints[5]=xPoints[4]-3;
		yPoints[5]=yPoints[4]-3;
		grf.setColor(out);
		grf.fillPolygon(xPoints, yPoints, 6);
		grf.setColor(in);
		grf.fillRect(xPoints[1]+2, yPoints[1]+2, 3, cellSize-7);
	}

}
