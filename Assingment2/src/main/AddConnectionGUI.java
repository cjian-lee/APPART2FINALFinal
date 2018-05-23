package main;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import exceptions.NoAvailableException;
import exceptions.NotToBeClassmatesException;
import exceptions.NotToBeColleaguesException;
import exceptions.NotToBeCoupledException;
import exceptions.NotToBeFriendsException;
import exceptions.TooYoungException;

public class AddConnectionGUI extends JFrame implements ActionListener{

	private ArrayList<Person> listPeople;

	private JButton btnCheck = new JButton("Check connection between these two people");

	private JComboBox typeCombo;

	private JPanel panel;
	private JPanel buttonPanel = new JPanel();

	private JTable table;
	private Object[] columnHeader = new Object[] {"name", "image", "status", "gender", "age"};
	private String[] relationshipType = new String[] {"Partner", "Frined", "Colleagues", "Classmate", "Sibling"};
	private DefaultTableModel defTableMD;
	private int []rowIndex;
	
	public AddConnectionGUI(ArrayList<Person> listPeople) 
	{
		super("All people in the list");
		this.listPeople=listPeople;

		btnCheck.addActionListener(this);

		createTypeCombo();	
		buttonPanel.add(btnCheck);
		buttonPanel.add(typeCombo);
		bindData();


		this.setLayout(new GridLayout(2,1));
		this.add(buttonPanel);
		this.add(panel);
		this.pack();
		this.show();

	
	}


	private void createTypeCombo() {

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		typeCombo = new JComboBox(relationshipType);
		typeCombo.setSelectedIndex(4);
		//typeCombo.addActionListener(this);	
	}


	private void bindData() {
		panel = new JPanel();
		defTableMD =  new DefaultTableModel(columnHeader, 0);

		table = new JTable(defTableMD);


		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(220);
		table.getColumnModel().getColumn(2).setPreferredWidth(220);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		insertData();
		panel.add(table);
	}
	private void insertData() {
		Object[][] array= new Object[listPeople.size()][columnHeader.length];
		int rowIndex=0;
		int colIndex=0;

		for (Person object : listPeople) {

			array[rowIndex][colIndex++]=object.getName();
			array[rowIndex][colIndex++]=object.getImage();

			array[rowIndex][colIndex++]=object.getStatus();

			array[rowIndex][colIndex++]=object.getGender();
			array[rowIndex][colIndex++]=Integer.toString(object.getAge());

			defTableMD.addRow(array[rowIndex]);
			rowIndex++;
			colIndex=0;
		}

	}
	private boolean ifTwoPeopleConnected(Person p1, Person p2)
	{
		for (Relationship re : p1.getRelationship(""))
		{
			String name = re.getPerson().getName();
			if(p2.getName().equalsIgnoreCase(re.getPerson().getName())) 
			{
				return true;
			}
			for (Relationship re2 : p2.getRelationship(""))
			{	
				String name2 = re2.getPerson().getName();
				if(name.equalsIgnoreCase(name2)) {
					System.out.println("found relation");
					return true;
				} 

			}
		} 
		return false;
	}
	private void MakeRelations(String relationshipType, Person personFound, Person personFound2) throws NoAvailableException, 
	NotToBeCoupledException,	NotToBeFriendsException, 
	TooYoungException, NotToBeColleaguesException, NotToBeClassmatesException 
	{

		switch(relationshipType.toLowerCase()){

		case "partner":
			if(personFound2 instanceof Adult){
				Adult personAdult =(Adult)personFound;
				Adult personAdult2 = (Adult)personFound2;
				if(personAdult.getPartner()!=null || personAdult2.getPartner()!=null){
					throw new NoAvailableException("Already connected to someone else");

				}else{
					System.out.println("Partner added");
					personAdult.setPartner(personAdult2);
					personAdult2.setPartner(personAdult);

				}

			}else{

				throw new NotToBeCoupledException("You can't be partner with a non adult.");
			}
			break;

		case "friend":
			if (personFound instanceof Child){
				// child adult
				if (personFound2 instanceof Adult){
					throw new NotToBeFriendsException("Adult can't be friends with child");
				}
				// child child
				else if (personFound2 instanceof Child){
					Child personChild =(Child)personFound;
					Child personChild2 =(Child)personFound2;					

					if(Math.abs(personChild2.getAge() - personChild.getAge())<3)
					{
						personChild.addRelationship("friend", personFound2);
						personChild2.addRelationship("friend", personFound);
					}
					else 
						throw new NotToBeFriendsException("Child friend's age gap must be less than 3");
				}
				// child young children
				else   // young child					
				{
					throw new TooYoungException("Can't be friends with Young Child");

				}
			}
			else if (personFound instanceof Adult)
			{
				if (personFound2 instanceof Adult)
				{

					System.out.println("Friend Added");
					Adult personAdult =(Adult)personFound;
					Adult personAdult2 =(Adult)personFound2;					
					personAdult.addRelationship("Friend", personAdult2);
					personAdult2.addRelationship("Friend", personAdult);
				}
				else if (personFound2 instanceof Child)
				{
					throw new NotToBeFriendsException("Adult can't be friends with child");

				}
				else if (personFound2 instanceof YoungChild)
				{
					throw new TooYoungException("Can't be friends with Young Child");
				}
			}

			break;
		case "colleagues":

			if(personFound instanceof Adult &&	personFound2 instanceof Adult)
			{				
				Adult personAdult =(Adult)personFound;
				Adult personAdult2 = (Adult)personFound2;
				System.out.println("Colleague added");
				personFound.AddRelationship(new Relationship("colleagues",personAdult2));
				personFound2.AddRelationship(new Relationship("colleagues",personAdult));

			}else{
				throw new NotToBeColleaguesException("Unable to connect to colleague for non Adult person");

			}


			break;

		case "classmate":

			if(personFound instanceof Adult && personFound2 instanceof Adult)
			{
				Adult personAdult =(Adult)personFound;
				Adult personAdult2 = (Adult)personFound2;
				System.out.println("Classmate added");
				personFound.AddRelationship(new Relationship("classmate",personAdult2));
				personFound2.AddRelationship(new Relationship("classmate",personAdult));
			}
			else if (personFound instanceof Adult && personFound2 instanceof Child)

			{
				Adult personAdult =(Adult)personFound;
				Child personAdult2 = (Child)personFound2;
				System.out.println("Classmate added");
				personFound.AddRelationship(new Relationship("classmate",personAdult2));
				personFound2.AddRelationship(new Relationship("classmate",personAdult));
			}
			else if(personFound instanceof Child && personFound2 instanceof Adult)
			{
				Child personAdult = (Child)personFound;
				Adult personAdult2 =(Adult)personFound2;

				System.out.println("Classmate added");
				personFound.AddRelationship(new Relationship("classmate",personAdult2));
				personFound2.AddRelationship(new Relationship("classmate",personAdult));


			}else{

				throw new NotToBeClassmatesException("Young child can't be a classmate");
			}

			break;

		case "dependent":

			if(personFound instanceof Adult && personFound2 instanceof Child)
			{
				Adult personAdult =(Adult)personFound;
				Child personAdult2 = (Child)personFound2;
				System.out.println("Parent added");
				personFound.AddRelationship(new Relationship("dependent",personAdult2));
				personFound2.AddRelationship(new Relationship("parent",personAdult));

			}
			else if(personFound instanceof Adult && personFound2 instanceof YoungChild)
			{
				Adult personAdult =(Adult)personFound;
				YoungChild personAdult2 = (YoungChild)personFound2;
				System.out.println("Parent added");
				personFound.AddRelationship(new Relationship("parent",personAdult2));
				personFound2.AddRelationship(new Relationship("dependent",personAdult));

			}
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();

		if(btn ==  btnCheck) {
			String type = typeCombo.getSelectedItem().toString();

			rowIndex= table.getSelectedRows();

			if(rowIndex.length!=2) { 

				JOptionPane.showMessageDialog(this,"Two people is needed");
				return;
			}
			Person p1= listPeople.get(rowIndex[0]);

			Person p2= listPeople.get(rowIndex[1]);
			try {
				MakeRelations(type, p1, p2);

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this,e2.getMessage());
				return;
			}


			JOptionPane.showMessageDialog(this,"Two people is now connected");



		}

	}	
}
