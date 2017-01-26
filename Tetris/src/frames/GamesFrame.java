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
import resources.AllImage;
import resources.AllSounds;
import resources.ColorSet;
import square.Member;
import main.Main;

public abstract class GamesFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel =null;
	private Timer timer = null;
	private JMenu menu_1;
	private JSlider slider;
	private JCheckBoxMenuItem helpItem;
	private JMenuBar menuBar;
	private JMenuItem menuItem_1;
	private Timer timer2 = null;
	/**Icon for slide show*/
	protected Icon slide=AllImage.PICI[0];
	protected Image scaledImage;
	private int slideShowCounter=0;	
	/**Size of cell*/
	protected int coubSize = 10;
	/**Inner size of cell*/
	protected int coubSizeInner = 6;
	/**Distance between external side of cell and internal side of cell */
	protected int coubStep = 2;
	private int fieldWidth;
	private int fieldHeight;
	private boolean pause = false;
	private boolean secret=false;
	private boolean mute = true;
	protected final int heightConst = 45+20+45;
	private String pauseStr = "PAUSE";
	protected Field<Member[][]> field;
	
	public boolean isSecretActive() {
		return this.secret;
	}
	public boolean isPause() {
		return this.pause;
	}
	
	public GamesFrame(int fieldWidth, int fieldHeight,Field<Member [][]> field) {
		setResizable(false);
		this.setJMenuBar(getMenuBar_1());
		this.setContentPane(getPanel());
		Theme1();
		this.field = field;
		int scrH=Main.screenSize.height;
		this.fieldHeight=fieldHeight;//Height of field (count of cell)
		this.fieldWidth=fieldWidth;// Width of field (count of cell)
		coubSize =scrH/ fieldHeight-(heightConst/fieldHeight+2); 
		if(coubSize%2==1) coubSize+=1;
		coubSizeInner =coubSize-coubSize*45/100; 
		coubStep = (coubSize-coubSizeInner)/2; 
		this.setSize(fieldWidth*coubSize+6,fieldHeight*coubSize+heightConst );
		this.setLocation(Main.screenSize.width/2-this.getSize().width/2, Main.screenSize.height/2-this.getSize().height/2-25);
		timer=screenUpdater();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch(code){
				case KeyEvent.VK_RIGHT:
					if(!isPause())
						field.right();
					break;
				case KeyEvent.VK_LEFT:
					if(!isPause()){
						field.left();
					}
					break;
				case KeyEvent.VK_DOWN:
					if(!isPause())
						field.down();
					break;
				case KeyEvent.VK_UP:
					if(!isPause())
						field.up();
					break;
				case KeyEvent.VK_DIVIDE:
					if(secret=!secret)
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
					pause = !field.pause().isRunning();
					break;
				case KeyEvent.VK_1:
					Theme1();
					break;
				case KeyEvent.VK_2:
					Theme2();
					break;
				case KeyEvent.VK_3:
					Theme3();
					break;
				case KeyEvent.VK_4:
					Theme4();
					break;
				}
			}
		});
	}
	protected JPanel getPanel(){
		if(contentPanel==null){
			contentPanel= new JPanel(){
				private static final long serialVersionUID = 1L;
				private final String TITLE = "BONUS";
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					Graphics2D grf = (Graphics2D)g;
					//if secret was activated
					if(isSecretActive()){
						if(scaledImage.getWidth(null)==GamesFrame.this.getWidth() && scaledImage.getHeight(null)==GamesFrame.this.getHeight())
							grf.drawImage(scaledImage, 0, 0, null);
						else{
							scaledImage=scaleImage(slide);
							grf.drawImage(scaledImage, 0, 0, null);
						}
					}
					Member[][] bitField = field.getField();
					// draw field
					for(int i=0;i<bitField[0].length;i++)
						for(int j=0;j<bitField.length;j++)
							if(bitField[j][i]!=null)
								bitField[j][i].draw(grf, j*coubSize, i*coubSize, coubSize);
					// draw score
					grf.setColor(Color.WHITE);
					grf.setFont(new Font("Arial", Font.BOLD, 20));
					grf.drawString("Score: "+field.getScore(), 40,coubSize*bitField[0].length+20);
					// draw PAUSE
					if(isPause()){
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(pauseStr, this.getSize().width/2-65,this.getSize().height/2);
					}
					// draw name of bonus
					String bonus = field.getBonusName();
					if(bonus!=null){
						grf.setFont(new Font("Arial", Font.BOLD, 40));
						grf.drawString(TITLE, (this.getSize().width/2)-TITLE.length()*25/2,this.getSize().height/2);
						grf.drawString(bonus, this.getSize().width/2-bonus.length()*19/2,this.getSize().height/2);
					}
					if(getHelpCheckItem().isSelected()){
						Icon icoHelp = HelpFrame.engineOptimalSel;
						//black rectangle behind "Help" image
						grf.setColor(Color.BLACK);
						grf.fillRect(bitField.length*coubSize, //x
								0, //y
								icoHelp.getIconWidth(), //Width
								bitField[0].length*coubSize+heightConst); // Height
						// scaling "Help" image
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
	protected Timer screenUpdater(){
		if(timer==null)
		{
			timer=new Timer(17,new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					contentPanel.repaint();
				}
			});
		}
		timer.start();
	return timer;
	}
	protected Timer slideShow(){
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
		if(fieldHeight*coubSize*2+heightConst<=Main.screenSize.getHeight()){
			coubSize*=2;
			coubSizeInner =coubSize-coubSize*45/100; // внутренний размиер квадратика €чейки
			coubStep = (coubSize-coubSizeInner)/2; // рассто€ние от кра€ €чейки до кра€ внутреннего квадрата
			this.setSize(fieldWidth*coubSize+6,fieldHeight*coubSize+heightConst);
		}
	}
	protected void subSize(){
		helpItem.setSelected(false);
		if(coubSize<=10) return;
		coubSize/=2;
		coubSizeInner =coubSize-coubSize*45/100; // внутренний размиер квадратика €чейки
		coubStep = (coubSize-coubSizeInner)/2; // рассто€ние от кра€ €чейки до кра€ внутреннего квадрата
		this.setSize(fieldWidth*coubSize+6,fieldHeight*coubSize+heightConst );
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.setBackground(Color.BLACK);
			menuBar.add(getMenu_1());
			menuBar.add(getHelpCheckItem());
			
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
	protected JCheckBoxMenuItem getHelpCheckItem() {
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
	protected void stateChangedSlider(ChangeEvent arg0) {
		AllSounds.mute(slider.getValue());
		//System.out.println(slider.getValue());
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
	protected Image scaleImage(Icon ico){
		Image img =((ImageIcon)ico).getImage();
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
		return bimage.getScaledInstance(fieldWidth*coubSize,fieldHeight*coubSize, Image.SCALE_SMOOTH);
	}
	protected void itemStateChangedHelpItem(ItemEvent arg0) {
		run.run();
	}
	private Runnable run = new Thread(){
		public void run(){
			if(helpItem.isSelected()){
				for(int i=0;i<HelpFrame.engineOptimalSel.getIconWidth()/6;i++)
					GamesFrame.this.setSize(GamesFrame.this.getWidth()+6,GamesFrame.this.getHeight() );
			}
			else{
				for(int i=0;i<HelpFrame.engineOptimalSel.getIconWidth()/6;i++)
					GamesFrame.this.setSize(GamesFrame.this.getWidth()-6,GamesFrame.this.getHeight() );
			}
			
		}
	};
	protected void Theme1(){
		getContentPane().setBackground(Color.BLACK);
		ColorSet.colorBorder=Color.WHITE;
		ColorSet.colorFigure=Color.BLUE;
		ColorSet.colorFieldBorder=Color.RED;
	}
	protected void Theme2(){
		getContentPane().setBackground(Color.LIGHT_GRAY);
		ColorSet.colorBorder=Color.BLACK;
		ColorSet.colorFigure=Color.GREEN;
		ColorSet.colorFieldBorder=Color.MAGENTA;
	}
	protected void Theme3(){
		getContentPane().setBackground(new Color(127,127,127));
		ColorSet.colorBorder=new Color(0,127,50);
		ColorSet.colorFigure=new Color(255,156,0);
		ColorSet.colorFieldBorder=Color.WHITE;
	}
	protected void Theme4(){
		getContentPane().setBackground(Color.DARK_GRAY);
		ColorSet.colorBorder=Color.PINK;
		ColorSet.colorFigure=Color.RED;
		ColorSet.colorFieldBorder=Color.WHITE;
	}
}
