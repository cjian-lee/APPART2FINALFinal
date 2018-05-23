package main;

import java.util.ArrayList;

import exceptions.TooYoungException;

public class Child extends Person{
	
	
    public Child(String name, String image, String status, String gender, int age, String state) {
        super(name, image, status, gender, age, state);
    }

    public void addRelationship(Relationship relationshipObj) {
    	
    	relationship.add(relationshipObj);
    }
    
    /**
     * to add a friend with other dependent
     * @param type
     * @param person
     */
    public void addRelationship(String type, Person person) throws TooYoungException{
    	AddRelationship(new Relationship(type, person));
    	System.out.println(this.getName() + " is now friend of " + person.getName());
        
    }

    
    /**
     * to check if the target person is within the age range 
     * @param person
     * @return
     */
    private boolean withinRange(Person person){
        if(person.getAge() > 2 && person.getAge() < 16){
            if(Math.abs(person.getAge()- getAge()) <= 3){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public void printProfile() {
    	super.printProfile();
        if(hasRelationship()){
            printRelation();
            //printFamily();
        }else{
            System.out.println(getName() + " does not have any relationship");
        }
        //System.out.println("===================================");
    }
}