package seatInAdmin.GUI;

import java.awt.Component;


import javax.swing.JButton;


import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;

@SuppressWarnings("serial")
public class PanelResourceAdmin extends PanelResource {
	
	Component c = this;


	protected PanelResourceAdmin(Resource resource, Section sectionFather) {
		super(resource, sectionFather);
		
	}

}
