package main;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import exceptions.NoSuchAgeException;


public class ReadFileClass {

	private final String peopleFile = "people.txt";
	private final String relationsFile = "relation.txt";
	private StringTokenizer st;
	public void readPeopleFile(ArrayList<Person> personList) throws NoSuchAgeException, FileNotFoundException 
	{
		String str = ""; 
		try { 
			FileReader file = new FileReader(peopleFile); 
			BufferedReader buff = new BufferedReader(file); 
			 
			 while((str = buff.readLine())!=null)
			 {
				 st = new StringTokenizer(str,",");
			 
				 String name = st.nextToken().trim();
				 String image = st.nextToken();
				 String status = st.nextToken();
				 String gender  = st.nextToken();
				 int age = Integer.parseInt(st.nextToken().trim());
				 if (age<0 || age >150) 
						throw new NoSuchAgeException("Invalid age in "+ peopleFile);
				 String state = st.nextToken();
				 Person newPerson=null;
				 if  (age<=16 && age>=3)  
				 
				 	newPerson = new Child(name, image, status, gender, age, state);
				 else if(age>16){ 
					 newPerson =  new Adult(name, image, status, gender, age, state);
				 }
				 else if (age <=2 && age >=0)
					 newPerson = new YoungChild(name, image, status, gender, age, state);
				 personList.add(newPerson);
			 
	           
			 }          
	          
			buff.close();
			
		}
		catch (FileNotFoundException e) {
			throw e; 
		}
	
		catch (IOException e) {
			System.out.println(e.getMessage()); 
		}
	     
	}
	
	private Person FindPersonInList(String name, ArrayList<Person> personList) {
		for (Person person : personList) {
			if(person.getName().equalsIgnoreCase(name)) return person;
		}
		return null;
	}
	
	public boolean readRelationsFile(ArrayList<Person> personList) {

		String str = ""; 
		if(personList.size()==0) return false;
		
		try { 
			FileReader file = new FileReader(relationsFile); 
			BufferedReader buff = new BufferedReader(file); 
			 
			 while((str = buff.readLine())!=null)
			 {
				 st = new StringTokenizer(str,",");
				 
				 String person1Name = st.nextToken().trim();
				 String person2Name = st.nextToken().trim();
				 String type = st.nextToken().trim();
				 
				 Person p1 = FindPersonInList(person1Name,personList);
				 Person p2 = FindPersonInList(person2Name,personList);
				 if(p1!=null && p2!=null)
				 {
					 p1.AddRelationship(new Relationship(type, p2));
					 p2.AddRelationship(new Relationship(type, p1));
					 
				 }
				 else{
					 
					 System.out.println("Relationship can't be added in the file "+ str);
				 }
			
			 }          
	          
			buff.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage()); 
		}
	
		catch (IOException e) {
			System.out.println(e.getMessage()); 
		}
		return true;
		
		
	}
}
