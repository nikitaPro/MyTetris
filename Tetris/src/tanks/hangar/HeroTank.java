package tanks.hangar;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import resources.AllImage;
import strike.elements.Element;

public class HeroTank extends Element {
	public final static int UP=0;
	public final static int RIGHT=1;
	public final static int DOWN=2;
	public final static int LEFT=3;
	protected int direction=0;
	protected Image img=((ImageIcon)AllImage.HERO_TANK).getImage();
	public HeroTank(int x, int y) {
		super(x, y);
	}
	@Override
	public void moveUp() {
		if(direction==UP)
			y--;
		else
			direction=UP;
	}

	@Override
	public void moveRight() {
		if(direction==RIGHT)
			x++;
		else
			direction=RIGHT;
	}

	@Override
	public void moveLeft() {
		if(direction==LEFT)
			x--;
		else
			direction=LEFT;
	}

	@Override
	public void moveDown() {
		if(direction==DOWN)
			y++;
		else
			direction=DOWN;
	}
	public int getDirection(){
		return direction;
	}
	@Override
	public void draw(Graphics2D grf, int x, int y, int cellSize) {
		Image imgR=rotateImage(img, cellSize,direction);
		grf.drawImage(imgR, x+(imgR.getWidth(null)-cellSize), y-(imgR.getHeight(null)-cellSize), null);
	}
	protected Image rotateImage(Image img, int cellSize,int dir){
		int w = img.getHeight(null);
		int h = img.getHeight(null);
		BufferedImage bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
        double x = (h-w)/2.0;
        double y = (w-h)/2.0;
	    AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(90*dir), w/2.0-1, h/2.0-1);
        bGr.drawImage(img, at, null);
	    bGr.dispose();
		return new ImageIcon(bimage).getImage();//*/bimage.getScaledInstance(cellSize,cellSize, Image.SCALE_DEFAULT);
	}
}
