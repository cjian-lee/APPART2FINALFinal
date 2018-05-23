
package main;

import java.util.ArrayList;



public abstract class Person {

	/**
	 * instance variables for building up profile
	 */
    private String name;
    private int age;
    private String gender;
    private String status;
    private String image;
    private String state;
    protected ArrayList<Relationship> relationship = new ArrayList<>(); //to hold "this" person's relationships with other people

    /**
     * constructor for initialize an Adult or Dependent object
     * @param name person's name
     * @param image		can be switched on/off
     * @param status    can be optional
     * @param gender  person's gender(any type of gender)
     * @param age  person's age
     * @param state TODO
     */
    public Person(String name, String image, String status, String gender, int age, String state) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.image = image;
        this.state = state;
    }
    public String getPersonType() {
    	
    	if(age>0 && age<2) return "Young Child";
    	else if (age>=3 && age <=16) return "Child";
    	else return "Adult";
    	
    }


    /**
     * getters for instance variables
     * @return
     */
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }
    
    public String getState() {
    	return state;
    }

    public ArrayList<Relationship> getRelationship(String type) {
    	ArrayList<Relationship>  listToReturn = new ArrayList<>();
    	
    	for (Relationship rel : relationship) {
    		 
    		if(type.equals("")){
    			
    			 listToReturn.add(rel);
    		}
    		else if(rel.getType().equalsIgnoreCase(type)){
    			 
    			 listToReturn.add(rel);
    		}
		}
        return listToReturn;
    }

    public String getImage(){
        return image;
    }



    /**
     * setters to change value of instance variables
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setImage(String image){
        this.image = image;
    }
    
    public void setState(String state) {
    	this.state = state;
    }


    /**
     * to remove "this" person and type of relationship from other people's relationship 
     * lists 
     */
    public void removeRelated(){
        for(int i = 0; i < getRelationship("").size(); i++){
            int removedIndex = getRelationship("").get(i).getPerson().relatedIndex(this);
            if(relationship.get(i).getType().equals("partner")){
                ((Adult) relationship.get(i).getPerson()).setPartner(null);
            }
            getRelationship("").get(i).getPerson().getRelationship("").remove(removedIndex);
        }
    }
    public void AddRelationship(Relationship newRelationShip)
    {
    	relationship.add(newRelationShip);
    }
    /**
     * check if a person has a relationship with "this" person
     * @param person
     * @return true if so
     * 		   false if not
     */
    public boolean withRelationship(Person person){
        for(int i = 0; i < relationship.size(); i++){
            if(relationship.get(i).getPerson().equals(person)) {
                return true;
            }
        }return false;
    }

    /**
     * to get the index of another person within relationship list 
     * @param person
     * @return
     */
    public int relatedIndex(Person person){
        for (int i = 0; i < relationship.size(); i++){
            if(relationship.get(i).getPerson().equals(person)){
                return i;
            }
        }return -1;
    }

/**
 * to check if "this" person has any relationship
 * @return
 */
    public boolean hasRelationship(){
        if(relationship.size() > 0){
            return true;
        }return false;
    }

    /**
     * to print out parents and dependents
     */
    public void printFamily() {
        for (int i = 0; i < relationship.size(); i++) {
            if(relationship.get(i).getType().equals("parent") || relationship.get(i).getType().equals("dependent")){
                System.out.println(getName() + " is " + relationship.get(i).getType() + " of " + relationship.get(i).getPerson().getName());
            }

        }
    }

    /**
     * print out other relationship
     */
    public void printRelation(){
      
    	for (Relationship relaObj : relationship) 
    		System.out.println("Relation Type :"+ relaObj.getType() + " with  " + relaObj.getPerson().getName());
    }
    public String detailProfile(){
    	
    	String personDetails, relationshipDetails="";
    	personDetails= "<html><br>===================================<br>" +
                "Print Profile of " + getName() + "<br>" +
                "-----------------------------------<br>" +
                "Name   -> " + getName() + "<br>" +
                "Age    -> " + getAge() + "<br>" +
                "Gender -> " + getGender() + "<br>" +
                "Status -> " + getStatus() + "<br>" +
                "Image  -> " + getImage() + "<br>" +
                "State  -> " + getState() + "<br>" + 
                "===================================<br>";
		                
		for (Relationship relaObj : relationship) 
			relationshipDetails = relationshipDetails+ "<br>Relation Type :"+ relaObj.getType()  + " with "+ relaObj.getPerson().getName();
		 
        return personDetails+ "<br> his/her relationship details are as below <br>"+relationshipDetails +"</html>";
    }
    //abstract class to be overridden in subclasses
    public void printProfile(){
    	
    	
    	   System.out.println("\n===================================\n" +
                   "Print Profile of " + getName() + "\n" +
                   "-----------------------------------\n" +
                   "Name   -> " + getName() + "\n" +
                   "Age    -> " + getAge() + "\n" +
                   "Gender -> " + getGender() + "\n" +
                   "Status -> " + getStatus() + "\n" +
                   "Image  -> " + getImage() + "\n" +
                   "State  -> " + getState() + "\n" +
                   "===================================\n" );
    }




}

