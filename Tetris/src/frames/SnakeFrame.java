package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pool.Field;
import main.Main;
import resources.AllImage;
import resources.AllSounds;
import resources.ColorSet;
import square.Member;

public class SnakeFrame extends GamesFrame {
	private static final long serialVersionUID = 1L;
	
	public SnakeFrame(Field<Member [][]> snakeField) {
		super(snakeField.getField().length,
				snakeField.getField()[0].length,
				snakeField);
		Theme2();
		this.setTitle("JSnake");
		this.setVisible(true);

	}
	protected void Theme1(){
		super.Theme1();
		ColorSet.colorApple=Color.GREEN;
	}
	protected void Theme2(){
		super.Theme2();
		ColorSet.colorApple=Color.RED;
	}
	protected void Theme3(){
		getContentPane().setBackground(new Color(117,117,117));
		ColorSet.colorBorder=Color.DARK_GRAY;
		ColorSet.colorFigure=Color.BLACK;
		ColorSet.colorFieldBorder=new Color(139, 8, 209);
		ColorSet.colorApple=new Color(235, 195, 0);
	}
	protected void Theme4(){
		getContentPane().setBackground(Color.BLACK);
		ColorSet.colorBorder=Color.DARK_GRAY;
		ColorSet.colorFigure=new Color(91, 156, 6);
		ColorSet.colorFieldBorder=Color.BLACK;
		ColorSet.colorApple=Color.BLUE;
	}
}
