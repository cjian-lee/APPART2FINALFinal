package main;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class CheckConnectionGUI extends JFrame implements ActionListener{
	
	private ArrayList<Person> listPeople;
	
	private JButton btnCheck = new JButton("Check connection between these two people");
	
	
	
	private JPanel panel;
	private JPanel buttonPanel = new JPanel();
	
	private JTable table;
	private Object[] columnHeader = new Object[] {"name", "image", "status", "gender", "age"};
	private DefaultTableModel defTableMD;
	private int []rowIndex;
	public CheckConnectionGUI(ArrayList<Person> listPeople) 
	{
		super("All people in the list");
		this.listPeople=listPeople;
		
		btnCheck.addActionListener(this);
		
		
		buttonPanel.add(btnCheck);

		bindData();
		
		
		this.setLayout(new GridLayout(2,1));
		this.add(buttonPanel);
		this.add(panel);
		this.pack();
		this.show();
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		
		if(btn ==  btnCheck) {
		
			rowIndex= table.getSelectedRows();
			
			if(rowIndex.length!=2) { 
			
				JOptionPane.showMessageDialog(this,"Two people is needed");
				return;
			}
			Person p1= listPeople.get(rowIndex[0]);
			Person p2= listPeople.get(rowIndex[1]);
				
			boolean ifConnected = ifTwoPeopleConnected(p1,p2);
			String msg = ifConnected?"":"NOT";
			JOptionPane.showMessageDialog(this,"Two people is "+ msg+ " connected");
			
		
			
		}
		
	}	
}
