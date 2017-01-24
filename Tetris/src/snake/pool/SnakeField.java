/**
 * 
 */
package snake.pool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import resources.AllSounds;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import pool.Field;
import snake.body.Apple;
import snake.body.Head;
import snake.body.Segment;
import snake.body.Soft;
import square.BorderSquare;
import square.Member;
import square.Square;
import tetrisPool.Score;

/**
 * @author NikitaNB
 *
 */
public class SnakeField implements Field<Member[][] > {
	
	protected Member[][] field = null;
	private int column = 20;
	private int startPositionX=0;
	private int startPositionY=0;
	private int line = 30;
	private Score score;
	private String bonus=null;
	private int applePartyCounter=0;
	private String recordsFilePath = "Records/Snake.txt"; 
	private int scoreVal=0;
	private Timer timer ;
	private Head snake =null;
	private int direction;
	private int currentDir;
	public SnakeField(){
		startPositionX=column/2-2;
		startPositionY=line/2-2;
		init();
	}	
	public String getBonus() {
		return this.bonus;
	}
	public void resetBonus() {
		this.bonus = null;
	}
	public SnakeField(int hor, int vert){
		column=hor;
		line=vert;
		startPositionX=column/2-2;
		startPositionY=line/2-2;
		init();
	}
	private void init(){
		setDfficulty(400);
		field = new Member[column][line];
		for(int i=0;i<line;i++){
			field[column-1][i]=new BorderSquare(column-1,i);
			field[0][i]=new BorderSquare(0,i);
		}
		for(int i=0;i<column;i++){
			field[i][line-1]=new BorderSquare(i,line-1);
			field[i][0]=new BorderSquare(i,0);
		}
		createSnake();
		setOnField(snake);
	}
	/**@param diff - Time in ms before next action start.
	 * It is set by default - 400ms */
	public void setDfficulty(int diff){
		score = new Score(recordsFilePath,diff);
		timer = score.getTimer();
		timer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				crawl();
			}
		});
	}
	public void createSnake(){
		snake = new Head(startPositionX,startPositionY,
				new Segment(startPositionX+1,startPositionY,
						new Segment(startPositionX+2,startPositionY)));
	}
	public void delFromField(Segment snake){
		field[snake.getX()][snake.getY()]=null;
		Segment next = snake.getNextSegment();
		if(next!=null)
			delFromField(next);
	}
	public void setOnField(Segment snake){
		field[snake.getX()][snake.getY()]=snake;
		Segment next = snake.getNextSegment();
		if(next!=null)
			setOnField(next);
	}
	@Override
	public Timer letsGo(){
		left();
		deployNewApple();
		timer.start();
		return timer;
	}
	private void crawl(){
		delFromField(snake);
		go();
		setOnField(snake);
	}
	@Override
	public Timer pause(){
		if(timer.isRunning()) timer.stop();
		else timer.start();
		return timer;
	}
	
	private void test(){
		for(int i=0;i<line;i++){
			for(int j=0;j<column;j++){
				if(field[j][i]!=null)
					System.out.print("1");
				else
					System.out.print("0");
			}
			System.out.println();
		}
		System.out.println();
	}
	@Override
	public Member[][] getField(){
		return field;
	}
	@Override
	public void right(){
		if(direction==3){
			crawl();
		}
		else if(currentDir!=4){
			direction=3;
		}
	}
	@Override
	public void left(){
		if(direction==4){
			crawl();
		}
		else if(currentDir!=3){
			direction=4;
		}
	}
	@Override
	public void down(){
		if(direction==2){
			crawl();
		}
		else if(currentDir!=1){
			direction=2;
		}
	}
	@Override
	public void up(){
		if(direction==1){
			crawl();
		}
		else if(currentDir!=2){
			direction=1;
		}
	}
	@Override
	public void go(){
		AllSounds.Shoroh.play();
		if(collisionCheck()){ 
			return;
		}
		currentDir=direction;
		switch(direction){
		case 1:	snake.moveUp();break; 
		case 2: snake.moveDown();break;
		case 3: snake.moveRight();break;
		case 4:	snake.moveLeft(); break;
		default: throw new IllegalArgumentException("Unknown direction");
		}
		//autoStop();
		//test();
	}
	public int getScore(){
		return scoreVal;
	}
	public boolean collisionCheck(){
		int x = snake.getX();
		int y = snake.getY();
		switch(direction){
			case 1:	y--;break; 
			case 2: y++;break;
			case 3: x++;break;
			case 4:	x--; break;
			default: throw new IllegalArgumentException("Unknown direction");
		}
		if(field[x][y]!=null){
			if(!(field[x][y] instanceof Soft)){
				gameOver();
				return true;
			}
			else{
				eat((Apple)field[x][y],snake);
				return true;
			}
		}
		if(wasBittenByItself(snake)){
			gameOver();
			return true;
		}
		return false;
	}
	private boolean wasBittenByItself(Segment seg){
		Segment next = seg.getNextSegment();
		while(next!=null){
			if(seg.getX()==next.getX()&&seg.getY()==next.getY())
				return true;
			else next=next.getNextSegment();
		}
		return false;
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
		createSnake();
		setOnField(snake);
		letsGo();
	} 
	private void clean(){
		for(int i=1;i<line-1;i++)
			for(int j=1;j<column-1;j++)
				field[j][i]=null;
		scoreVal=0;
		direction=0;
		currentDir=0;
		bonus=null;
		applePartyCounter=0;
	}
	private void eat(Apple apple,Head snake){
		AllSounds.Hrum.play();
		scoreVal=score.addScore();
		currentDir = direction;
		this.snake=new Head(apple.getX(),apple.getY(),direction);
		this.snake.setNextSegment(new Segment(snake.getX(),snake.getY(),snake.getNextSegment()));
		field[apple.getX()][apple.getY()]=null;
		if(scoreVal%20==0){
			bonus="Apple Party";
			applePartyCounter=6;
			for(int i=0;i<applePartyCounter;i++){
				deployNewApple();
			}
			Timer tm=new Timer(2000,null);
			tm.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					resetBonus();
					((Timer)e.getSource()).stop();
				}
			});
			tm.start();
		}
		if(applePartyCounter==0)
			if(scoreVal>=100){
				if(scoreVal%2==0){
					deployNewApple();
					deployNewApple();
				}
			}
			else 
				deployNewApple();
		else applePartyCounter--;
	}
	private void deployNewApple(){
		setOnField(snake);
		Random generator = new Random(); 
		int x=generator.nextInt(column-1)+1;
		int y=generator.nextInt(line-1)+1;
		Apple newApple = new Apple(x,y);
		int freePlaces[][] = new int[(column-2)*(line-2)][2];
		if(field[x][y]==null) field[x][y] = newApple;
		else {
			int k=0;
			for(int i=1;i<column-1;i++)
				for(int j=1;j<line-1;j++){
					if(field[i][j]==null){
						freePlaces[k][0]=i;
						freePlaces[k][1]=j;
						k++;
					}
				}
			if(k==0){
				gameOver();
				return;
			}
			k=generator.nextInt(k);
			field[freePlaces[k][0]][freePlaces[k][1]] = new Apple(freePlaces[k][0],freePlaces[k][1]);
		}
		delFromField(snake);
	}
}