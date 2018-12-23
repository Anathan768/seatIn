package seatInServer.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ServerMenu extends JPanel{
	
	// PANELS
		JPanel panel1 = new JPanel();
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// LABELS
		JLabel title = new JLabel("SERVER CONSOLE");

		// BUTTONS
		JButton serverConfig = new JButton("Server Configuration");
		JButton login = new JButton("Login");
		JButton registration = new JButton("Registration");
		JButton resetPsw = new JButton("Reset Password");

		public ServerMenu() {

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
			title.setFont(title.getFont().deriveFont(25.0f));
			
			this.setBorder((new EmptyBorder(5, 5, 10, 5)));


			serverConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
			login.setAlignmentX(Component.CENTER_ALIGNMENT);
			registration.setAlignmentX(Component.CENTER_ALIGNMENT);
			resetPsw.setAlignmentX(Component.CENTER_ALIGNMENT);
			Dimension d = serverConfig.getMaximumSize();
			login.setMaximumSize(new Dimension(d));
			registration.setMaximumSize(new Dimension(d));
			resetPsw.setMaximumSize(new Dimension(d));
			
			
			titlePanel.add(title);

			
			serverConfig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					serverConfigAction();
					
				}
			});
			

			login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loginAction();
					
				}
			});

			registration.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					registrationAction();
				}
			});
			
			resetPsw.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resPswAction();
				}
			});
			

			panel1.add(serverConfig);
			panel1.add(Box.createRigidArea(new Dimension(0,10)));
			panel1.add(login);
			panel1.add(Box.createRigidArea(new Dimension(0,10)));
			panel1.add(registration);
			panel1.add(Box.createRigidArea(new Dimension(0,10)));
			panel1.add(resetPsw);
			
			
			this.add(titlePanel);
			this.add(panel1);
			

		}
		
		private void loginAction(){
			
			JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new ServerLogin());
			frame.pack();
			frame.getContentPane().validate();
			
		}
		
		private void registrationAction(){
			
			JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new ServerRegistration());
			frame.pack();
			frame.getContentPane().validate();
			
			
		}
		
		private void resPswAction(){
			
			JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new ServerResPsw());
			frame.pack();
			frame.getContentPane().validate();
			
			
		}
		
	private void serverConfigAction(){
			
			JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new ServerConfig());
			frame.pack();
			frame.getContentPane().validate();
			
			
		}

}
