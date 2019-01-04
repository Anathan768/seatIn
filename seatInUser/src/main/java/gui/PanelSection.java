package gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class PanelSection extends JPanel {

	Component c = this;
	String s;
	String d;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel resButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel secButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel nameLabel = new JLabel();
	JLabel idLabel = new JLabel();
	JLabel subTitleLabel = new JLabel("Sections:");
	JLabel subTitleLabel1 = new JLabel("Resources:");

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton downloadButton = new JButton("Download");
	JButton openResButton = new JButton("Open");
	JButton openSecButton = new JButton("Open");

	// FIELDS
	JTextArea description = new JTextArea(5, 30);

	// TABLES & LAYOUTING
	JList<String> resList;
	JScrollPane resScroll;
	JList<String> secList;
	JScrollPane secScroll;

	protected PanelSection(String name, String id) {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		nameLabel.setText(name);
		nameLabel.setFont(nameLabel.getFont().deriveFont(25.0f));
		idLabel.setText(id);
		description.setEditable(false);
		description.setText("a"); // TODO test

		String[] a = { "Section A", "Section B", "Section C", "Section A", "Section B", "Section C", "Section A", "Section B", "C", "A", "B", "C", "Z", "Z", "Z" };
		
		String[] b = { "Resource A", "Resource B", "Resource C", "Resource A", "Resource B", "Resource C", "Resource A", "Resource B", "Resource C", "A", "B", "C", "Z", "Z", "Z" };

		resList = new JList<String>(b);
		secList = new JList<String>(a);

		secList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				// TODO funz prima lista
				s = resList.getSelectedValue();

			}
		});

		resList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				// TODO funz seconda lista
				s = resList.getSelectedValue();

			}
		});

		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO download action
				System.out.println(s);

			}
		});

		openResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO open action
				new ToResource(c, resList.getSelectedValue(), resList.getSelectedValue());

			}
		});
		
		openSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO open action
				new ToSection(c, secList.getSelectedValue(), secList.getSelectedValue());

			}
		});

		resScroll = new JScrollPane(resList);
		resScroll.setPreferredSize(description.getPreferredSize());

		secScroll = new JScrollPane(secList);
		secScroll.setPreferredSize(description.getPreferredSize());

		titlePanel.add(nameLabel);
		titlePanel.add(idLabel);

		subTitlePanel.add(subTitleLabel);
		subTitlePanel1.add(subTitleLabel1);

		buttonPanel.add(downloadButton);
		buttonPanel.add(openResButton);

		backPanel.add(backButton);

		this.add(titlePanel);
		if (!description.getText().isEmpty()) {
			//System.out.println(description.getText());     //Test code
			this.add(description);
		}
		
		// TODO disabilitare tramite parentid nel caso di sottosessione
		this.add(subTitlePanel);
		this.add(secScroll);
		this.add(secButtonPanel);
		secButtonPanel.add(openSecButton);
		//
		
		this.add(subTitlePanel1);
		this.add(resScroll);
		this.add(resButtonPanel);
		this.add(buttonPanel);
		this.add(backPanel);

	}

}
