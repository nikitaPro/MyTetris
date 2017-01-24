/**
 * 
 */
package strike.pool;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import pool.Field;
import square.BorderSquare;
import square.Member;
import square.Square;
import strike.elements.Element;
import tetrisPool.Score;

/**
 * @author NikitaNB
 *
 */
public class StrikeField implements Field<Member[][]> {
	private String recordsFilePath = "Records/Strike.txt";
	private Score score= new Score(recordsFilePath,100); 
	private Timer timer = score.getTimer();
	protected Member[][] field = null;
	private int column = 20;
	private int line = 30;
	private int startPosition=line-2;
	private Element point = new Element(column/2,startPosition);
	private ArrayList<Member> bullets= new ArrayList<Member>();
	private ArrayList<Member> stones= new ArrayList<Member>();

	public StrikeField(){
		init();
	}
	private void init(){
		field = new Member[column][line];
		for(int i=0;i<line;i++){
			field[column-1][i]= new BorderSquare(column-1,i);
			field[0][i]= new BorderSquare(0,i);
		}
		for(int i=0;i<column;i++){
			field[i][line-1]= new BorderSquare(i,line-1);
			field[i][0]=new BorderSquare(i,0);
		}
		setPoint(point);
	}
	private void setPoint(Member b){
		field[b.getX()][b.getY()]=b;
	}
	private void delPoint(Member b){
		field[b.getX()][b.getY()]=null;
	}
	@Override
	public Timer letsGo() {
		timer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				go();
			}
		});
		timer.start();
		return timer;
	}

	@Override
	public Timer pause() {
		if(timer.isRunning()) timer.stop();
		else timer.start();
		return timer;
	}

	@Override
	public Member[][] getField() {
		return field;
	}

	@Override
	public void right() {
		if(point.getX()+1!=column-1){
			delPoint(point);
			point.moveRight();
			setPoint(point);
		}
	}
	public void fire(){
		up();
		Element bul = new Element(point.getX(),point.getY()-1);
		bullets.add(bul);
		field[bul.getX()][bul.getY()]=bul;

	}
	@Override
	public void left() {
		if(point.getX()-1!=0){
			delPoint(point);
			point.moveLeft();
			setPoint(point);
		}
	}
	private int spaceBetweenBullets=2;
	private int currentSpace= 0;
	@Override
	public void go() {
		if(currentSpace==0){
			fire();
			currentSpace=spaceBetweenBullets;
		}
		else{
			up();
			currentSpace--;
		}
	}
	private boolean collision(Member bullet){
		Member mem = field[bullet.getX()][bullet.getY()-1];
		try{
			if(mem!=null){
				bullets.remove(bullet);
				field[bullet.getX()][bullet.getY()]=null;
				if(mem instanceof BorderSquare){
					return true;
				}
				field[bullet.getX()][bullet.getY()-1]=null;
				return true;
			}
		}catch (ArrayIndexOutOfBoundsException e){
			bullets.remove(bullet);
			return true;
		}
		return false;
	}
	@Override
	public void up() {
		for(int i =0;i<bullets.size();i++){
			if(collision(bullets.get(i)));
			delPoint(bullets.get(i));
			bullets.get(i).moveUp();
			setPoint(bullets.get(i));
		}
	}
	@Override
	public void down() {
		for(int i =0;i<stones.size();i++){
			stones.get(i).moveDown();
		}
		Random generator = new Random(); 
		for(int i=0;i<column;i++){
			if(generator.nextBoolean()){
				Element stone=new Element(i,1);
				field[i][1]=stone;
				stones.add(stone);
			}
		}
	}

}
