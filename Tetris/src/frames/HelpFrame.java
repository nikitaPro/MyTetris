/**
 * 
 */
package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.AllImage;

/**
 * @author NikitaNB
 *
 */
public class HelpFrame extends JFrame {
	private JPanel panel =null; 
	public static final ImageIcon engineOptimalSel=(ImageIcon)AllImage.engineOptimalSel;
	public HelpFrame() {
		initGUI();
	}
	private void initGUI() {
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(engineOptimalSel.getIconWidth()+18, engineOptimalSel.getIconHeight()+45);
		this.setLocation(screenSize.width/2-this.getSize().width/2, screenSize.height/2-this.getSize().height/2);
		this.setTitle("JTetris");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(panel=new JPanel(){
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D grf = (Graphics2D)g;
				grf.drawImage(engineOptimalSel.getImage(), 0, 0, null);
			}
		});
		panel.setBackground(Color.BLACK);
	}

}
