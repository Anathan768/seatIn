package seatInAdmin.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelResourceAdmin extends PanelResource {
	
	Component c = this;
	
	JButton deleteButton = new JButton("Delete");
	JButton addButton = new JButton("Add");

	protected PanelResourceAdmin(String id, String name) {
		super(id, name);
		
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
	  
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete action

			}
		});
		
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAddFile());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		

	}

}
