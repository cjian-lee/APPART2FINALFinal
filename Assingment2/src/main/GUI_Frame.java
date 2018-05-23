package main;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI_Frame extends JFrame implements ActionListener{

	private JButton btnAddAPersonToTheNetowrk = new JButton("Add a person to the network");
	private JButton btnSelectdelete = new JButton("Select / Delete");
	private JButton btnAssociatedpeople = new JButton("Are there two people connected in some ways?");
	private JButton btnDefinerelationship = new JButton("Define Relationship between two people");
	private JButton btnExit = new JButton("Exit");
	private ArrayList<Person> listPeople;

	private JPanel panel = new JPanel();

	public GUI_Frame(String title, ArrayList<Person> listPeople) throws HeadlessException {
		super(title);
		
		
		btnAddAPersonToTheNetowrk.addActionListener(this);
		btnSelectdelete.addActionListener(this);
		btnAssociatedpeople.addActionListener(this);
		btnDefinerelationship.addActionListener(this);
		btnExit.addActionListener(this);
		this.listPeople=listPeople;
		
		panel.add(btnAddAPersonToTheNetowrk);
		panel.add(btnSelectdelete);
		panel.add(btnAssociatedpeople);
		panel.add(btnDefinerelationship);
		panel.add(btnExit);
		this.add(panel);
		this.pack();
		this.show();
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		
		if(btn ==  btnAddAPersonToTheNetowrk) {
			new AddAPersonGUI(listPeople);
			System.out.println("Please enter your details");
				
		}
		else if(btn == btnAssociatedpeople) 
		{
			new CheckConnectionGUI(listPeople);
			System.out.println("Please highlight two people to check if they are connected");
		}
		else if(btn ==  btnDefinerelationship) {
			new AddConnectionGUI(listPeople);
			System.out.println("Please highlight two people and define their relationship");
		}
		else if(btn == btnSelectdelete) 
		{
			 new DisplayPeopleGUI(listPeople);
			 System.out.println("Please select a person to either view their profile or delete their profile ");
		}
		else if(btn == btnExit)
		{
			System.exit(1);
		}

	}

}
