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
	private Member[][] bitField;
	private String pauseStr = "PAUSE";
	
	public TetrisFrame(Field<Member [][]> snakeField) {
		super(snakeField.getField().length,
				snakeField.getField()[0].length,
				snakeField);
		ColorSet.colorBorder=Color.BLACK;
		ColorSet.colorFigure=Color.GREEN;
		ColorSet.colorFieldBorder=Color.MAGENTA;
		ColorSet.colorApple=Color.RED;
		bitField = snakeField.getField();
		this.setTitle("JTetris");
		this.setVisible(true);
		contentPanel.setBackground(Color.LIGHT_GRAY);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				//System.out.println("["+KeyEvent.VK_RIGHT+" "+KeyEvent.VK_LEFT+"] "+code);
				switch(code){
				case KeyEvent.VK_1:
					contentPanel.setBackground(Color.BLACK);
					ColorSet.colorBorder=Color.WHITE;
					ColorSet.colorFigure=Color.BLUE;
					ColorSet.colorFieldBorder=Color.RED;
					break;
				case KeyEvent.VK_2:
					contentPanel.setBackground(Color.LIGHT_GRAY);
					ColorSet.colorBorder=Color.BLACK;
					ColorSet.colorFigure=Color.GREEN;
					ColorSet.colorFieldBorder=Color.MAGENTA;
					break;
				case KeyEvent.VK_3:
					contentPanel.setBackground(new Color(127,127,127));
					ColorSet.colorBorder=new Color(0,127,50);
					ColorSet.colorFigure=new Color(255,156,0);
					ColorSet.colorFieldBorder=Color.WHITE;
					break;
				case KeyEvent.VK_4:
					contentPanel.setBackground(Color.DARK_GRAY);
					ColorSet.colorBorder=Color.PINK;
					ColorSet.colorFigure=Color.RED;
					ColorSet.colorFieldBorder=Color.WHITE;
					break;
				}
			}
		});
	}
	@Override
	protected JPanel getPanel(){
		if(contentPanel==null){
			contentPanel= new JPanel(){
				private static final long serialVersionUID = 1L;
				private final String TITLE = "BONUS";
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					Graphics2D grf = (Graphics2D)g;
					//если секрет активирован
					if(isSecretActive()){
						if(scaledImage.getWidth(null)==TetrisFrame.this.getWidth() && scaledImage.getHeight(null)==TetrisFrame.this.getHeight())
							grf.drawImage(scaledImage, 0, 0, null);
						else{
							scaledImage=scaleImage(slide);
							grf.drawImage(scaledImage, 0, 0, null);
						}
					}
					// отрисовка поля
					for(int i=0;i<bitField[0].length;i++)
						for(int j=0;j<bitField.length;j++)
							if(bitField[j][i]!=null)
								bitField[j][i].insertCell(grf, j*coubSize, i*coubSize, coubSize);
					// отрисовка счета
					grf.setColor(Color.WHITE);
					grf.setFont(new Font("Arial", Font.BOLD, 20));
					grf.drawString("Score: "+Main.tetrisFld.getScore(), 40,coubSize*bitField[0].length+20);
					//если пауза то нарисовать слово PAUSE
					if(isPause()){
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(pauseStr, this.getSize().width/2-65,this.getSize().height/2);
					}
					String bonus=Main.tetrisFld.bonus;
					if(bonus!=null){
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(TITLE, (this.getSize().width/2)-TITLE.length()*25/2,this.getSize().height/2);
						grf.drawString(bonus, (this.getSize().width/2)-bonus.length()*25/2,this.getSize().height/2+40);
					}
					if(getHelpCheckItem().isSelected()){
						Icon icoHelp = HelpFrame.engineOptimalSel;
						//черный прямоугольник под хелпом
						grf.setColor(Color.BLACK);
						grf.fillRect(bitField.length*coubSize, //x
								0, //y
								icoHelp.getIconWidth(), //Width
								bitField[0].length*coubSize+heightConst); // Height
						// Масштабирование хелпа
						Image img =((ImageIcon)icoHelp).getImage();
						BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
					    // Draw the image on to the buffered image
					    Graphics2D bGr = bimage.createGraphics();
					    bGr.drawImage(img, 0, 0, null);
					    bGr.dispose();
						img = bimage.getScaledInstance(icoHelp.getIconWidth(),icoHelp.getIconHeight()<bitField[0].length*coubSize?icoHelp.getIconHeight():bitField[0].length*coubSize, Image.SCALE_FAST);
						grf.drawImage(img, bitField.length*coubSize, 0, null);
					}
				}
						
			};
			
		}
		return contentPanel;
	}
	
}
