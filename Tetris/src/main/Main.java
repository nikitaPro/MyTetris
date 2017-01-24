/**
 * 
 */
package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import frames.MainFrame;
import snake.pool.SnakeField;
import strike.pool.StrikeField;
import tetrisPool.TetrisField;

/**
 * @author NikitaNB
 *
 */
public class Main {
	public static TetrisField tetrisFld = new TetrisField();//поле тетриса
	public static StrikeField strikeFld = new StrikeField();// поле страйка
	public static SnakeField snakeFld = new SnakeField();
	public static Dimension screenSize;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// TODO Auto-generated method stub
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}

}
