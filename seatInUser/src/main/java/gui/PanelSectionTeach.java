package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelSectionTeach extends PanelSection {
	
	JButton deleteResButton = new JButton("Delete");
	JButton modifyResButton = new JButton("Modify");
	JButton addResButton = new JButton("Add");
	
	JButton deleteSecButton = new JButton("Delete");
	JButton modifySecButton = new JButton("Modify");
	JButton addSecButton = new JButton("Add");
	
	

	protected PanelSectionTeach(String name, String id) {
		super(name, id);
		
		resButtonPanel.add(addResButton);
		resButtonPanel.add(modifyResButton);
		resButtonPanel.add(deleteResButton);
		
		secButtonPanel.add(addSecButton);
		secButtonPanel.add(modifySecButton);
		secButtonPanel.add(deleteSecButton);
	  
		deleteResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete action

			}
		});
		
		modifyResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModResource());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		
		addResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAddResource());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		
		deleteSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete action

			}
		});
		
		modifySecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModSection());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		
		addSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAddSection());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		
		
		
	}

}
