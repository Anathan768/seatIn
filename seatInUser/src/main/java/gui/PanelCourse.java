package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class PanelCourse extends JPanel {
	
	Component c = this;
	String s;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel titlePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel = new JPanel();
	JPanel descriptionPanel = new JPanel();
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel listPanel = new JPanel();
	JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel();
	JLabel idLabel = new JLabel();
	JLabel title1 = new JLabel("Sections");
	JLabel actYear = new JLabel();
	JLabel subject = new JLabel();

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton openButton = new JButton("Open");
	//JButton subButton = new JButton("Subscribe");

	// FIELDS & AREAS
	JTextArea description = new JTextArea(10, 30);

	// TABLES & LAYOUTING
	JList<String> list;
	JScrollPane scroll;

	protected PanelCourse(String id, String year, String name, String course) {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));	

		description.setEditable(false);

		title.setText(name);
		title.setFont(title.getFont().deriveFont(25.0f));
		idLabel.setText(id);
		actYear.setText("Activation year: " + year + " ");
		subject.setText(course);
		

		backButton.addActionListener(new ToMenuAction(this));
		
		
		String[] a = { "Section A", "Section B", "Section C", "Section A", "Section B", "Section C", "Section A", "Section B", "Section C", "Section A", "Section B", "C", "Z", "Z", "Z" };

		list = new JList<String>(a);

		
		scroll = new JScrollPane(list);
		scroll.setPreferredSize(description.getPreferredSize());
		
		
		list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
            //	new ToSection(c,list.getSelectedValue(),list.getSelectedValue());
				s = list.getSelectedValue();
            }
        });
		
		
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ToSection(c, s, s);

			}
		});


		functionPanel.add(openButton);
		
		listPanel.add(scroll);

		titlePanel.add(title);
		titlePanel.add(idLabel);
		
		subTitlePanel.add(subject);
		subTitlePanel.add(Box.createRigidArea(new Dimension(120,0)));
		subTitlePanel.add(actYear);

		
		titlePanel1.add(title1);
		descriptionPanel.add(description);

		backPanel.add(backButton);
		//buttonPanel.add(subButton);
		
		this.add(titlePanel);
		this.add(subTitlePanel);
		this.add(descriptionPanel);
		this.add(titlePanel1);
		this.add(listPanel);
		this.add(functionPanel);
		this.add(backPanel);

	}

}
