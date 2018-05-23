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

public class DisplayPeopleGUI extends JFrame implements ActionListener{
	
	private ArrayList<Person> listPeople;
	
	private JButton btnSelect = new JButton("Display Profile of Selected Person");
	private JButton btnDelete = new JButton("Delete a Person");
	
	
	private JPanel panel;
	private JPanel buttonPanel = new JPanel();
	
	private JTable table;
	private Object[] columnHeader = new Object[] {"name", "image", "status", "gender", "age"};
	private DefaultTableModel defTableMD;
	private int rowIndex;
	public DisplayPeopleGUI(ArrayList<Person> listPeople) 
	{
		super("All people in the list");
		this.listPeople=listPeople;
		
		btnSelect.addActionListener(this);
		btnDelete.addActionListener(this);
		
		buttonPanel.add(btnSelect);
		buttonPanel.add(btnDelete);

		bindData();
		
		
		this.setLayout(new GridLayout(2,1));
		this.add(buttonPanel);
		this.add(panel);
		this.pack();
		this.show();
		
	}
	/**
	 * to bind all data within JPanel
	 */	
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
	
	/**
	 * to insert data into column headers
	 */	
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		rowIndex= table.getSelectedRow();
		if(rowIndex<0) return;
		
		if(btn ==  btnSelect) {
			System.out.println("ur select is "+ rowIndex);
			
			String det = listPeople.get(rowIndex).detailProfile();
			Panel panel = new Panel();
			JLabel l = new JLabel();
			l.setText(det);
			
			panel.add(l);
			final JDialog frame = new JDialog(this, "Details", true);
			frame.getContentPane().add(panel);
			frame.pack();
			frame.setVisible(true);
			frame.show();
			
		}
		else if(btn == btnDelete) 
		{
			listPeople.remove(rowIndex);
			JOptionPane.showMessageDialog(this,"Person Deleted");
			this.dispose();
			//bindData();
		}

	}	
}
