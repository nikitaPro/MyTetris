/**
 * 
 */
package frames;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import resources.AllImage;
import resources.AllSounds;
import resources.ColorSet;
import square.Member;
import square.Square;
import tetrisPool.TetrisField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.JCheckBoxMenuItem;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.SwingConstants;

import pool.Field;
import main.Main;

/**
 * @author NikitaNB
 *
 */
public class TetrisFrame extends GamesFrame {
	private static final long serialVersionUID = 1L;
	
	public TetrisFrame(Field<Member [][]> snakeField) {
		super(snakeField.getField().length,
				snakeField.getField()[0].length,
				snakeField);
		this.setTitle("JTetris");
		this.setVisible(true);
	
	}
	
	
}
