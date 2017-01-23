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

import main.Main;
import resources.AllImage;
import resources.AllSounds;
import resources.ColorSet;
import square.Member;

public class SnakeFrame extends GamesFrame {
	private static final long serialVersionUID = 1L;
	private String pauseStr = "PAUSE";
	protected Member[][] bitField;
	
	public SnakeFrame(Member[][] field) {
		super(field.length,field[0].length);
		ColorSet.colorBorder=Color.BLACK;
		ColorSet.colorFigure=Color.GREEN;
		ColorSet.colorFieldBorder=Color.MAGENTA;
		ColorSet.colorApple=Color.RED;
		bitField = field;
		this.setTitle("JSnake");
		this.setVisible(true);
		contentPanel.setBackground(Color.LIGHT_GRAY);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch(code){
				case KeyEvent.VK_RIGHT:
					if(!isPause())
						Main.snakeFld.right();
					break;
				case KeyEvent.VK_LEFT:
					if(!isPause()){
						Main.snakeFld.left();
					}
					break;
				case KeyEvent.VK_DOWN:
					if(!isPause())
						Main.snakeFld.down();
					break;
				case KeyEvent.VK_UP:
					if(!isPause())
						Main.snakeFld.up();
					break;
				case KeyEvent.VK_1:
					contentPanel.setBackground(Color.BLACK);
					ColorSet.colorBorder=Color.WHITE;
					ColorSet.colorFigure=Color.BLUE;
					ColorSet.colorFieldBorder=Color.RED;
					ColorSet.colorApple=Color.GREEN;
					break;
				case KeyEvent.VK_2:
					contentPanel.setBackground(Color.LIGHT_GRAY);
					ColorSet.colorBorder=Color.BLACK;
					ColorSet.colorFigure=Color.GREEN;
					ColorSet.colorFieldBorder=Color.MAGENTA;
					ColorSet.colorApple=Color.RED;
					break;
				case KeyEvent.VK_3:
					contentPanel.setBackground(new Color(117,117,117));
					ColorSet.colorBorder=Color.DARK_GRAY;
					ColorSet.colorFigure=Color.BLACK;
					ColorSet.colorFieldBorder=new Color(139, 8, 209);
					ColorSet.colorApple=new Color(235, 195, 0);//=Color.RED;
					break;
				case KeyEvent.VK_4:
					contentPanel.setBackground(Color.BLACK);//DARK_GRAY);
					ColorSet.colorBorder=Color.DARK_GRAY;
					ColorSet.colorFigure=new Color(91, 156, 6);//=new Color(88, 145, 1);
					ColorSet.colorFieldBorder=Color.BLACK;
					ColorSet.colorApple=Color.BLUE;
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
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					Graphics2D grf = (Graphics2D)g;
					//если секрет активирован
					if(isSecretActive()){
						if(scaledImage.getWidth(null)==SnakeFrame.this.getWidth() && scaledImage.getHeight(null)==SnakeFrame.this.getHeight())
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
					grf.drawString("Score: "+Main.snakeFld.getScore(), 40,coubSize*bitField[0].length+20);
					//если пауза то нарисовать слово PAUSE
					if(isPause()){
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(pauseStr, this.getSize().width/2-65,this.getSize().height/2);
					}
					//Бонус
					String bonus = Main.snakeFld.getBonus();
					if(bonus!=null){
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(bonus, this.getSize().width/2-bonus.length()*19/2,this.getSize().height/2);
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
