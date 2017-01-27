/**
 * 
 */
package score;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import resources.FileWorker;

/**
 * @author NikitaNB
 *
 */
public class Score {
	private int score = 0;
	private int scoreTmp = 0;
	private int difficulty;
	private int difficultyStart;
	private String recordsFile;
	private Timer timer;
	public Score(String pathToRecordsFile,int diff){
		difficulty =diff;
		difficultyStart=diff;
		recordsFile=pathToRecordsFile;
		timer=new Timer(difficulty,null);
	}
	public void setRecordsFile(String path){
		recordsFile=path;
	}
	public int getScore(){
		return score;
	}
	public Timer getTimer(){
		return timer;
	}
	public int addScore(){
		scoreTmp++;
		if(scoreTmp==10)
			difficulty();
		return ++score;
	}
	private void difficulty(){
			scoreTmp=0;
			difficulty-=difficulty*10/100;
			timer.setDelay(difficulty);
		
	}
	public Timer resetDiff(){
		score = 0;
		scoreTmp = 0;
		difficulty =difficultyStart;
		timer.setDelay(difficulty);
		return timer;
	}
	public void setRecord(){
		ArrayList<String> record=null;
		try {
			record=FileWorker.readTab(recordsFile);
			if(score<Integer.valueOf(record.get(record.size()-1)))
				return;
		} catch (FileNotFoundException e) {
			String name = JOptionPane.showInputDialog(null, "Введите ваше имя: ", "Рекорд",JOptionPane.QUESTION_MESSAGE);
			if(name==null) return;
			record = new ArrayList<String> ();
			record.add(name);
			record.add(score+"");
			FileWorker.writeTab(recordsFile, record);
			return;
		}
		String name = JOptionPane.showInputDialog(null, "Введите ваше имя: ", "Рекорд",JOptionPane.QUESTION_MESSAGE);
		if(name==null) return;
		if(record.size()==0){
			record.add(name);
			record.add(score+"");
			FileWorker.writeTab(recordsFile, record);
			return;
		}
		record.add(name);
		record.add(String.valueOf(score));
		sort(record);
		if(record.size()==8){
			int r=6;
			record.remove(r);
			record.remove(r);
		}
		FileWorker.writeTab(recordsFile, record);
		return;
	}
	private void sort(ArrayList<String> mass){
		String buff1 ;
		String buff2;
		boolean f=true;
		while(f){
			f=false;
			for(int i=1; i<mass.size()-2;i+=2){
				if(Integer.valueOf(mass.get(i))<Integer.valueOf(mass.get(i+2))){
					buff1 =mass.get(i-1);
					buff2 =mass.get(i);
					mass.set(i-1, mass.get(i+1));
					mass.set(i, mass.get(i+2));
					mass.set(i+1, buff1);
					mass.set(i+2, buff2);
					f=true;
				}
			}
		}
	}
}
