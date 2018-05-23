package main;


public class Relationship {

	/**
	 * instance variables that indicates the type of a relationship
	 * and the person who has the relationship with
	 */
    private String type;
    private Person person;

    //constructor to initialize a 1 to 1 relationship
    public Relationship(String type, Person person) {
        this.type = type;
        this.person = person;
    }

    /**
     * to return a type of this relationship
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * to return a person 
     * @return
     */
    public Person getPerson() {
        return person;
    }

    /**
     * setters to change the value of type of a relationship
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }


}
