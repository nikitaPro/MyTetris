/**
 * 
 */
package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import frames.MainFrame;

/**
 * @author NikitaNB
 *
 */
public class Main {
	public static Dimension screenSize;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}

}
