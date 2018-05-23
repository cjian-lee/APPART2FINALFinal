package main;


import java.awt.image.ImagingOpException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.ExceptionHelper;
import exceptions.NoAvailableException;
import exceptions.NoParentException;
import exceptions.NoSuchAgeException;
import exceptions.NotToBeClassmatesException;
import exceptions.NotToBeColleaguesException;
import exceptions.NotToBeCoupledException;
import exceptions.NotToBeFriendsException;
import exceptions.TooYoungException;



public class Community {

	/**
	 * Person type ArrayList to store "qualified" people in community
	 */
	private ArrayList<Person> personList = new ArrayList<>();
	private Scanner sc = new Scanner(System.in);
	private ReadFileClass fileReader = new ReadFileClass();

	private boolean loadPeople, loadRelationship;

	public void establishCommunity() {
		try {
			loadPeople = initialPeople();
			if(loadPeople==false)
			{
				// read from db
				DBTest dbConn= new DBTest();
				loadPeople = dbConn.displayPeopleTable(personList);
				System.out.println("read from db"); 

			}
			loadRelationship= fileReader.readRelationsFile(personList);
			if(loadRelationship && loadPeople)
				printMenu();
			else {
				System.out.println("Failed to load either people or relationships");
			}


		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}

	}

	/**
	 * initial data of different types of people for user's ease of use
	 * two children "Lily" and "Luke" were set as dependent of Jake & Lucy and Mike & Jay respectively
	 */
	private boolean initialPeople() throws FileNotFoundException{ 

		try {
			fileReader.readPeopleFile(personList);		
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}

	}

	/**
	 * to print menu, ask user to input and direct to corresponding method based on user input.
	 */
	private void printMenu() {
		boolean flag = false;
		while (!flag) {
			System.out.println("\n----------------------------------------\n"+
					"|              MiniNet Menu            |\n" +
					"----------------------------------------\n" +
					"| 1  ->  List Everyone                 |\n" +
					"| 2  ->  Select a person               |\n" +
					"| 3  ->  Add new person                |\n" +
					"| 4  ->  Remove Existing Person        |\n" +
					"| 5  ->  Are these two direct friends? |\n" +
					"| 6  ->  Are there mutual friends?     |\n" +
					"| 7  ->  Use GUI                       |\n" +
					"| 0  ->  Exit MiniNet                  |\n" +
					"----------------------------------------\n" +
					"Input number of function: ");
			String choice = sc.next();
			switch (choice) {
			case "1":
				listEveryone();
				break;
			case "2":
				updateSelected(selectPerson());
				break;
			case "3":
				addToCommunity();
				break;
			case "4":
				removePerson();
				break;
			case "5":
				directFriend();
				break;
			case "6":
				ifMutalFriend();
				break;
			case "7":
				showGUI();
				break;
			case "0":
				flag = true;
				break;
			default:
				System.out.println("Invalid Input, please try again: ");
			}
		}
	}

	private void showGUI() {
		new GUI_Frame("Welcome to GUI", personList);
	}

	private void ifMutalFriend() {
		Person p1 = selectPerson();
		Person p2 = selectPerson();
		System.out.println(ifTwoPeopleConnected(p1, p2));
		//ifTwoPeopleConnected();

	}

	/**
	 * to list everyone's name within community
	 */
	private void listEveryone() {
		System.out.println("===============================================");
		System.out.println("|         Listing People in Community         |");
		System.out.println("===============================================");
		if (personList.size() == 0) {
			System.out.println("|                                             |");
			System.out.println("|         There's no one in community         |");
			System.out.println("|                                             |");

			return;
		}
		String personType;
		DisplayAllPeople();

	}

	/**
	 * to display menu for Adult
	 */
	private void displayAdultMenu(Person person){
		boolean flag = false;
		do {
			System.out.println("________________________________________________________________\n" +
					"Input following number for further manipulations on " + person.getName() + "\n" +
					"1  ->  Print Profile\n" +
					"2  ->  Add Relationship\n" +
					"3  ->  Update Name\n" +
					"4  ->  Update Age\n" +
					"5  ->  Update Gender\n" +
					"6  ->  Update Status\n" +
					"7  ->  Modify Existing Relationship\n" +
					"8  ->  Switch ON/OFF Image\n" +
					"0  ->  Return to Main Menu\n" +
					"_________________________________________________________________\n" +
					"Please input your choice:");
			String choice = sc.next();
			if (choice.equals("1")) {
				person.printProfile();
			} else if (choice.equals("2")) {
				addRelationship(person);
			} else if (choice.equals("3")) {
				updateName(person);
			} else if (choice.equals("4")) {
				updateAge(person);
			} else if (choice.equals("5")) {
				updateGender(person);
			} else if (choice.equals("6")) {
				updateStatus(person);
			} else if (choice.equals("7")) {
				modifyRelationship(person);
			} else if (choice.equals("8")) {
				switchOnOffImage(person);
			} else if (choice.equals("0")) {
				System.out.println("RETURNING BACK TO MAIN MENU...");
				flag = true;
			} else{
				System.out.println("! Invalid Input. Please try again !");
			}
		} while (!flag);

	}

	/**
	 * to display menu for Child and Young Child
	 */
	
	private void displayChildMenu(Person person){
		boolean flag = false;
		do {
			System.out.println("__________________Child Menu________________________________________\n" +
					"Input following number for further manipulations on " + person.getName() + "\n" +
					"1  ->  Print Profile\n" +
					"2  ->  Add Relationship\n" +
					"3  ->  Update Name\n" +
					"4  ->  Update Gender\n" +
					"5  ->  Update Status\n" +
					"6  ->  Modify Existing Relationship\n" +
					"7  ->  Switch ON/OFF Image\n" +
					"0  ->  Return to Main Menu\n" +
					"_________________________________________________________________\n" +
					"Please input your choice:");
			String choice = sc.next();
			if (choice.equals("1")) {
				person.printProfile();
			} else if (choice.equals("2")) {
				addRelationship(person);
			} else if (choice.equals("3")) {
				updateName(person);
			} else if (choice.equals("4")) {
				updateGender(person);
			} else if (choice.equals("5")) {
				updateStatus(person);
			} else if (choice.equals("6")) {
				modifyRelationship(person);
			} else if (choice.equals("7")) {
				switchOnOffImage(person);
			} else if (choice.equals("0")) {
				System.out.println("RETURNING BACK TO MAIN MENU...");
				flag = true;
			} else{
				System.out.println("! Invalid Input. Please try again !");
			}
		} while (!flag);
	}

	/**
	 * present a submenu when a person was selected
	 * @param person to pass in a person to manipulate this person
	 */
	private void updateSelected(Person person) {
		if (person == null) {
			return;
		} else {
			if(person instanceof Adult){
				displayAdultMenu(person);

			}
			else if(person instanceof Child){
				displayChildMenu(person);

			}


		}
	}

	/**
	 *
	 * @param person pass in person and change his name
	 */
	private void updateName(Person person) {
		String newName;
		boolean isValid;
		do {
			isValid = true;
			System.out.println("Input new name : ");
			newName = sc.next().trim();
			for(int i=0; i<personList.size();i++) {
				if(personList.get(i).getName().equals(newName)) {
					isValid = false;
				}
			}
			if(newName.isEmpty()){
				System.out.println("! Cannot Have Empty Name !" );
			}
			if(!isValid){
				System.out.println("! The Name of " + newName + " Has Already Been Used !");
			}
		} while(newName.isEmpty() || !isValid);
		person.setName(newName);
		System.out.println("Name Update Successful");


	}

	/**
	 * user is not allowed to change a child to adult and vise versa
	 * @param person pass in person and change his age
	 */
	private void updateAge(Person person) {

		System.out.println("Input age:");
		int age = sc.nextInt();

		try	
		{
			ageValidation(age);
		}catch(Exception e){

			System.out.println(e.getMessage());
		}
		if (person.getAge() < 16 && age >= 16) {
			System.out.println("! Cannot Change Child To Adult !");
			System.out.println("! Age Update Not Successful !");
		} else if (person.getAge() >= 16 && age < 16) {
			System.out.println("! Cannot Change Adult To Child !");
			System.out.println("! Age Update Not Successful !");
		} else {
			person.setAge(age);
			System.out.println("Age Update Successful");
		}
	}

	/**
	 * @param person pass in person and change his gender
	 */
	private void updateGender(Person person) {
		System.out.println("Input New Gender:");
		person.setGender(sc.nextLine());
		System.out.println("Gender Update Successful");
	}

	/**
	 * @param person pass in person and change his status
	 */
	private void updateStatus(Person person) {
		System.out.println("Input New Status:");
		person.setStatus(sc.nextLine());
		System.out.println("Status Update Successful");
	}

	/**
	 * @param person pass in person and switch on/off his image display
	 */
	private void switchOnOffImage(Person person){

		String status = person.getImage().length()==0?"Off":"On";


		System.out.println("Image is currently " + status);
		System.out.println("Do you want to change the image?");
		String s = yesNoValid();

		if(s.equalsIgnoreCase("y")){
			System.out.println("Please enter new file name");
			String newFilename=sc.next();
			person.setImage(newFilename);
			System.out.println("Image has been changed to " + newFilename);
		}else{
			person.setImage("");
			System.out.println("Update Not Successful. User Choose Not to Update");
		}

	}

	/**
	 * @return person based on user input
	 */
	private Person selectPerson() {
		sc= new Scanner(System.in);
		listEveryone();
		String personIndex;
		for(;;)
		{
			try {
				if (personList.size() != 0) {
					System.out.println("Input Person's index For Further Manipulation (input '/' to return):");
					boolean flag = false;
					while (!flag) {
						personIndex = sc.next();
						if(personIndex.equals("/")){
							System.out.println("RETURNING TO MAIN MENU.....");
							return null;
						}
						else
						{
							return personList.get(Integer.parseInt(personIndex)-1);
						}
					}
				}else{
					System.out.println("Current no one in the list");
						
				}
			}
			catch (NumberFormatException ime) {
		     	System.out.println("Invalid format");	
			}
			catch (InputMismatchException ime) {
				ime.printStackTrace();
			}
			catch(Exception e) {
				
			}
		}
		//return null;
	}

			



	/**
	 * to add relationship to an adult or child
	 * @param person pass in super class reference and judge if the reference is point to an adult
	 *               or child using instanceof and do corresponding casting
	 */
	private void addRelationship(Person person) {

		try {
			if (personList.size() == 1) {
				System.out.println("No other person to have relationship with, add another person first.");
				return;
			}
			if (person instanceof Adult) {
				addAdultRelation((Adult) person);
			} else if (person instanceof Child) {
				addChildrenRelation((Child) person);
			}
		} catch (NoAvailableException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Children who are 2 or under 2 years old cannot have any friends.
	 * Children who are 2 or under 2 years old have dependent/parent relationship established once they
	 *    were added to community.
	 * Children who are above 2 years old can have "friend" relationship under certain constraints.
	 * @param childObj
	 */
	private void addChildrenRelation(Child childObj) {
		if (childObj.getAge() <= 2) {
			System.out.println("Children who is 2 or under 2 years old cannot have any friend");
		} else {
			System.out.println("Specify relationship type\n1. Friend\n2. Sibling\n3. Classmate" );

			int relationshipType = sc.nextInt();

			System.out.println("People in community to have relationship with: ");
			int counter = 1;
			for (int i = 0; i < personList.size(); i++) {
				System.out.print(counter + ": ");
				System.out.printf("%-15s|", personList.get(i).getName());
				System.out.println("  Age: " + personList.get(i).getAge());
				counter++;

			}
			System.out.println("Input person's number to establish a relationship:");
			int personIndex =sc.nextInt();
			Person personFound  = personList.get(personIndex-1);



			try {
				switch(relationshipType){
				case 1:
					MakeRelations("Friend", childObj, personFound);


					break;
				case 2:
					MakeRelations("Sibling", childObj, personFound);


					break;

				case 3:
					MakeRelations("Classmate", childObj, personFound);

					break;
				}
			} 
			catch (Exception e) {
				System.err.println(e.getMessage());
			}

		}
	}
	private void ageValidation(int age) throws NoSuchAgeException{

		if (age<0 || age >150) 
			throw new NoSuchAgeException("Please enter a valid age");
	}
	/**
	 * To add new relationship to an adult.
	 * adult who is already someone's partner cannot add or be added new "partner" relationship     *
	 * @param adult
	 * @throws NoAvailableException 
	 */
	private void addAdultRelation(Adult adult) throws NoAvailableException 
	{
		String personName;
		System.out.println("People in community to have relationship with: ");
		int counter = 1;
		for (int i = 0; i < personList.size(); i++) 
		{
			System.out.print(counter + ": ");
			System.out.printf("%-15s|", personList.get(i).getName());
			System.out.println("  Age: " + personList.get(i).getAge());
			counter++;

		}

		Person personFound  = null;

		
		for(;;) 
		{
			try 
			{
				sc = new Scanner(System.in);
				DisplayAllPeople();
				System.out.println("Input person's number to establish a relationship:");
				int personIndex =sc.nextInt();

				boolean validInput = false;
				personFound  = personList.get(personIndex-1);
				if (personFound != null && !personFound.getName().equalsIgnoreCase(adult.getName()))
					validInput = true;

				System.out.println("Specify relationship type\n1. Partner\n2. Friend\n3. Colleague\n4. Classmate");


				int relationshipType = sc.nextInt();
				switch(relationshipType)
				{
				case 1:

					MakeRelations("Partner", adult, personFound);


					break;
				case 2:
					MakeRelations("Friend", adult, personFound);


					break;
				case 3:
					MakeRelations("Colleagues", adult, personFound);
					break;

				case 4:
					MakeRelations("Classmate", adult, personFound);
					break;
				}
				break;

			}	
			catch (InputMismatchException ime)
			 
			{	

				System.out.println("Invalid input. please enter an integer!");
			} 
			catch (NotToBeCoupledException e) 
			{
				e.printStackTrace();
			} catch (NotToBeFriendsException e) {
				e.printStackTrace();
			} catch (TooYoungException e) {
				e.printStackTrace();
			} catch (NotToBeColleaguesException e) {
				e.printStackTrace();
			} catch (NotToBeClassmatesException e) {
				e.printStackTrace();
			}

		}

	}


	/**
	 * to add different kind of relationships
	 */	
	private void MakeRelations(String relationshipType, Person personFound, Person personFound2) throws NoAvailableException, 
	NotToBeCoupledException,	NotToBeFriendsException, 
	TooYoungException, NotToBeColleaguesException, NotToBeClassmatesException 
	{
		sc= new Scanner(System.in);


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
	/**
	 * to display all people in the social network
	 */
	private void DisplayAllPeople()
	{
		int counter=1;

		for (Person curPerson : personList)
		{			
			System.out.println(counter + "- Name : "+ curPerson.getName());
			counter++;
		}

	}

	/**
	 * checking if 2 people have direction relationship
	 * not allowed to check if there's nobody or only one in community
	 */
	private void directFriend() {
		if (personList.size() == 0) {
			System.out.println("=============================");
			System.out.println("|There's no one in community|");
			System.out.println("|Try to add two people first|");
			System.out.println("=============================");
		} else if (personList.size() == 1) {
			System.out.println("=============================");
			System.out.println("|Only one person in community|");
			System.out.println("|Try to add another first    |");
			System.out.println("==============================");
		} else {
			listEveryone();
			String firstName, secondName;
			do {
				System.out.println("Input first person's name (input '/' to return):");
				firstName = sc.nextLine();
				if (firstName.equals("/")){
					System.out.println("Returning to Main Menu....");
					return;
				}
				if (findPerson(firstName) == null) {

					System.out.println("Cannot find first person, please try again.");
				}
			}while(findPerson(firstName) == null);
			do{
				System.out.println("Input second person's name (input '/' to return):");
				secondName = sc.nextLine();
				if (secondName.equals("/")){
					System.out.println("Returning to Main Menu....");
					return;
				}
				if (findPerson(secondName) == null) {
					System.out.println("Cannot find second person, please try again.");
				}
			} while (findPerson(secondName) == null);
			Person firstPerson = findPerson(firstName);
			Person secondPerson = findPerson(secondName);
			if (firstPerson.withRelationship(secondPerson)) {
				System.out.println(firstName + " is " + firstPerson.getRelationship("").get(firstPerson.relatedIndex(secondPerson)).getType()
						+ " of " + secondName);
			} else {
				System.out.println(firstName + " is not in relationship with " + secondName);
			}
		}
	}


	/**
	 * to remove existing person from community
	 * not allowed to remove if there's no one in community
	 */
	private void removePerson() {
		String deleteName;
		Person deletePerson;
		if (personList.size() == 0) {
			System.out.println("=============================");
			System.out.println("|There's no one in community|");
			System.out.println("|Try to add one person first|");
			System.out.println("=============================");
		} 
		else 
		{

			deletePerson = selectPerson();

			if (deletePerson instanceof Adult) 
			{	
				deleteAdult(deletePerson);
			} 
			else 
				deletePerson(deletePerson);

		}
	}

	/**
	 * to delete an adult from community
	 * delete adult who has a dependent will lead to deletion of both if user confirm it
	 *      and his partner will be available for a new partner relationship
	 * @param deletePerson
	 */
	private void deleteAdult(Person deletePerson) {
		if (!((Adult) deletePerson).getHasDependent()) {
			deletePerson.removeRelated();
			personList.remove(deletePerson);
			System.out.println(deletePerson.getName() + " was deleted.");
		}
		else 
		{
			System.out.println("Unable to delete because adult has dependent");

		}
	}

	/**
	 * to delete a dependent from community
	 * delete a dependent will not delete his parents. after deletion, his parent will still be in "partner"
	 *      relationship, but they will become available parents for a new dependent.
	 * @param deletePerson
	 */
	private void deletePerson(Person deletePerson) 
	{
		if(deletePerson instanceof Child) {
			Child c = (Child)deletePerson;
			c.getRelationship("parent");
		}
		else if(deletePerson instanceof YoungChild) {
			YoungChild yc = (YoungChild)deletePerson;

		}

		deletePerson.removeRelated();
		personList.remove(deletePerson);
		System.out.println(deletePerson.getName() + " was deleted.");
	}

	/**
	 * to add a person into community and create his own profile
	 * status is optional: either input by user or "Not Available" by default
	 * image is optional: either to switch on or off
	 * adding adult will not affect other people in community
	 * adding dependent will force user to assign two parents for the new dependent
	 * adding dependent will not be successful if there's not enough (at least 2) parents currently in community
	 */
	private void addToCommunity() {
		sc= new Scanner(System.in);
		String name;
		boolean isValid;
		do {
			isValid = true;
			System.out.println("Input new name : ");
			name = sc.next().trim();
			for(int i=0; i<personList.size();i++) {
				if(personList.get(i).getName().equals(name)) {
					isValid = false;
				}
			}
			if(name.isEmpty()){
				System.out.println("! Cannot Have Empty Name !" );
			}
			if(!isValid){
				System.out.println("! The Name of " + name + " Has Already Been Used !");
			}
		} while(name.isEmpty() || !isValid);

		int age ;
		
		for(;;)
		{

			try	
			{
				sc= new Scanner(System.in);
				System.out.println("Input age:");
				age = sc.nextInt();

				ageValidation(age);

				if (age<=16) //child or young child
				{
					System.out.println("Please enter\n 1.Child\n2.Young Child\n");
					int choice= sc.nextInt();
					if (choice == 1) 
					{
						if (age>16 || age<3) 
						{
							System.out.println("You can't create a child with that age");  

						}
						else {
							break;
						}
					}
					else if (choice == 2)
					{
						if (age>2 || age<=0)
						{
							System.out.println("You can't create a young child with that age");

						}
						else 
						{
							break;
						}
					}
				}else 
				{
					break;	

				}
			}catch(Exception e){

				System.out.println(e.getMessage());
			}
		}

		System.out.println("Input gender:");
		String gender = sc.next();
		System.out.println("Do you wish to set up status now? (y/n)");
		String choice = yesNoValid();   // to force user to input either "y" for yes or "n" for no
		String status = "Not available";
		if (choice.equals("y")) {
			System.out.println("Input status:");
			status = sc.nextLine();
		} else if (choice.equals("n")) {
			status = "Not available";
		}
		System.out.println("Do you wish to display profile image now? (y/n)");
		String yesNo = yesNoValid();
		String image;
		if(yesNo.equalsIgnoreCase("y"))
		{
			System.out.println("Please enter your photo name");
			image =  sc.next();
		}
		else {
			image="";
		}
		System.out.println("What is the state you live in");
		String state = sc.next();
		if (age >= 16) {                    // adult can be added to community directly
			personList.add(addToAdult(name, age, gender, status, image,state));
			System.out.println(name + " added to the community.");
		} else {                            // adding dependents will force user to assign 2 parents first
			if (getAvailParent() < 2) {     // failed to add a dependent if there's not enough parents
				System.out.println("Cannot add " + name + ". Not enough adults available.");
			}
			else 
			{
				try {
					addTwoParents(name, age, gender, status, image,state);

				} catch (NoParentException | NoAvailableException | NotToBeCoupledException | NotToBeFriendsException | TooYoungException | NotToBeColleaguesException | NotToBeClassmatesException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}



	/**
	 * to add two qualified parents to a newly added dependent
	 * 2 parents can be in "partner" relationship but each of them must have no dependent
	 * if either parents has "partner" relationship with someone else but has no dependent,
	 *      remove "partner" relationship with existing partner and establish a new "partner"
	 *      relationship with new partner according to user input
	 * @param name
	 * @param age
	 * @param gender
	 * @param status
	 * @param image
	 * @throws NoParentException 
	 * @throws NotToBeClassmatesException 
	 * @throws NotToBeColleaguesException 
	 * @throws TooYoungException 
	 * @throws NotToBeFriendsException 
	 * @throws NotToBeCoupledException 
	 * @throws NoAvailableException 
	 */
	private void addTwoParents(String name, int age, String gender, 
			String status, String image, String state) throws NoParentException, NoAvailableException, NotToBeCoupledException, NotToBeFriendsException, TooYoungException, NotToBeColleaguesException, NotToBeClassmatesException
	{
		sc= new Scanner(System.in);

		Person p1, p2;

		Adult adult1, adult2;
		do {
			p1 = selectPerson();
			p2 = selectPerson();

			if(p1==null || p2==null)
			{
				System.out.println("! Invalid Input. Please Input Names Of Following Available Adults !");
				listAvailParent();
			}else{
				if(p1 instanceof Adult && p2 instanceof Adult)
					break;
			}


		}while(p1 == null || p2 == null);  //user input has to be valid


		adult1 = (Adult)p1;
		adult2 = (Adult)p2;


		ArrayList<Relationship> p1Rel = p1.getRelationship("partner");
		ArrayList<Relationship> p2Rel = p2.getRelationship("partner");

		if(p1Rel.size()>0 && p2Rel.size()>0){
			Adult p1Partner = (Adult)p1Rel.get(0).getPerson();
			Adult p2Partner = (Adult)p2Rel.get(0).getPerson();

			if(adult1.getName().equalsIgnoreCase(p2Partner.getName()) && adult2.getName().equalsIgnoreCase(p1Partner.getName()))	
			{
				Child newDependent = new Child(name, image, status, gender, age, state);


				MakeRelations("dependent", p1Partner,newDependent );
				MakeRelations("dependent", p2Partner,newDependent );

				personList.add(newDependent);


				System.out.println(name + " added. " + adult1.getName() + " and " + adult2.getName() + " are now parents of " + name + ".");

			}else
			{
				throw new NoParentException("They are not married so can't adapt a child");


			}

		}else{
			throw new NoParentException("You must be a couple to have a child");


		}
	}


	/**
	 * to remove existing "partner" relationship and add a new "partner" relationship with someone else
	 * @param adult
	 * @return
	 */
	private String breakUp(Adult adult){
		System.out.println(adult.getName() + " is currently partner of " + adult.getPartner().getName() + "\n" +
				"Do you wish to change partner? (y/n)");
		String choice = yesNoValid();
		if(choice.equals("n")){
			System.out.println("! Fail to Add !\n" +
					"! User Choose Not to Change Current Partner !");
		}else{
			adult.removeRelatedPartner();
		}
		return choice;
	}



	/**
	 * to modify a person's relationship with another person: change from "partner" to "friend" or "friend" to partner
	 * @param person
	 */

	private void modifyRelationship(Person person) {
		if(person.getRelationship("").size() == 0){ // cannot modify relationship if there's no existing relationship
			System.out.println("No other related people to modify, add a relationship first!");
		}else {
			if (person instanceof Adult) {
				Adult adult = (Adult) person;
				if (adult.getPartner() != null) {
					modifyHasPartnerRelation(adult);
				} else {
					modifySingleRelation(adult);
				}
			} else {
				System.out.println("Fail to modify!");
				System.out.println("Reason: Dependent can only be friend with another dependent.");
			}
		}
	}

	/**
	 * a single adult(has no partner) can have "friend" relationship with anyone
	 * a single adult cannot have "partner" relationship with someone who has a partner
	 * @param adult
	 */

	private void modifySingleRelation(Adult adult){
		System.out.println("!NOTE: Cannot Add Partner Relationship With Other Person Who Has a Partner!");
		System.out.println("Input another person's name to modify existing relationship:");
		printModifiedRelation(adult);
		String anotherName = sc.nextLine();
		Person anotherPerson = findPerson(anotherName);
		if (anotherPerson != null && adult.withRelationship(anotherPerson)){
			if (anotherPerson instanceof Adult) {
				String existingRelation = adult.getRelationship("").get(adult.relatedIndex(anotherPerson)).getType();
				System.out.println(adult.getName() + " is currently " + existingRelation + " of " + anotherName);
				System.out.println("Specify new relationship: ");
				String newRelation = partnerOrFriend();
				if(newRelation.equals("partner") && ((Adult) anotherPerson).getPartner() != null){
					System.out.println("Fail to modify relationship.");
					System.out.println("Reason: " + anotherPerson.getName() + " is already partner of " +
							((Adult) anotherPerson).getPartner().getName());
				}else if(newRelation.equals("partner") && ((Adult) anotherPerson).getPartner() == null){
					adult.getRelationship("").get(adult.relatedIndex(anotherPerson)).setType("partner");
					adult.setPartner((Adult)anotherPerson);
					anotherPerson.getRelationship("").get(anotherPerson.relatedIndex(adult)).setType("partner");
					((Adult) anotherPerson).setPartner(adult);
					System.out.println("Relationship modified successfully");
					System.out.println(adult.getName() + " is now " + newRelation + " of " + anotherName);
				}else{
					adult.getRelationship("").get(adult.relatedIndex(anotherPerson)).setType(newRelation);
					anotherPerson.getRelationship("").get(anotherPerson.relatedIndex(adult)).setType(newRelation);
					System.out.println("Relationship modified successfully");
					System.out.println(adult.getName() + " is now " + newRelation + " of " + anotherName);
				}
			}else{
				System.out.println("Fail to modify relationship.");
				System.out.println("Reason: " + anotherName + " is a child/dependent");
			}
		}else{
			System.out.println("Cannot find " + anotherName + " or " + anotherName + " not in " + adult.getName() +
					"'s relationship list, please try again.");
		}
	}

	/**
	 * a has-partner adult can have "friend" relationship and cannot have any "partner" relationship with anyone else
	 * a pair of couple can be modified to "friend" only when their dependent was deleted.
	 * @param adult
	 */
	private void modifyHasPartnerRelation(Adult adult) {
		System.out.println("!NOTE: " + adult.getName() + " is partner of " + adult.getPartner().getName() + "!");
		System.out.println("!Cannot add partner relationship with other person!");
		System.out.println("Input another person's name to modify existing relationship:");
		printModifiedRelation(adult);
		String anotherName = sc.nextLine();
		Person anotherPerson = findPerson(anotherName);
		if (anotherPerson != null && adult.withRelationship(anotherPerson)) {
			if (anotherPerson instanceof Adult) {
				String existingRelation = adult.getRelationship("").get(adult.relatedIndex(anotherPerson)).getType();
				System.out.println(adult.getName() + " is currently " + existingRelation + " of " + anotherName);
				System.out.println("Specify new relationship: ");
				String newRelation = partnerOrFriend();
				if (newRelation.equals("partner")) {
					System.out.println("Fail to modify relationship.");
					System.out.println("Reason: " + adult.getName() + " is already partner of " + adult.getPartner().getName());
				} else if(adult.getPartner().equals(anotherPerson)){
					if(adult.getHasDependent()) {
						System.out.println("Fail to modify relationship.");
						System.out.println("Reason: modify partner relationship with " + anotherName + " will affect " +
								adult.getDependent().getName());
						System.out.println("Try to delete " + adult.getName() + "'s dependent first!");
					}else{
						adult.getRelationship("").get(adult.relatedIndex(anotherPerson)).setType(newRelation);
						adult.setPartner(null);
						anotherPerson.getRelationship("").get(anotherPerson.relatedIndex(adult)).setType(newRelation);
						((Adult) anotherPerson).setPartner(null);
						System.out.println("Relationship modified successfully");
						System.out.println(adult.getName() + " is now " + newRelation + " of " + anotherName);
					}
				} else{
					if (newRelation.equals(existingRelation)) {
						System.out.println("Fail to modify relationship.");
						System.out.println("Reason: " + adult.getName() + " is already " + existingRelation + " of " + anotherName);
					} else {
						adult.getRelationship("").get(adult.relatedIndex(anotherPerson)).setType(newRelation);
						anotherPerson.getRelationship("").get(anotherPerson.relatedIndex(adult)).setType(newRelation);
						System.out.println("Relationship modified successfully");
						System.out.println(adult.getName() + " is now " + newRelation + " of " + anotherName);
					}
				}
			} else {
				System.out.println("Fail to modify relationship.");
				System.out.println("Reason: " + anotherName + " is a child/dependent");
			}
		} else {
			System.out.println("Cannot find " + anotherName + " or " + anotherName + " not in " + adult.getName() +
					"'s relationship list, please try again.");
		}
	}

	//validation when user inputting relationship type(friend/partner)
	private String partnerOrFriend(){
		String s;
		do{
			s = sc.next();
			if(!s.equals("partner") && !s.equals("friend")){
				System.out.println("! Wrong Relationship,  An Adult Can Only Be Partner or Friend With Another Adult !");
				System.out.println("! Input 'partner' Or 'friend' !");
			}
		}while(!s.equals("partner") && !s.equals("friend"));
		return s;
	}

	// to print out currently available parents who have no dependent in community
	private void listAvailParent(){
		int counter = 0;
		for(int i = 0; i < personList.size(); i++){
			if(personList.get(i) instanceof Adult && !((Adult)personList.get(i)).getHasDependent()){
				counter++;
				System.out.println(counter + ":   " + personList.get(i).getName());
			}
		}
	}

	// to return number of available parents who have no dependent in community
	private int getAvailParent(){
		int counter = 0;
		for(int i = 0; i < personList.size(); i++){
			if(personList.get(i) instanceof Adult && !((Adult)personList.get(i)).getHasDependent()){
				counter++;
			}
		}
		return counter;
	}

	// add an Adult object by calling class Adult constructor
	private Person addToAdult(String name, int age, String gender, String status, String image, String state){
		return new Adult(name, image, gender, status, age, state);
	}

	// to valid user inputting age



	private Person findPerson(String name) {
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getName().equals(name.trim())) {
				return personList.get(i);
			}
		}
		return null;
	}

	/**
	 * to valid user inputting "y" or "n"
	 */	
	private String yesNoValid(){
		String s;
		do{
			s = sc.next();
			if(!s.equals("y") && !s.equals("n")){
				System.out.println("Invalid input. Input 'y' for yes or 'n' for no");
			}
		}while(!s.equals("y") && !s.equals("n"));
		return s;
	}

	/**
	 * to print out a people that has relationship with a person to be modified
	 */	
	
	private void printModifiedRelation(Adult adult){
		System.out.println("----------------------------------------");
		for(int i = 0; i < adult.getRelationship("").size(); i++){
			if((!adult.getRelationship("").get(i).getType().equals("dependent"))){
				System.out.println("Name:  " + adult.getRelationship("").get(i).getPerson().getName() +
						"      |      Type: " + adult.getRelationship("").get(i).getType());
			}
		}
		System.out.println("----------------------------------------");
	}
	/**
	 * to check if two people have mutual friends - bonus question
	 */	
	private boolean ifTwoPeopleConnected(Person p1, Person p2)
	{
		for (Relationship re : p1.getRelationship(""))
		{
			String name = re.getPerson().getName();

			for (Relationship re2 : p2.getRelationship(""))
			{	
				String name2 = re2.getPerson().getName();
				if(name.equalsIgnoreCase(name2)) {
					System.out.println("Found mutual friends");
					return true;
				} 

			}
		} 
		System.out.println("They have no mutual friends or related relationships");
		return false;
	}
}



