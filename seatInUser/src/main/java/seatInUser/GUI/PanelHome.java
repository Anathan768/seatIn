package seatInUser.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelHome extends JPanel {

	// PANELS
	JPanel rightContainer = new JPanel();
	JPanel leftContainer = new JPanel();
	JPanel infoContainer = new JPanel();
	JPanel mscContainer = new JPanel();
	JPanel notesContainer = new JPanel();
	JPanel titleContainer = new JPanel();
	JPanel buttonContainer = new JPanel();

	// LABELS
	JLabel title = new JLabel("E-Learning");
	JLabel subTitle1 = new JLabel("WELCOME TO INSUBRIA");

	JLabel link1 = new JLabel("<HTML><FONT color=\"#000099\"><U>Insubria Home Website</U></FONT></HTML>");
	JLabel link2 = new JLabel("<HTML><FONT color=\"#000099\"><U>Students' Help</U></FONT></HTML>");
	JLabel link3 = new JLabel("<HTML><FONT color=\"#000099\"><U>Students' E-Mail</U></FONT></HTML>");
	// BUTTONS
	JButton loginButton = new JButton("Login");

	// TEXT FIELDS/AREAS
	JTextArea infoArea = new JTextArea(10, 30);
	JTextArea warningArea = new JTextArea(10, 10);

	protected PanelHome() {
		this.setLayout(new BorderLayout(5, 5));
		title.setFont(title.getFont().deriveFont(25.0f));

		// Layouting
		leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.Y_AXIS));
		rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.Y_AXIS));
		infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.Y_AXIS));
		mscContainer.setLayout(new BoxLayout(mscContainer, BoxLayout.Y_AXIS));
		notesContainer.setLayout(new BoxLayout(notesContainer, BoxLayout.Y_AXIS));
		titleContainer.setLayout(new BorderLayout());

		// Setting TextArea
		infoArea.setEditable(false);
		warningArea.setEditable(false);

		infoArea.setBackground(null);
		warningArea.setBackground(null);

		// Setting Containers
		infoContainer.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Informations"));
		mscContainer.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Useful Links"));
		notesContainer
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Events"));

		//Adjusting spacings
		link1.setBorder((new EmptyBorder(10, 5, 10, 5)));
		link2.setBorder((new EmptyBorder(10, 5, 10, 5)));
		link3.setBorder((new EmptyBorder(10, 5, 10, 5)));
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));
		
		// Linking class functions
		link1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://www.uninsubria.it/").toURI());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		link2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URL("https://www.uninsubria.it/servizi/servizi-di-sportello-segreterie-studenti-e-contatti")
									.toURI());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		link3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://outlook.com/studenti.uninsubria.it").toURI());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// Mounting subpanels

		leftContainer.add(title);
		leftContainer.add(infoContainer);
		leftContainer.add(mscContainer);

		link1.setBorder((new EmptyBorder(10, 5, 10, 5)));
		mscContainer.add(link1);
		link2.setBorder((new EmptyBorder(10, 5, 10, 5)));
		mscContainer.add(link2);
		link3.setBorder((new EmptyBorder(10, 5, 10, 5)));
		mscContainer.add(link3);

		infoContainer.add(subTitle1);
		infoContainer.add(infoArea);

		notesContainer.add(warningArea);

		rightContainer.add(notesContainer);

		buttonContainer.add(loginButton);

		titleContainer.add(title, BorderLayout.CENTER);
		titleContainer.add(buttonContainer, BorderLayout.LINE_END);

		this.add(titleContainer, BorderLayout.PAGE_START);
		this.add(leftContainer, BorderLayout.LINE_START);
		this.add(rightContainer, BorderLayout.LINE_END);

		loginButton.addActionListener(new ToLoginAction(this));

	}

}
