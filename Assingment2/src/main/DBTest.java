package main;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.hsqldb.Server;

import exceptions.NoSuchAgeException;
public class DBTest {
	private Connection connection = null;
	private ResultSet rs = null;
	
	public DBTest() {
		Server hsqlServer = null;
		hsqlServer = new Server();
		hsqlServer.setLogWriter(null);
		hsqlServer.setSilent(true);
		hsqlServer.setDatabaseName(0, "TestDB");
		hsqlServer.setDatabasePath(0, "file:MYDB");
		hsqlServer.start();
		// making a connection
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:TestDB", "","");
			createPeopleTable();
			//displayPeopleTable();
			// // query from the db
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		// end of stub code for in/out stub
		
	}
	

	public boolean displayPeopleTable(ArrayList<Person> personList) 
	{
		  String query = "SELECT * FROM people";
		  try
		  {
			PreparedStatement ps = connection.prepareStatement(query);
		    Person newPerson=null;
		    // process the results
		    rs = ps.executeQuery();
		    while ( rs.next() )
		    {
		    	
		    	 String name = rs.getString("name").trim();
				 String image = rs.getString("image");
				 String status = rs.getString("status");
				 String gender  = rs.getString("gender");
				 int age = rs.getInt("age");
				 String state = rs.getString("state");
				 
		
		         if  (age<=16 && age>=3)  
				 	newPerson = new Child(name, image, status, gender, age, state);
				 else if(age>16)
					 newPerson =  new Adult(name, image, status, gender, age, state);
				 else if (age <=2 && age >=0)
					 newPerson = new YoungChild(name, image, status, gender, age, state);
				 personList.add(newPerson);
		         
		       
		    }
		    rs.close();
		    ps.close();
		    return true;
		  }
		  catch (SQLException se)
		  {
		    return false;
		  }
	}
	
	public void createPeopleTable() throws SQLException 
	{
		connection.prepareStatement("drop table people if exists;").execute();
		connection.prepareStatement("create table people	(name varchar(30), image varchar(30) null, status varchar(30) null, gender varchar(5), age int, state varchar(10) );").execute();
		connection.prepareStatement("insert into people (name, image, status, gender, age, state) values ('Alex Smith','', 'student at RMIT', 'M', 21, 'WA')").execute();
		connection.prepareStatement("insert into people (name, image, status, gender, age, state) values ('Ben Turner', 'BenPhoto.jpg', 'manager at Coles', 'M', 35, 'VIC')").execute();
		connection.prepareStatement("insert into people (name, image, status, gender, age, state) values ('Hannah White', 'Hannah.png', 'student at PLC', 'F', 14, 'VIC')").execute();
			
		connection.commit();
		
		
		

		
	}
}