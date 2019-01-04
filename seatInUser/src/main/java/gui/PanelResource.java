package gui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class PanelResource extends JPanel {

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel nameLabel = new JLabel();
	JLabel idLabel = new JLabel();
	JLabel subTitleLabel = new JLabel("Files:");

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton downloadButton = new JButton("Download");

	// FIELDS
	JTextArea description = new JTextArea(5, 30);

	// TABLES & LAYOUTING
	JList<String> fileList;
	JScrollPane fileScroll;

	
	
	protected PanelResource(String id, String name){
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		idLabel.setText(id);
		nameLabel.setText(name);
		nameLabel.setFont(nameLabel.getFont().deriveFont(25.0f));
		

		
		String[] a = { "File A", "File B", "File C", "File A", "File B", "File C", "File A", "File B", "File C", "A", "B", "C", "Z", "Z", "Z" };

		fileList = new JList<String>(a);
		
		fileList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				// TODO funz lista

			}
		});
		
		
		fileScroll = new JScrollPane(fileList);
		fileScroll.setPreferredSize(description.getPreferredSize());
		
		
		titlePanel.add(nameLabel);
		titlePanel.add(idLabel);

		subTitlePanel.add(subTitleLabel);
		
		buttonPanel.add(downloadButton);
		backPanel.add(backButton);
		
		this.add(titlePanel);
		this.add(subTitlePanel);
		this.add(fileScroll);
		this.add(buttonPanel);
		this.add(backPanel);
		
		
		
		
		
	}

}
