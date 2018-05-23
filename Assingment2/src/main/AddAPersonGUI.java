package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import exceptions.NoSuchAgeException;

public class AddAPersonGUI extends JFrame implements ActionListener {

	
	private JPanel panel=new JPanel();
	private JButton btnAddAPerson = new JButton("Add this Person to the Network");
	
	private JTable table;
	private String[] lblNames = new String[] {"name", "image", "status", "gender", "age", "state"};
	private DefaultTableModel defTableMD;
	private int colIndex;
	private JTextField tfName, tfImage, tfStatus, tfGender, tfAge, tfState;
	private JLabel lblName, lblImage, lblStatus, lblGender, lblAge, lblState;
	private ArrayList<Person> listPeople;
	
	
	public AddAPersonGUI(ArrayList<Person> listPeople) {
		super("Add a person");
		btnAddAPerson.addActionListener(this);
		this.listPeople=listPeople;
		
		
		tfName=new JTextField();
		tfImage=new JTextField();
		tfStatus=new JTextField();
		tfGender=new JTextField();
		tfAge=new JTextField();
		tfState=new JTextField();
		
		lblName = new JLabel(lblNames[0]);
		lblImage = new JLabel(lblNames[1]);
		lblStatus = new JLabel(lblNames[2]);
		lblGender = new JLabel(lblNames[3]);
		lblAge = new JLabel(lblNames[4]);
		lblState = new JLabel(lblNames[5]);
			
		panel.setLayout(new GridLayout(7,2));
		panel.add(lblName);
		panel.add(tfName);
		panel.add(lblImage);
		panel.add(tfImage);
		panel.add(lblStatus);
		panel.add(tfStatus);
		panel.add(lblGender);
		panel.add(tfGender);
		panel.add(lblAge);
		panel.add(tfAge);
		panel.add(lblState);
		panel.add(tfState);
		
		panel.add(btnAddAPerson);
		
		
		this.add(panel);
		this.pack();
		this.show();
		
	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if (btn == btnAddAPerson) {
			String name = tfName.getText();
			String image = tfImage.getText();
			String status = tfStatus.getText();
			String gender = tfGender.getText();
			int age = Integer.parseInt(tfAge.getText());
			String state = tfState.getText();
			
			if (age<0 || age >150) 
			{
				JOptionPane.showMessageDialog(this,"Please enter a valid age");
				return;
			}
			
			
			Person newPerson=null;
			if  (age<=16 && age>=3)  
			 	newPerson = new Child(name, image, status, gender, age, state);
			 else if(age>16)
				 newPerson =  new Adult(name, image, status, gender, age, state);
			 else if (age <=2 && age >=0)
				 newPerson = new YoungChild(name, image, status, gender, age, state);
			
			listPeople.add(newPerson);
			JOptionPane.showMessageDialog(this,"Successfully added");
		}
	}

	
	
}
