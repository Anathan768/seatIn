package gui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	public static String s = "";
	
	PanelHome home = new PanelHome();
	
	//TESTS

	//PanelModResource home = new PanelModResource();
	//PanelStats home = new PanelStats();
	//PanelAddFile home = new PanelAddFile();
	//PanelMailCourse home = new PanelMailCourse();
	//PanelResource home = new PanelResource("030986", "Name");
	//PanelSection home = new PanelSection("Section 1", "030986");
	//PanelMenuStd home = new PanelMenuStd();
	//PanelCourse home = new PanelCourse("111111", "20XX", "Fanta-Science", "Mad Science");
	//PanelMenu home = new PanelMenuTeach();
	//PanelRedeem home = new PanelRedeem();
	//PanelProfileTeach home = new PanelProfileTeach("Mario", "Rossi", "asdasd", "123456", "Astronomy");
	//PanelProfileStd home = new PanelProfileStd("Mario", "Rossi", "asdasd", "123456", "IT","20XX","1° Year");
	
	
	public GUI(){
	
		this.setTitle("Seat In User");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(home);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		
		
	}
	


}
