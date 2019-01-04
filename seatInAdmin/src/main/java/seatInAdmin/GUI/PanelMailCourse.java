package seatInAdmin.GUI;

@SuppressWarnings("serial")
public class PanelMailCourse extends PanelMail{
	
	protected PanelMailCourse(){
		super();
		reciverLabel.setText("Course ID:");
		
	}
	
@Override
protected void sendAction() {
		
		System.out.println("Course");

	}
	
	

}
