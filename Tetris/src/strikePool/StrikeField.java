/**
 * 
 */
package strikePool;

import java.util.ArrayList;

import javax.swing.Timer;

import pool.Field;
import square.Member;
import square.Square;
import tetrisPool.Score;

/**
 * @author NikitaNB
 *
 */
public class StrikeField implements Field {
	private String recordsFilePath = "Records/Strike.txt";
	private Score score= new Score(recordsFilePath,400); 
	private Timer timer = score.getTimer();
	protected boolean[][] field = null;
	private int column = 20;
	private int line = 30;
	private int startPosition=line-1;
	private Square point = new Square(column/2,startPosition);
	private ArrayList<Member> bullets= new ArrayList<Member>();

	public StrikeField(){
		init();
	}
	private void init(){
		field = new boolean[column][line];
		setPoint(point);
	}
	private void setPoint(Member b){
		field[b.getX()][b.getY()]=true;
	}
	private void delPoint(Member b){
		field[b.getX()][b.getY()]=false;
	}
	@Override
	public Timer letsGo() {
		timer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				fire();
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
	public Object getField() {
		return field;
	}

	@Override
	public void right() {
		delPoint(point);
		point.moveRight();
		setPoint(point);
	}
	public void fire(){
		up();
		bullets.add(new Square(point.getX(),point.getY()));
	}
	@Override
	public void left() {
		delPoint(point);
		point.moveLeft();
		setPoint(point);
	}

	@Override
	public void go() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void up() {
		for(int i =0;i<bullets.size();i++){
			if(bullets.get(i).getY()>1){
				delPoint(bullets.get(i));
				bullets.get(i).moveUp();
				setPoint(bullets.get(i));
			}
			else 
				bullets.remove(i);
		}
	}

}
