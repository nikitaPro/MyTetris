/**
 * 
 */
package square;

/**
 * @author NikitaNB
 *
 */
public class Figure {
public static final int SQUARE = 1;
public static final int LINE = 2;
public static final int ZIGZAG1 = 3;
public static final int ZIGZAG2 = 4;
public static final int CORNER1 = 5;
public static final int CORNER2 = 6;
public static final int TOBJECT = 7;
public static final int COUNTTYPE = 7;
//public static final int 
protected int x=-1;
protected int y=-1;
private Square figure[]=null;
	public Figure(int x, int y, int figureConst){
		this.x=x;
		this.y=y;
		figure = new Square[4];
		switch(figureConst){
		case SQUARE:
			figure[0]=new Square(1,1);
			figure[1]=new Square(2,1);
			figure[2]=new Square(1,2);
			figure[3]=new Square(2,2);
			break;
		case LINE:
			figure[0]=new Square(0,1);
			figure[1]=new Square(1,1);
			figure[2]=new Square(2,1);
			figure[3]=new Square(3,1);
			break;
		case ZIGZAG1:
			figure[0]=new Square(1,1);
			figure[1]=new Square(1,2);
			figure[2]=new Square(2,2);
			figure[3]=new Square(2,3);
			break;
		case ZIGZAG2:
			figure[0]=new Square(2,1);
			figure[1]=new Square(1,2);
			figure[2]=new Square(2,2);
			figure[3]=new Square(1,3);
			break;
		case CORNER1:
			figure[0]=new Square(1,1);
			figure[1]=new Square(2,1);
			figure[2]=new Square(1,2);
			figure[3]=new Square(1,3);
			break;
		case CORNER2:
			figure[0]=new Square(1,1);
			figure[1]=new Square(2,1);
			figure[2]=new Square(2,2);
			figure[3]=new Square(2,3);
			break;
		case TOBJECT:
			figure[0]=new Square(2,1);
			figure[1]=new Square(1,2);
			figure[2]=new Square(2,2);
			figure[3]=new Square(3,2);
			break;
		default: throw new IllegalArgumentException("Unknown figure; figureConst = "+figureConst);
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void down(){
		y++;
	}
	public void right(){
		x++;
	}
	public void left(){
		x--;
	}
	public void rotateR(){
		for(int i =0;i<figure.length;i++)
			//figure[i].transpon();
			figure[i].rotateOnClock();
	}
	public void rotateL(){
		for(int i =0;i<figure.length;i++)
			//figure[i].transpon();
			figure[i].rotateConClock();
	}
	public Square[] getSquares(){
		return figure;
	}
	public Member[][] getBitField(){
		Member[][] bits = new Member[figure.length][figure.length];
		for(int i=0;i<figure.length;i++){
			for(int j=0;j<figure.length;j++)
				bits[j][i] = null;
			for(int j=0;j<figure.length;j++)
				bits[figure[j].x][figure[j].y]=figure[j];
		}
		return bits;
	}
}
