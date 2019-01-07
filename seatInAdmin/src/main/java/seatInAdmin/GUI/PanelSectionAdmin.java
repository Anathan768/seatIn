package seatInAdmin.GUI;



import javax.swing.JButton;


import seatInServer.JDBC.Beans.Section;

@SuppressWarnings("serial")
public class PanelSectionAdmin extends PanelSection {
	
	JButton deleteResButton = new JButton("Delete");
	JButton modifyResButton = new JButton("Modify");
	JButton addResButton = new JButton("Add");
	
	JButton deleteSecButton = new JButton("Delete");
	JButton modifySecButton = new JButton("Modify");
	JButton addSecButton = new JButton("Add");
	
	

	protected PanelSectionAdmin(Section newSection) {
		super(newSection);
		  		
		
	}

}
