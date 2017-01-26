/**
 * 
 */
package strike.pool;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import pool.Field;
import resources.AllSounds;
import square.BorderSquare;
import square.Member;
import square.Square;
import strike.elements.Bullet;
import strike.elements.Element;
import strike.elements.Gun;
import tetrisPool.Score;

/**
 * @author NikitaNB
 *
 */
public class StrikeField implements Field<Member[][]> {
	private String recordsFilePath = "Records/Strike.txt";
	private Score score= new Score(recordsFilePath,150); 
	private Timer timer = score.getTimer();
	protected Member[][] field = null;
	private int column = 20;
	private int line = 30;
	private int startPosition=line-2;
	private Gun gun ;
	private ArrayList<Member> bullets;
	private ArrayList<Member> stones;
	
	private final int spaceBetweenBullets=1;
	private int currentSpace;
	private final int rockfallDelay=15;
	private int rockfallCounter;

	public StrikeField(){
		init();
	}
	private void init(){
		bullets= new ArrayList<Member>();
		stones= new ArrayList<Member>();
		currentSpace= 0;
		rockfallCounter= 0;
		field = new Member[column][line];
		for(int i=0;i<line;i++){
			field[column-1][i]= new BorderSquare(column-1,i);
			field[0][i]= new BorderSquare(0,i);
		}
		for(int i=0;i<column;i++){
			field[i][line-1]= new BorderSquare(i,line-1);
			field[i][0]=new BorderSquare(i,0);
		}
		gun=new Gun(column/2,startPosition);
		setPoint(gun);
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
		if(gun.getX()+1!=column-1){
			delPoint(gun);
			gun.moveRight();
			setPoint(gun);
		}
	}
	public void fire(){
		up();
		Member stone = field[gun.getX()][gun.getY()-1];
		//if there are situation when under bullet exist stone 
		if(stone!=null){
			stoneDestroy(stone);
			return;
		}
		Bullet bul = new Bullet(gun.getX(),gun.getY()-1);
		bullets.add(bul);
		setPoint(bul);
	}
	@Override
	public void left() {
		if(gun.getX()-1!=0){
			delPoint(gun);
			gun.moveLeft();
			setPoint(gun);
		}
	}
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
		for(int i=0;i<bullets.size();i++){
			if(collision(bullets.get(i)))i--;
		}
		if(rockfallCounter==0){
			down();
			rockfallCounter=rockfallDelay;
		}
		else{
			rockfallCounter--;
		}
		for(int i=0;i<bullets.size();i++){
			if(collision(bullets.get(i)))i--;
		}
	}
	private int collisionCount=0;
	private boolean collision(Member bullet){
		Member mem = field[bullet.getX()][bullet.getY()-1];
		try{
			if(mem!=null){
				bullets.remove(bullet);
				delPoint(bullet);
				if(mem instanceof BorderSquare){
					return true;
				}
				Member stone = field[bullet.getX()][bullet.getY()-1];
				stoneDestroy(stone);
				return true;
			}
		}catch (ArrayIndexOutOfBoundsException e){
			bullets.remove(bullet);
			return true;
		}
		return false;
	}
	private void stoneDestroy(Member stone){
		delPoint(stone);
		stones.remove(stone);
		collisionCount++;
		if(collisionCount==column){
			score.addScore();
			collisionCount=0;
		}
	}
	@Override
	public void up() {
		for(int i =0;i<bullets.size();i++){
			delPoint(bullets.get(i));
			bullets.get(i).moveUp();
			setPoint(bullets.get(i));
		}
	}
	@Override
	public void down() {
		for(int i =0;i<stones.size();i++){
			Member mem = stones.get(i);
			delPoint(mem);
			stones.get(i).moveDown();
			setPoint(mem);
			if (mem.getY()==line-2) gameOver();
		}
		Random generator = new Random(); 
		for(int i=1;i<column-1;i++){
			if(generator.nextBoolean()){
				Element stone=new Element(i,1);
				field[i][1]=stone;
				stones.add(stone);
			}
		}
	}
	private void gameOver(){
		timer.stop();
		if(score.getScore()<100){
			AllSounds.GOverSnd.play();
			JOptionPane.showMessageDialog(null, 
				"YOU ARE LOOSER", 
				"Game Over", 
				JOptionPane.INFORMATION_MESSAGE);
			AllSounds.GOverSnd.stop();
		}
		else{
			AllSounds.Winner.play();
			JOptionPane.showMessageDialog(null, 
				"YOU ARE FUCKING WINNER", 
				"WIN", 
				JOptionPane.INFORMATION_MESSAGE);
			AllSounds.Winner.stop();
			}
		score.setRecord();
		timer=score.resetDiff();
		clean();
		timer.start();
	} 	
	private void clean(){
		init();
	}
	@Override
	public int getScore() {
		return score.getScore();
	}
	@Override
	public String getBonusName() {
		// TODO Auto-generated method stub
		return null;
	}
}
