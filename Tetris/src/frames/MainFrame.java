package frames;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SpringLayout;

import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

import resources.AllImage;
import resources.FileWorker;
import snake.pool.SnakeField;
import strike.pool.StrikeField;
import tetrisPool.TetrisField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.io.FileNotFoundException;

import javax.swing.JComboBox;

import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import main.Main;
import pool.Field;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8453782959588089915L;
	private int width=460;
	private int height=520;
	private Dimension screenSize = Main.screenSize;
	private JPanel contentPane;
	private SpringLayout springLayout;
	private JPanel panel;
	private JButton button;
	private JButton button_1;
	private JComboBox<String> comboBox;
	private JButton button_2;
	private JComboBox<String> comboBox_1;
	private JLabel label_1;
	private Timer timer;
	private JPanel panel_1;
	private JButton button_3;
	private JButton button_4;
	private JPanel panel_2;
	private JButton button_5;
	private JButton button_6;
	private JLabel label;
	private JComboBox<String> snakeDiffComboBox;
	private JComboBox<String> snakeSizeComboBox;
	private JLabel label_3;
	private JComboBox comboBox_2;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		initGUI();
	}
		
	private void initGUI() {
		setSize(new Dimension(width, height));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/image/ico.png")));
		this.setTitle("Tetris games");
		this.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
			
		contentPane = new JPanel(){
			private static final long serialVersionUID = 1L;
			private int i=0;
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D grf = (Graphics2D)g;
				if(i>1000)i=-22;
				i++;
				AllImage.BRICK.paintIcon(this, grf, 0, i-1023);// отрисовывает текстуру вверху за экраном
				AllImage.BRICK.paintIcon(this, grf, 1000, i-1023);// отрисовывает текстуру вверху и справа за экраном
				AllImage.BRICK.paintIcon(this, grf, 0, i);// отрисовывает текстуру в видимой области
				AllImage.BRICK.paintIcon(this, grf, 1000, i);// отрисовывает текстуру в видимой области справа
			}
		};
		setContentPane(contentPane);
		springLayout = new SpringLayout();
		contentPane.setLayout(springLayout);
		contentPane.add(getPanel());
		contentPane.add(getButton_2());
		Animation();
		contentPane.add(getPanel_1());
		contentPane.add(getPanel_2());
		contentPane.add(getLabel_3());
	}
	/**Panel for Tetris*/
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel(){
				private static final long serialVersionUID = 1L;
				private int i=0;
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					Graphics2D grf = (Graphics2D)g;
					if(i<-1000)i=0;
					i--;
					scrollEffect(grf,AllImage.FIRE,i);
				}
			};
			springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, contentPane);
			springLayout.putConstraint(SpringLayout.SOUTH, panel, -300, SpringLayout.SOUTH, contentPane);
			springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, contentPane);
			panel.setBackground(Color.RED);
			panel.setFont(new Font("Tahoma", Font.BOLD, 15));
			panel.setBorder(new TitledBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)), "Tetris", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma",Font.BOLD,25), new Color(0, 0, 0)));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
			gridBagLayout.rowHeights = new int[]{0, 0, 0};
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gridBagLayout);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = 2;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel.add(getButton(), gbc);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.gridwidth = 3;
			gbc_1.insets = new Insets(0, 0, 5, 5);
			gbc_1.fill = GridBagConstraints.VERTICAL;
			gbc_1.gridx = 2;
			gbc_1.gridy = 0;
			panel.add(getButton_1(), gbc_1);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.insets = new Insets(0, 0, 5, 0);
			gbc_2.gridx = 5;
			gbc_2.gridy = 0;
			panel.add(getComboBox(), gbc_2);
			GridBagConstraints gbc_4 = new GridBagConstraints();
			gbc_4.anchor = GridBagConstraints.EAST;
			gbc_4.gridwidth = 2;
			gbc_4.insets = new Insets(0, 0, 0, 5);
			gbc_4.gridx = 1;
			gbc_4.gridy = 1;
			panel.add(getLabel_1(), gbc_4);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.gridwidth = 3;
			gbc_3.insets = new Insets(0, 0, 0, 5);
			gbc_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_3.gridx = 3;
			gbc_3.gridy = 1;
			panel.add(getComboBox_1(), gbc_3);
		}
		return panel;
	}
	/**
	 * RECORDS for Tetris
	 * */
	private JButton getButton() {
		if (button == null) {
			button = new JButton("Records");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showRecords("TETRIS", "Records/Tetris.txt");
				}
			});
			button.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
			button.setIcon(AllImage.RECORDS);
		}
		return button;
	}
	/**
	 * START for Tetris
	 * */
	private JButton getButton_1() {
		if (button_1 == null) {
			button_1 = new JButton("Start");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionPerformedButton_1(e);
				}
			});
			button_1.setFont(new Font("Tahoma", Font.BOLD, 20));
			button_1.setIcon(AllImage.TETRIS);
		}
		return button_1;
	}
	private JComboBox<String> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<String>();
			comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
			comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Size: 20x30", "Size: 26x42", "Size: 30x50"}));
			comboBox.setSelectedIndex(0);
		}
		return comboBox;
	}
	/**
	 * HELP
	 * */
	private JButton getButton_2() {
		if (button_2 == null) {
			button_2 = new JButton("Help");
			springLayout.putConstraint(SpringLayout.NORTH, getPanel(), 6, SpringLayout.SOUTH, button_2);
			springLayout.putConstraint(SpringLayout.WEST, button_2, -159, SpringLayout.EAST, contentPane);
			springLayout.putConstraint(SpringLayout.NORTH, button_2, 10, SpringLayout.NORTH, contentPane);
			springLayout.putConstraint(SpringLayout.EAST, button_2, -10, SpringLayout.EAST, contentPane);
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actionPerformedButton_2(arg0);
				}
			});
			button_2.setIcon(AllImage.HELP);
			button_2.setForeground(new Color(25, 25, 112));
			button_2.setFont(new Font("Wide Latin", Font.BOLD | Font.ITALIC, 18));
		}
		return button_2;
	}
	/**
	 * Действие по кнопке HELP
	 * */
	protected void actionPerformedButton_2(ActionEvent e) {
		HelpFrame hf = new HelpFrame();
		hf.setVisible(true);
		hf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	/**
	 * Action START for Tetris
	 * */
	protected void actionPerformedButton_1(ActionEvent e) {
		switch(comboBox.getSelectedIndex()){//выбираем размер поля из выпадающего списка
		case 0: 
			Main.tetrisFld = new TetrisField(20,30);
			break;
		case 1:
			Main.tetrisFld = new TetrisField(26,42);
			break;
		case 2:
			Main.tetrisFld = new TetrisField(30,50);
			break;
		default: return;
		}
		// спрашиваем пользователя о готовности если окно закрыл то выход из обработчика
		if(JOptionPane.showOptionDialog(MainFrame.this, 
				"                         ARE YOU READY?", 
				"Start", 
				JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				new String[]{"GO!"},"GO!")==JOptionPane.CLOSED_OPTION) return;
		
		MainFrame.this.setVisible(false);//скрыть гл меню
		TetrisFrame tf = new TetrisFrame(Main.tetrisFld);// передаем окну поле
		
		switch(comboBox_1.getSelectedIndex()){// выбираем сложность 
		case 0: 
			Main.tetrisFld.setDfficulty(400);// 400 мс до сдвига фигуры вниз
			break;
		case 1:
			Main.tetrisFld.setDfficulty(320);
			break;
		case 2:
			Main.tetrisFld.setDfficulty(240);
			break;
		case 3:
			Main.tetrisFld.setDfficulty(160);
			break;
		default: return;
		}
		
		final Timer tmr =Main.tetrisFld.letsGo();// запускаем игру
		tf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//при закрытии остановка таймера, уничтожение окна возврат в гл меню 
				tmr.stop();
				tf.setVisible(false);
				tf.dispose();
				MainFrame.this.setVisible(true);
			}
		});
	}

	/**
	 * difficulty level for Tetris
	 * */
	private JComboBox<String> getComboBox_1() {
		if (comboBox_1 == null) {
			comboBox_1 = new JComboBox<String>();
			comboBox_1.setFont(new Font("Snap ITC", Font.PLAIN, 20));
			comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"Loser", "King", "GURU", "Zadrot"}));
		}
		return comboBox_1;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Difficulty:");
			label_1.setFont(new Font("Tahoma", Font.BOLD, 20));
			label_1.setForeground(Color.BLACK);
		}
		return label_1;
	}
	/**
	 * Panel for Strike
	 * */
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			springLayout.putConstraint(SpringLayout.WEST, panel_1, 10, SpringLayout.WEST, contentPane);
			springLayout.putConstraint(SpringLayout.SOUTH, panel_1, -28, SpringLayout.SOUTH, contentPane);
			springLayout.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.EAST, getPanel());
			panel_1.setBorder(new TitledBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)), "Strike", TitledBorder.LEADING, TitledBorder.TOP, new Font("Old English Text MT", Font.BOLD | Font.ITALIC, 20), null));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
			gridBagLayout.rowHeights = new int[]{0, 0, 0};
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel_1.setLayout(gridBagLayout);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = 2;
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel_1.add(getButton_3(), gbc);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.insets = new Insets(0, 0, 5, 5);
			gbc_1.gridwidth = 3;
			gbc_1.gridx = 2;
			gbc_1.gridy = 0;
			panel_1.add(getButton_4(), gbc_1);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.insets = new Insets(0, 0, 5, 0);
			gbc_2.gridx = 5;
			gbc_2.gridy = 0;
			panel_1.add(getComboBox_2_1(), gbc_2);
		}
		return panel_1;
	}
	/**Records for Strike*/
	private JButton getButton_3() {
		if (button_3 == null) {
			button_3 = new JButton("Records");
			button_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showRecords("STRIKE", "Records/Strike.txt");
				}
			});
			button_3.setFont(new Font("Old English Text MT", Font.BOLD | Font.ITALIC, 20));
			button_3.setIcon(new ImageIcon(MainFrame.class.getResource("/image/Records.png")));
		}
		return button_3;
	}
	/**Start button for Strike*/
	private JButton getButton_4() {
		if (button_4 == null) {
			button_4 = new JButton("Start");
			button_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actionPerformedButton_4(arg0);
				}
			});
			button_4.setFont(new Font("Old English Text MT", Font.BOLD | Font.ITALIC, 20));
			button_4.setIcon(AllImage.STRIKE);
		}
		return button_4;
	}
	/**
	 * действие по кнопке START для Strike
	 * */
	protected void actionPerformedButton_4(ActionEvent arg0) {
		if(JOptionPane.showOptionDialog(MainFrame.this, 
				"                         ARE YOU READY?", 
				"Start", 
				JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				new String[]{"GO!"},"GO!")==JOptionPane.CLOSED_OPTION) return;
		
		MainFrame.this.setVisible(false);
		StrikeField sField= new StrikeField();
		SnakeFrame sf = new SnakeFrame(sField);
		
		final Timer tmr =sField.letsGo();
		sf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				tmr.stop();
				sf.setVisible(false);
				sf.dispose();
				MainFrame.this.setVisible(true);
			}
		});
	}
	/** Анимация главного меню*/
	private Timer Animation(){
		if(timer==null)
		{
			timer=new Timer(15,new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					contentPane.repaint();
				}
			});
		}
		timer.start();
	return timer;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel(){
				private static final long serialVersionUID = 1L;
				private int i=0;
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					Graphics2D grf = (Graphics2D)g;
					if(i<-1000)i=0;
					i--;
					scrollEffect(grf,AllImage.BARK,i);
				}
			};
			springLayout.putConstraint(SpringLayout.NORTH, getPanel_1(), 6, SpringLayout.SOUTH, panel_2);
			springLayout.putConstraint(SpringLayout.NORTH, panel_2, 6, SpringLayout.SOUTH, getPanel());
			springLayout.putConstraint(SpringLayout.WEST, panel_2, 0, SpringLayout.WEST, getPanel());
			springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -164, SpringLayout.SOUTH, contentPane);
			springLayout.putConstraint(SpringLayout.EAST, panel_2, -10, SpringLayout.EAST, contentPane);
			panel_2.setBackground(new Color(46, 139, 87));
			panel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
			panel_2.setBorder(new TitledBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)), "Snake", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma",Font.BOLD,25), Color.WHITE));
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
			gridBagLayout.rowHeights = new int[]{0, 0, 0};
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel_2.setLayout(gridBagLayout);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.gridwidth = 2;
			gbc_1.fill = GridBagConstraints.VERTICAL;
			gbc_1.insets = new Insets(0, 0, 5, 5);
			gbc_1.gridx = 0;
			gbc_1.gridy = 0;
			panel_2.add(getButton_6(), gbc_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = 3;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 0;
			panel_2.add(getButton_5(), gbc);
			GridBagConstraints gbc_4 = new GridBagConstraints();
			gbc_4.insets = new Insets(0, 0, 5, 0);
			gbc_4.gridx = 5;
			gbc_4.gridy = 0;
			panel_2.add(getComboBox_3(), gbc_4);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.gridwidth = 2;
			gbc_2.anchor = GridBagConstraints.EAST;
			gbc_2.insets = new Insets(0, 0, 0, 5);
			gbc_2.gridx = 1;
			gbc_2.gridy = 1;
			panel_2.add(getLabel(), gbc_2);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.gridwidth = 3;
			gbc_3.insets = new Insets(0, 0, 0, 5);
			gbc_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_3.gridx = 3;
			gbc_3.gridy = 1;
			panel_2.add(getComboBox_2(), gbc_3);
		}
		return panel_2;
	}
	private int scrollEffect(Graphics2D grf,Icon ico,int i){
		ico.paintIcon(this, grf, 0, i+999);//установка внизу за экраном
		//ico.paintIcon(this, grf, 1000, i+999);// справа
		ico.paintIcon(this, grf, 0, i);//в видимой области
	//	ico.paintIcon(this, grf, 1000, i);//в видимой области справа(resizable - false - текстура справа за пределом окна)
	return i;
	}
	private JButton getButton_5() {
		if (button_5 == null) {
			button_5 = new JButton("Start");
			button_5.setFont(new Font("Tahoma", Font.BOLD, 20));
			button_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actionPerformedButton_5(arg0);
				}
			});
			button_5.setIcon(AllImage.SNAKE);
		}
		return button_5;
	}	
	/**
	 * действие по кнопке START для Snake
	 * */
	protected void actionPerformedButton_5(ActionEvent arg0) {
		switch(snakeSizeComboBox.getSelectedIndex()){//выбираем размер поля из выпадающего списка
		case 0: 
			Main.snakeFld = new SnakeField(20,30);
			break;
		case 1:
			Main.snakeFld = new SnakeField(26,42);
			break;
		case 2:
			Main.snakeFld = new SnakeField(30,50);
			break;
		default: return;
		}
		// выбираем сложность 
		Main.snakeFld.setDfficulty(400-80*snakeDiffComboBox.getSelectedIndex());// 400 мс до сдвига фигуры вниз
		// спрашиваем пользователя о готовности если окно закрыл то выход из обработчика
		if(JOptionPane.showOptionDialog(MainFrame.this, 
				"                         ARE YOU READY?", 
				"Start", 
				JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				new String[]{"GO!"},"GO!")==JOptionPane.CLOSED_OPTION) return;
		
		MainFrame.this.setVisible(false);
		SnakeField fs =Main.snakeFld;
		final Timer tmr =fs.letsGo();// запускаем игру
		SnakeFrame sf = new SnakeFrame(fs);// передаем окну поле
		sf.setVisible(true);
		sf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//при закрытии остановка таймера, уничтожение окна возврат в гл меню 
				tmr.stop();
				sf.setVisible(false);
				sf.dispose();
				MainFrame.this.setVisible(true);
			}
		});
	}
	private JButton getButton_6() {
		if (button_6 == null) {
			button_6 = new JButton("Records");
			button_6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actionSnakeRecords(arg0);
				}
			});
			button_6.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
			button_6.setIcon(AllImage.RECORDS);
		}
		return button_6;
	}
	protected void actionSnakeRecords(ActionEvent arg0) {
		showRecords("SNAKE", "Records/Snake.txt");
	}
	private void showRecords(String title, String Path){
		try {
			RecordsFrame rf = new RecordsFrame(title,FileWorker.readTab(Path));
			rf.setVisible(true);
			rf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, 
					"No records", 
					"Empty", 
					JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
		}
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Difficulty: ");
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return label;
	}
	private JComboBox<String> getComboBox_2() {
		if (snakeDiffComboBox == null) {
			snakeDiffComboBox = new JComboBox<String>();
			snakeDiffComboBox.setFont(new Font("Snap ITC", Font.BOLD, 20));
			snakeDiffComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Snail", "Turtle", "Snake", "Guepard"}));
		}
		return snakeDiffComboBox;
	}
	private JComboBox<String> getComboBox_3() {
		if (snakeSizeComboBox == null) {
			snakeSizeComboBox = new JComboBox<String>();
			snakeSizeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
			snakeSizeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Size: 20x30", "Size: 26x42", "Size: 30x50"}));
			snakeSizeComboBox.setSelectedIndex(0);
		}
		return snakeSizeComboBox;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("This button will be very useful for you \u2192");
			label_3.setForeground(Color.WHITE);
			springLayout.putConstraint(SpringLayout.NORTH, label_3, 14, SpringLayout.NORTH, getButton_2());
			springLayout.putConstraint(SpringLayout.EAST, label_3, -6, SpringLayout.WEST, getButton_2());
			label_3.setFont(new Font("Arial", Font.BOLD, 14));
		}
		return label_3;
	}
	private JComboBox getComboBox_2_1() {
		if (comboBox_2 == null) {
			comboBox_2 = new JComboBox();
			comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
			comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {"Size: 20x30", "Size: 26x42", "Size: 30x50"}));
			comboBox_2.setSelectedIndex(0);
		}
		return comboBox_2;
	}
}
