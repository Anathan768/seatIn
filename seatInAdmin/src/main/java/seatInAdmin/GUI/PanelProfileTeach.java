package seatInAdmin.GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelProfileTeach extends PanelProfile {

	JLabel department = new JLabel("Department: ");
	JLabel depField = new JLabel();

	public PanelProfileTeach(String name, String surname, String email, String id, String dep) {
		super();
		setTable(dep);
		title.setText("PROFESSOR");
		setValues(name, surname, email, id);
		depField.setText(dep);

		
	}

	private void setTable(String dep){
		
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModTeachProf());
				frame.pack();
				frame.getContentPane().validate();
			}

		});
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		// COMPONENT: COLUMN 0, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(department, gbc);

		// COMPONENT: COLUMN 1, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(depField, gbc);

	}
	
	
	
}


