/**
 * 
 */
package frames;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JList;

import java.awt.Color;

import javax.swing.JSplitPane;

import main.Main;

/**
 * @author NikitaNB
 *
 */
public class RecordsFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> model = null;
	private DefaultListModel<String> model2 = null;
	private JLabel lblRecords;
	public RecordsFrame(String nameOfRec, ArrayList<String> rec) {
		lblRecords = new JLabel(nameOfRec+" RECORDS");

		initGUI(rec);
	}
	private void initGUI(ArrayList<String> rec) {
		getContentPane().setBackground(new Color(255, 127, 80));
		this.setTitle("Records");
		this.setSize(350,300);
		setMinimumSize(new Dimension(600,300));
		this.setLocation(Main.screenSize.width/2-Main.screenSize.width/2, Main.screenSize.height/2-Main.screenSize.height/2);
		lblRecords.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecords.setVerticalAlignment(SwingConstants.TOP);
		lblRecords.setFont(new Font("Sylfaen", Font.BOLD, 16));
		getContentPane().add(lblRecords, BorderLayout.NORTH);
		model = new DefaultListModel<String>();
		model2 = new DefaultListModel<String>();
		model.addElement("===NAME===");
		model2.addElement("===SCORE===");
		for(int i=0; i<rec.size();i+=2){
			model.addElement(rec.get(i));
			model2.addElement(rec.get(i+1));
		}		
		JSplitPane splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setResizeWeight(0.5);
		JList<String> list = new JList<String>();
		splitPane.setLeftComponent(list);
		list.setForeground(new Color(255, 248, 220));
		list.setBackground(new Color(47, 79, 79));
		list.setFont(new Font("Magic Cards", Font.BOLD | Font.ITALIC, 25));
		list.setModel(model);
		JList<String> list2 = new JList<String>();
		splitPane.setRightComponent(list2);
		list2.setForeground(new Color(255, 248, 220));
		list2.setBackground(new Color(47, 79, 79));
		list2.setFont(new Font("Magic Cards", Font.BOLD | Font.ITALIC, 25));
		list2.setModel(model2);
	}

}
