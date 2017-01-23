package frames;

import java.awt.Color;
import java.awt.Font;
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

import resources.AllImage;
import resources.AllSounds;
import square.Member;
import main.Main;

public abstract class GamesFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	/**–азмер €чейки*/
	protected int coubSize = 10;
	/**¬нутренний размер €чейки*/
	protected int coubSizeInner = 6;
	/**Hассто€ние от внешнего кра€ €чейки до кра€ внутреннего */
	protected int coubStep = 2;
	protected JPanel contentPanel =null;
	protected Icon slide=AllImage.PICI[0];
	protected Image scaledImage;
	private int fieldWidth;
	private int fieldHeight;
	private boolean pause = false;
	private Timer timer = null;
	private JMenu menu_1;
	private JSlider slider;
	private JCheckBoxMenuItem helpItem;
	private JMenuBar menuBar;
	private JMenuItem menuItem_1;
	private Timer timer2 = null;
	private boolean pici=false;
	private int slideShowCounter=0;
	private boolean mute = true;
	protected final int heightConst = 45+20+45;
	
	public boolean isSecretActive() {
		return this.pici;
	}
	public boolean isPause() {
		return this.pause;
	}
	
	public GamesFrame(int fieldWidth, int fieldHeight) {
		setResizable(false);
		this.setJMenuBar(getMenuBar_1());
		this.setContentPane(getPanel());
		int scrH=Main.screenSize.height; // высота экрана
		this.fieldHeight=fieldHeight;//высота пол€ в €чейках
		this.fieldWidth=fieldWidth;// ширина пол€ в €чейках
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
					pause = !Main.snakeFld.pause().isRunning();
					break;
				}
			}
		});
	}
	protected abstract JPanel getPanel();
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
}
