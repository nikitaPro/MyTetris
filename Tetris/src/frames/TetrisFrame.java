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

import main.Main;

/**
 * @author NikitaNB
 *
 */
public class TetrisFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel =null;
	protected static Color colorBorder=Color.BLACK;
	protected static Color colorFigure=Color.GREEN;
	protected static Color colorFieldBorder=Color.MAGENTA;
	private Timer timer = null;
	protected int coubSize = 10;
	protected int coubSizeInner = 6;
	protected int coubStep = 2;
	private Rectangle2D rect = new Rectangle2D.Double(); 
	private boolean[][] bitField;
	private String pauseStr = "PAUSE";
	private Timer timer2 = null;
	private boolean pici=false;
	private int slideShowCounter=0;
	private Icon slide=AllImage.PICI[0];
	private Image scaledImage;
	private boolean pause = false;
	private JMenuBar menuBar;
	private JMenuItem menuItem_1;
	private boolean mute = true;
	private final int heightConst = 45+20+45;
	private JMenu menu_1;
	private JSlider slider;
	private JCheckBoxMenuItem helpItem;
	
	public TetrisFrame(Object field) {
		setResizable(false);
		bitField = (boolean[][])field;
		int scrH=Main.screenSize.height; // высота экрана
		int fldH=bitField[0].length;//высота пол€ в €чейках
		int fldW=bitField.length;// ширина пол€ в €чейках
		coubSize =scrH/ fldH-(heightConst/fldH+2); // размер €чейки
		if(coubSize%2==1) coubSize+=1;
		coubSizeInner =coubSize-coubSize*45/100; // внутренний размиер квадратика €чейки
		coubStep = (coubSize-coubSizeInner)/2; // рассто€ние от кра€ €чейки до кра€ внутреннего квадрата
		this.setJMenuBar(getMenuBar_1());
		this.setContentPane(getPanel());
		this.setSize(fldW*coubSize+6,fldH*coubSize+heightConst );
		//addSize();
		this.setLocation(Main.screenSize.width/2-this.getSize().width/2, Main.screenSize.height/2-this.getSize().height/2-25);
		this.setTitle("JTetris");
		this.setVisible(true);
		contentPanel.setBackground(Color.LIGHT_GRAY);
		timer=screenUpdater();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				//System.out.println("["+KeyEvent.VK_RIGHT+" "+KeyEvent.VK_LEFT+"] "+code);
				switch(code){
				case KeyEvent.VK_RIGHT:
					if(!pause)
						Main.tetrisFld.right();
					break;
				case KeyEvent.VK_LEFT:
					if(!pause)
						Main.tetrisFld.left();
					break;
				case KeyEvent.VK_DOWN:
					if(!pause)
						Main.tetrisFld.go();
					break;
				case KeyEvent.VK_UP:
					if(!pause)
						Main.tetrisFld.up();
					break;
				case KeyEvent.VK_1:
					contentPanel.setBackground(Color.BLACK);
					colorBorder=Color.WHITE;
					colorFigure=Color.BLUE;
					colorFieldBorder=Color.RED;
					break;
				case KeyEvent.VK_2:
					contentPanel.setBackground(Color.LIGHT_GRAY);
					colorBorder=Color.BLACK;
					colorFigure=Color.GREEN;
					colorFieldBorder=Color.MAGENTA;
					break;
				case KeyEvent.VK_3:
					contentPanel.setBackground(new Color(127,127,127));
					colorBorder=new Color(0,127,50);
					colorFigure=new Color(255,156,0);
					colorFieldBorder=Color.WHITE;
					break;
				case KeyEvent.VK_4:
					contentPanel.setBackground(Color.DARK_GRAY);
					colorBorder=Color.PINK;
					colorFigure=Color.RED;
					colorFieldBorder=Color.WHITE;
					break;
				case KeyEvent.VK_DIVIDE:
					if(pici=!pici)
						slideShow().start();
					else
						slideShow().stop();
					break;
				case KeyEvent.VK_EQUALS:
					addSize();
					break;
				case KeyEvent.VK_MINUS:
					subSize();
					break;
				case KeyEvent.VK_SPACE:
					pause = !Main.tetrisFld.pause().isRunning();
					break;
				}
			}
		});
	}
	private JPanel getPanel(){
		if(contentPanel==null){
			contentPanel= new JPanel(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				String bonus=Main.tetrisFld.bonus;
				final String TITLE = "BONUS";
				public void paintComponent(Graphics g){
					new Thread(){
						public void run(){
						}
					}.start();
					
					super.paintComponent(g);
					Graphics2D grf = (Graphics2D)g;
					//если секрет активирован
					if(pici){
						if(scaledImage.getWidth(null)==TetrisFrame.this.getWidth() && scaledImage.getHeight(null)==TetrisFrame.this.getHeight())
							grf.drawImage(scaledImage, 0, 0, null);
						else{
							scaledImage=scaleImage(slide);
							grf.drawImage(scaledImage, 0, 0, null);
						}
					}
					String bonus=Main.tetrisFld.bonus;
					if(bonus!=null){
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(TITLE, (this.getSize().width/2)-TITLE.length()*25/2,this.getSize().height/2);
						grf.drawString(bonus, (this.getSize().width/2)-bonus.length()*25/2,this.getSize().height/2+40);
					}
					// отрисовка внутри пол€
					for(int i=0;i<bitField[0].length-1;i++)
						for(int j=1;j<bitField.length-1;j++)
							if(bitField[j][i])
								insertCell(grf, j, i, colorFigure, colorBorder);
					// отрисовка границ пол€ правой и левой
					for(int i=0;i<bitField[0].length;i++){
						if(bitField[0][i])
							insertCell(grf, 0, i, colorFieldBorder, colorBorder);
						if(bitField[bitField.length-1][i])
							insertCell(grf, bitField.length-1, i, colorFieldBorder, colorBorder);
					}
					// отрисовка границ верхней и нижней
					for(int j=1;j<bitField.length-1;j++)
						if(bitField[j][bitField[0].length-1]){
							insertCell(grf, j, bitField[0].length-1, colorFieldBorder, colorBorder);
							insertCell(grf, j, 0, colorFieldBorder, colorBorder);
						}
					// отрисовка счета
					grf.setColor(Color.WHITE);
					grf.setFont(new Font("Arial", Font.BOLD, 20));
					grf.drawString("Score: "+Main.tetrisFld.getScore(), 40,coubSize*bitField[0].length+20);
					if(pause){// пауза то нарисовать слово PAUSE
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(pauseStr, this.getSize().width/2-65,this.getSize().height/2);
					}
					if(helpItem.isSelected()){
						Icon icoHelp = HelpFrame.engineOptimalSel;
						//черный пр€моугольник под хелпом
						grf.setColor(Color.BLACK);
						grf.fillRect(bitField.length*coubSize, //x
								0, //y
								icoHelp.getIconWidth(), //Width
								bitField[0].length*coubSize+heightConst); // Height
						//AllImage.engineOptimalSel.paintIcon(this, grf, bitField.length*coubSize, 0);
						// ћасштабирование хелпа
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
	// рисование квадрата €чейки
	private void insertCell(Graphics2D grf, int j, int i, Color in, Color out){
		grf.setColor(out);
		grf.fillRect(j*coubSize, i*coubSize, coubSize, coubSize);
		grf.setColor(in);
		grf.fillRect(j*coubSize+coubStep, i*coubSize+coubStep, coubSizeInner, coubSizeInner);			
	}
	private Timer screenUpdater(){
		if(timer==null)
		{
			timer=new Timer(16,new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					contentPanel.repaint();
				}
			});
		}
		timer.start();
	return timer;
	}
	private Timer slideShow(){
		if(timer2==null)
		{
			
			timer2=new Timer(5000,new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					actionPerformedSlideshow(e);
				}
			});
		}
		actionPerformedSlideshow(null);
		timer2.start();
	return timer2;
	}
	protected void addSize(){
		helpItem.setSelected(false);
		if(bitField[0].length*coubSize*2+heightConst<=Main.screenSize.getHeight()){
			coubSize*=2;
			coubSizeInner =coubSize-coubSize*45/100; // внутренний размиер квадратика €чейки
			coubStep = (coubSize-coubSizeInner)/2; // рассто€ние от кра€ €чейки до кра€ внутреннего квадрата
			this.setSize(bitField.length*coubSize+6,bitField[0].length*coubSize+heightConst);
		}
	}
	protected void subSize(){
		helpItem.setSelected(false);
		if(coubSize<=10) return;
		coubSize/=2;
		coubSizeInner =coubSize-coubSize*45/100; // внутренний размиер квадратика €чейки
		coubStep = (coubSize-coubSizeInner)/2; // рассто€ние от кра€ €чейки до кра€ внутреннего квадрата
		this.setSize(bitField.length*coubSize+6,bitField[0].length*coubSize+heightConst );
	}
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.setBackground(Color.BLACK);
			menuBar.add(getMenu_1());
			menuBar.add(getCheckBoxMenuItem());
			
		}
		return menuBar;
	}
	private JMenuItem getMenuItem_1() {
		if (menuItem_1 == null) {
			menuItem_1 = new JMenuItem("«вук");
			menuItem_1.setIcon(AllImage.VOLUME);
			menuItem_1.setBackground(Color.BLACK);
			menuItem_1.setForeground(Color.WHITE);
			menuItem_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionPerformedMenuItem_1(e);
				}
			});
		}
		return menuItem_1;
	}
	protected void actionPerformedMenuItem_1(ActionEvent e) {
		mute=!mute;
		if(mute) {
			menuItem_1.setIcon(AllImage.VOLUME);
			AllSounds.mute(slider.getValue());
		}
		else{ 
			menuItem_1.setIcon(AllImage.MUTE);
			AllSounds.mute(mute);
		}
		
	}
	protected void actionPerformedSlideshow(ActionEvent e) {
			slide=AllImage.PICI[slideShowCounter];
			slideShowCounter++;
			if(AllImage.PICI.length==slideShowCounter)
				slideShowCounter=0;
			scaledImage=scaleImage(slide);
	}
	private Image scaleImage(Icon ico){
		Image img =((ImageIcon)ico).getImage();
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
		return bimage.getScaledInstance(bitField.length*coubSize,bitField[0].length*coubSize, Image.SCALE_SMOOTH);
	}

	/*protected void ancestorMovedSlider(AncestorEvent arg0) {
		AllSounds.mute(slider.getValue());
		System.out.println(slider.getValue());
	}*/

	protected void stateChangedSlider(ChangeEvent arg0) {
		AllSounds.mute(slider.getValue());
		//System.out.println(slider.getValue());
	}
	private JMenu getMenu_1() {
		if (menu_1 == null) {
			menu_1 = new JMenu("√ромкость");
			menu_1.setForeground(Color.WHITE);
			menu_1.setBackground(Color.BLACK);
			menu_1.setFont(new Font("Segoe Print", Font.BOLD , 20));
			menu_1.add(getMenuItem_1());
			menu_1.add(getSlider());
		}
		return menu_1;
	}
	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					stateChangedSlider(arg0);
				}
			});
			slider.setMinimum(500);
			slider.setMaximum(1000);
			slider.setValue(750);
		}
		return slider;
	}
	private JCheckBoxMenuItem getCheckBoxMenuItem() {
		if (helpItem == null) {
			helpItem = new JCheckBoxMenuItem("Help");
			helpItem.setIcon(AllImage.HELP);
			helpItem.setFont(new Font("Segoe Print", Font.BOLD, 20));
			helpItem.setBackground(Color.BLACK);
			helpItem.setForeground(Color.WHITE);
			helpItem.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					itemStateChangedHelpItem(arg0);
				}
			});
			helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}
		return helpItem;
	}
	protected void itemStateChangedHelpItem(ItemEvent arg0) {
		run.run();
	}
	private Runnable run = new Thread(){
		public void run(){
			if(helpItem.isSelected()){
				for(int i=0;i<HelpFrame.engineOptimalSel.getIconWidth()/6;i++)
					TetrisFrame.this.setSize(TetrisFrame.this.getWidth()+6,TetrisFrame.this.getHeight() );
			}
			else{
				for(int i=0;i<HelpFrame.engineOptimalSel.getIconWidth()/6;i++)
					TetrisFrame.this.setSize(TetrisFrame.this.getWidth()-6,TetrisFrame.this.getHeight() );
			}
			
		}
	};
}
