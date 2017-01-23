/**
 * 
 */
package tetrisPool;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import resources.AllSounds;
import resources.FileWorker;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import pool.Field;
import square.Figure;
import square.Square;

/**
 * @author NikitaNB
 *
 */
public class TetrisField implements Field {
	
	protected boolean[][] field = null;
	private int column = 20;
	private int startPosition=0;
	private int line = 30;
	private Score score ;
	private String recordsFilePath = "Records/Tetris.txt"; 
	private int scoreVal=0;
	private Timer timer;
	private Figure current =null;
	private Toolkit tool = Toolkit.getDefaultToolkit();
	private boolean pause=false;
	public String bonus = null;
	public TetrisField(){
		startPosition=column/2-2;
		init();
	}	
	public TetrisField(int hor, int vert){
		column=hor;
		line=vert;
		startPosition=column/2-2;
		init();
	}
	private void init(){
		field = new boolean[column][line];
		for(int i=0;i<line;i++){
			field[column-1][i]=true;
			field[0][i]=true;
		}
		for(int i=0;i<column;i++){
			field[i][line-1]=true;
			field[i][0]=true;
		}

		createFigure();
	}
	public void  setDfficulty(int diff){
		score = new Score(recordsFilePath,diff);
		timer = score.getTimer();
	}
	public void createFigure(){
		Random generator = new Random(); 
		current = new Figure(startPosition,0 ,generator.nextInt(Figure.COUNTTYPE)+1);
		insBits();
	}
	public Timer letsGo(){
		timer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				go();
			}
		});
		timer.start();
		return timer;
	}
	public Timer pause(){
		if(!pause){ 
			timer.stop();
			pause=true;
		}
		else{
			timer.start();
			pause=false;
		}
		return timer;
	}
	
	private void delBits(){
		Square fig[];
		fig= current.getSquares();
		int absX=current.getX();
		int absY=current.getY();
		for(int i =0;i<fig.length;i++)
			field[absX+fig[i].getX()][absY+fig[i].getY()]=false;
	}
	private void insBits(){
		Square fig[];
		fig= current.getSquares();
		int absX=current.getX();
		int absY=current.getY();
		for(int i =0;i<fig.length;i++)
			field[absX+fig[i].getX()][absY+fig[i].getY()]=true;
	}
	private boolean autoStop()
	{
		if(!autoStopDown()){
			checkGameOver();
			checkLine();
			createFigure();
			return false;
		}
		return true;
	}
	private boolean autoStopDown(){
		boolean[][] bits= current.getBitField();
		int absX=current.getX();
		int absY=current.getY();
		for(int i=0;i<bits[0].length;i++)
			for(int j=0;j<bits.length;j++)
				if(bits[j][i]){
					try{
						if(bits[j][i+1]) continue;
						else if(field[absX+j][absY+i+1]){
							return false;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						if(field[absX+j][absY+i+1]){
							return false;
						}
					}
				}
		return true;
	}
	private boolean autoStopLeft()
	{
		boolean[][] bits= current.getBitField();
		int absX=current.getX();
		int absY=current.getY();
		for(int i=0;i<bits[0].length;i++)
			for(int j=0;j<bits.length;j++)
				if(bits[j][i]){
					try{
					if(bits[j-1][i]) continue;
					else if(field[absX+j-1][absY+i])return false;
					}catch (ArrayIndexOutOfBoundsException e){
						if(field[absX+j-1][absY+i])return false;
					}
				}
		return true;
	}
	private boolean autoStopRight()
	{
		boolean[][] bits= current.getBitField();
		int absX=current.getX();
		int absY=current.getY();
		for(int i=0;i<bits[0].length;i++)
			for(int j=0;j<bits.length;j++)
				if(bits[j][i]){
					try{
					if(bits[j+1][i]) continue;
					else if(field[absX+j+1][absY+i])return false;
					}catch (ArrayIndexOutOfBoundsException e){
						if(field[absX+j+1][absY+i])return false;
					}
				}
		return true;
	}
	private void test(){
		for(int i=0;i<line;i++){
			for(int j=0;j<column;j++){
				if(field[j][i])
					System.out.print("1");
				else
					System.out.print("0");
			}
			System.out.println();
		}
		System.out.println();
	}
	public Object getField(){
		return field;
	}
	public void right(){
		if(autoStopRight()){
			delBits();
			current.right();
			insBits();
		}
	}
	public void left(){
		if(autoStopLeft()){
			delBits();
			current.left();
			insBits();
		}
	}
	public void go(){
		if(autoStop()){
			AllSounds.downSnd.play(true);
			//tool.beep();
			delBits();
			current.down();
			insBits();
		}
		//autoStop();
		//test();
	}
	public int getScore(){
		return scoreVal;
	}
	public void up(){
		rotate();
	}
	private void rotate(){
		delBits();
		current.rotateR();
		boolean[][] bits = current.getBitField();
		for(int i=0;i<bits[0].length;i++)
			for(int j=0;j<bits.length;j++){
				if(bits[j][i])
					if(field[j+current.getX()][i+current.getY()]){
						current.rotateL();
						insBits();
						return;
					}
			}
		AllSounds.rotateSnd.play(true);
		insBits();
	}
	private void checkLine(){
		int kol;
		int completeLine=0;
		for(int i=line-2;i>1;i--){
			kol=0;
			for(int j=1;j<column-1;j++)
				if(field[j][i])kol++;
			if(kol==column-2){
				AllSounds.lineSnd.play(true);
				for(int t=i;t>1;t--)
					if(i!=line-1)
						for(int j=1;j<column-1;j++)
							field[j][t]=field[j][t-1];
				scoreVal=score.addScore();
				completeLine++;
				i++;
			}
		}
		Timer tm=new Timer(5000,null);
		switch(completeLine){
		case 2: 
			timer.stop();
			bonus="Time 5sec";
			tm.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!pause)
						timer.start();
					bonus=null;
					((Timer)e.getSource()).stop();
				}
			});
			tm.start();
			break;
		case 3:
			int foundLine=-1;
			boolean f =false;
			for(int i=1;i<line-1;i++){
				for(int j=1;j<column-1;j++)
					if(field[j][i]){
						bonus="LINE";
						foundLine=i;
						f=true;
						break;
						}
				if(f)break;
			}
			if(foundLine!=-1)
				for(int j=1;j<column-1;j++){
					//System.out.print(field[j][foundLine]);
					field[j][foundLine]=false;
				}
			tm.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bonus=null;
					((Timer)e.getSource()).stop();
				}
			});
			tm.start();
			break;
		case 4: 
			bonus="SCORE +4";
			scoreVal=score.addScore();
			scoreVal=score.addScore();
			scoreVal=score.addScore();
			scoreVal=score.addScore();
			tm.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					bonus=null;
					((Timer)e.getSource()).stop();
				}
			});
			tm.start();
			break;
		}
	}
	private void clean(){
		for(int i=0;i<line-1;i++)
			for(int j=1;j<column-1;j++)
				field[j][i]=false;
		scoreVal=0;

	}
	private void checkGameOver(){
		timer.stop();
		for(int j=startPosition;j<startPosition+4;j++)
			if(field[j][1]){
				if(score.getScore()<100){
					AllSounds.GOverSnd.play();
					JOptionPane.showMessageDialog(null, 
							"YOU ARE LOOSER", 
							"Game Over", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					AllSounds.Winner.play();
					JOptionPane.showMessageDialog(null, 
							"YOU ARE FUCKING WINNER", 
							"WIN", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				score.setRecord();
				clean();
				timer=score.resetDiff();
			}
		timer.start();  
	}
}
