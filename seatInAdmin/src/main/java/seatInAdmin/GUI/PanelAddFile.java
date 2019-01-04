package seatInAdmin.GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelAddFile extends JPanel implements ActionListener {

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel title = new JLabel("Add File");
	JLabel pathLabel = new JLabel("File Path: ");

	// FILEDS
	JTextField pathField = new JTextField(20);

	// JBUTTON
	JButton addButton = new JButton("Add");

	protected PanelAddFile() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		title.setFont(title.getFont().deriveFont(25.0f));

		addButton.addActionListener(this);

		panel1.add(pathLabel);
		panel1.add(pathField);

		panel2.add(addButton);

		titlePanel.add(title);

		this.add(titlePanel);
		this.add(panel1);
		this.add(panel2);

	}


	public void actionPerformed(ActionEvent arg0) {

		// TODO AZIONI DA EFFETTUARE DOPO AVER PREMUTO IL BOTTONE

		// Return to Menu
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelResourceAdmin("A","B"));
		frame.pack();
		frame.getContentPane().validate();

	}

}
