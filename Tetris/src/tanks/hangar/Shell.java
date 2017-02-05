package tanks.hangar;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import resources.AllImage;


public class Shell extends HeroTank {

	public Shell(int x, int y, int dir) {
		super(x, y);
		img=((ImageIcon)AllImage.SHELL).getImage();
		direction=dir;
		switch (dir){
		case HeroTank.DOWN: this.y++;break;
		case HeroTank.UP: this.y--;break;
		case HeroTank.RIGHT: this.x++;break;
		case HeroTank.LEFT: this.x--;break;
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}

	public void move(){
		switch (direction){
		case HeroTank.DOWN: moveDown();break;
		case HeroTank.UP: moveUp();break;
		case HeroTank.RIGHT:moveRight();break;
		case HeroTank.LEFT: moveLeft();break;
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}

}
