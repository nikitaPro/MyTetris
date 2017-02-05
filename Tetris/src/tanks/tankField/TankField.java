package tanks.tankField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.Timer;

import pool.Field;
import resources.AllSounds;
import score.Score;
import square.BorderSquare;
import square.Member;
import strike.elements.Bullet;
import strike.elements.Element;
import strike.elements.Rocket;
import strike.elements.Stone;
import tanks.hangar.HeroTank;
import tanks.hangar.Shell;

public class TankField implements Field<Member[][]> {
	private String recordsFilePath = "Records/Tank.txt";
	private Score score= new Score(recordsFilePath,150); 
	private Timer timer = score.getTimer();
	protected Member[][] field = null;
	private int column = 40;
	private int line = 15;
	private int startPosition=line-2;
	private HeroTank hTank ;
	private ArrayList<Shell> shells ;
	
	public TankField(int column, int line){
		this.column = column;
		this.line = line;
		init();
	}
	private void init(){
		shells = new ArrayList<Shell>();
		field = new Member[column][line];
		for(int i=0;i<line;i++){
			field[column-1][i]= new BorderSquare(column-1,i);
			field[0][i]= new BorderSquare(0,i);
		}
		for(int i=0;i<column;i++){
			field[i][line-1]= new BorderSquare(i,line-1);
			field[i][0]=new BorderSquare(i,0);
		}
		Random generator = new Random(); 
		for(int i=2;i<column-2;i++){
			for(int j=2;j<line-2;j++){
				if((i>column/2+1||i<column/2-1) && (j>line/2+1||j<line/2-1))
					if(generator.nextBoolean()){
						field[i][j]=new Stone(i,j);
					}
			}
		}
		hTank=new HeroTank(column/2,startPosition);
		deployOnField(hTank);
	}
	private void deployOnField(Member b){
		field[b.getX()][b.getY()]=b;
	}
	private void removeFromField(Member b){
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member[][] getField() {
		return field;
	}

	@Override
	public void right() {
		if(HeroTank.RIGHT==hTank.getDirection())
			if(collision(hTank))
				return;
		removeFromField(hTank);
		hTank.moveRight();
		deployOnField(hTank);
	}

	@Override
	public void left() {
		if(HeroTank.LEFT==hTank.getDirection())
			if(collision(hTank)) return;
				removeFromField(hTank);
				hTank.moveLeft();
				deployOnField(hTank);
	}
	public void fire(){
		int dir=hTank.getDirection();
		Shell shell=new Shell(hTank.getX(),hTank.getY(),dir);
		Member mem = field[shell.getX()][shell.getY()];
		//if there is situation when under bullet exist stone 
		if(mem!=null){
			if(!(mem instanceof Shell)){
				AllSounds.shot.multyPlay();
				elementDestroy(mem);
			}
			return;
		}
		AllSounds.shot.multyPlay();
		field[shell.getX()][shell.getY()]=shell;
		shells.add(shell);
	}
	private void elementDestroy(Member element){
		if(element==null)return;
		if(!(element instanceof Element)){
			AllSounds.ricochet.multyPlay();
			return;
		}
		removeFromField(element);
		AllSounds.stoneDestroy.multyPlay();
		
	}
	@Override
	public void go() {
		for(Shell s:shells){
			removeFromField(s);
			if(collision(s)){
				shells.remove(s);
				s.move();
				elementDestroy(field[s.getX()][s.getY()]);
				return;
			}
			s.move();
			deployOnField(s);
		}
	}
	private boolean collision(HeroTank tank){
		Member mem;
		switch (tank.getDirection()){
		case HeroTank.DOWN: mem=field[tank.getX()][tank.getY()+1];break;
		case HeroTank.UP: mem=field[tank.getX()][tank.getY()-1];break;
		case HeroTank.RIGHT: mem=field[tank.getX()+1][tank.getY()];break;
		case HeroTank.LEFT: mem=field[tank.getX()-1][tank.getY()];break;
		default: throw new IllegalArgumentException("Unknown direction");
		}
		if(mem!=null) return true;
		return false;
	}
	@Override
	public void up() {
		if(HeroTank.UP==hTank.getDirection())
			if(collision(hTank)) return;
				removeFromField(hTank);
				hTank.moveUp();
				deployOnField(hTank);
	}

	@Override
	public void down() {
		if(HeroTank.DOWN==hTank.getDirection())
			if(collision(hTank)) return;
				removeFromField(hTank);
				hTank.moveDown();
				deployOnField(hTank);
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBonusName() {
		// TODO Auto-generated method stub
		return null;
	}

}
