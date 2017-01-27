package strike.elements;

import java.awt.Graphics2D;

import resources.ColorSet;

public class Rocket extends Bullet {

	public Rocket(int x, int y) {
		super(x, y);
	}
	@Override
	public void draw(Graphics2D grf, int x, int y, int cellSize) {
		super.draw(grf, x, y, cellSize);
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		int xStartPoint = x+cellSize/2-3;
		int yStartPoint = y+7;
		//left wing
		xPoints[0]=xStartPoint;
		yPoints[0]=yStartPoint;
		xPoints[1]=xPoints[0]-3;
		yPoints[1]=yPoints[0]+3;
		xPoints[2]=xPoints[1];
		yPoints[2]=yPoints[1]+3;
		xPoints[3]=xPoints[2]+3;
		yPoints[3]=yPoints[2]-3;
		grf.setColor(ColorSet.colorBorder);
		grf.fillPolygon(xPoints,yPoints,4);
		xStartPoint = x+cellSize/2+4;
		yStartPoint = y+7;
		//right wing
		xPoints[0]=xStartPoint;
		yPoints[0]=yStartPoint;
		xPoints[1]=xPoints[0]+3;
		yPoints[1]=yPoints[0]+3;
		xPoints[2]=xPoints[1];
		yPoints[2]=yPoints[1]+3;
		xPoints[3]=xPoints[2]-3;
		yPoints[3]=yPoints[2]-3;
		grf.fillPolygon(xPoints,yPoints,4);
		xStartPoint = x+cellSize/2-3;
		yStartPoint = y+cellSize-3;
		// tail
		xPoints[0]=xStartPoint;
		yPoints[0]=yStartPoint;
		xPoints[1]=xPoints[0]-3;
		yPoints[1]=yPoints[0]+3;
		xPoints[2]=xPoints[1]+13;
		yPoints[2]=yPoints[1];
		xPoints[3]=xPoints[2]-3;
		yPoints[3]=yPoints[2]-3;
		grf.fillPolygon(xPoints,yPoints,4);
	}
}
