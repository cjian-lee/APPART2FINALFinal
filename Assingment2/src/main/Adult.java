package main;



public class Adult extends Person{
    private boolean hasDependent = false;

    /**
     * to store an adult's partner and his dependent
     */
   // private Adult partner;
    private Child dependent;


    public Adult(String name, String image, String status, String gender, int age, String state) {
        super(name, image, status, gender, age, state);
    }

    /**
     * getters to return instance variables
     * @return
     */
    public boolean getHasDependent() {
        return hasDependent;
    }

    public Adult getPartner() {
    	for (Relationship eachRe : relationship) {
			if(eachRe.getType().equalsIgnoreCase("Partner")){
				   return (Adult)eachRe.getPerson();			
				
			}
		}
    	return null;
     
    }

    public Child getDependent() {
        return dependent;
    }

    /**
     * setters to change the value of instance variables
     * @param hasDependent
     */
    public void setHasDependent(boolean hasDependent) {
        this.hasDependent = hasDependent;
    }

    public void setPartner(Adult partner) {
    	AddRelationship(new Relationship("partner", partner));
    	//addRelationship("partner",partner);
        //this.partner = partner;
    }

    public void setDependent(Child dependent) {
        this.dependent = dependent;
    }


    /**
     * to add a dependent and a partner at the same time
     * @param adult
     * @param dependent
     */
    /*
    public void addDependent(Adult adult, Child dependent){
        if(withRelationship(adult)){
            int findIndex = relatedIndex(adult);
            getRelationship().get(findIndex).setType("partner");
        }else{
            getRelationship().add(new Relationship("partner", adult));
        }
        getRelationship().add(new Relationship("parent", dependent));
        dependent.getRelationship().add(new Relationship("dependent", this));
        this.hasDependent = true;
       // this.partner = adult;
        this.dependent = dependent;
    }*/

    /**
     * to add a friend with another adult
     * @param type
     * @param adult
     */
    public void addRelationship(String type, Adult adult){
        if(withRelationship(adult)){
            System.out.println(getName() + " is already in relationship with " + adult.getName());
        }else{
        	AddRelationship(new Relationship(type, adult));
            //getRelationship("").add(new Relationship(type, adult));
            System.out.println(this.getName() + " is now " + type + " of " + adult.getName());
        }
    }
    
    /**
     * to remove a "partner" relationship with another person 
     */
    public void removeRelatedPartner(){
        for(int i = 0; i < getRelationship("").size(); i++){
            int removedIndex = getRelationship("").get(i).getPerson().relatedIndex(this);
            if(getRelationship("").get(i).getType().equals("partner")){
                ((Adult) getRelationship("").get(i).getPerson()).setPartner(null);
                this.setPartner(null);
                getRelationship("").get(i).getPerson().getRelationship("").remove(removedIndex);
                getRelationship("").remove(i);
            }
        }
    }

    @Override
    public void printProfile() {
    	super.printProfile();
      
    	if(getPartner()==null){
    		
        }else{
            System.out.println("Partner Name is  "+ this.getPartner().getName());
            
        }
    	
    	if(hasRelationship()){
            printRelation();
        }else{
            System.out.println(getName() + " does not have any relationship");
        }

        }
    }


