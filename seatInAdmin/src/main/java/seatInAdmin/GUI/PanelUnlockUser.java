package seatInAdmin.GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelUnlockUser extends JPanel {

	Component c = this;

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel insertionPanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel title = new JLabel("Unlock Account");
	JLabel idLabel = new JLabel("User ID: ");

	// FILEDS
	JTextField idField = new JTextField(15);

	// JBUTTON
	JButton unlockButton = new JButton("Unlock");
	JButton backButton = new JButton("Back");

	protected PanelUnlockUser() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		unlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMenu());
				frame.pack();
				frame.getContentPane().validate();
			}

		});

		backButton.addActionListener(new ToMenuAction(c));

		title.setFont(title.getFont().deriveFont(20.0f));

		insertionPanel.add(idLabel);
		insertionPanel.add(idField);

		buttonPanel.add(unlockButton);
		buttonPanel.add(Box.createHorizontalStrut(75));
		buttonPanel.add(backButton);

		titlePanel.add(title);

		this.add(titlePanel);
		this.add(insertionPanel);
		this.add(buttonPanel);

	}

}
