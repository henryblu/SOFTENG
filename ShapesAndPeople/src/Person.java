public class Person implements Sortable<Person>{
    private String firstName;
    private String surname;

    public Person(String firstName, String surname){
        this.firstName = firstName;
        this.surname = surname;
    }

    public boolean isBigger(Person first, Person second) {
        int cmp = first.getSurname().compareTo(second.getSurname());
        if (cmp == 0) {
            cmp = first.getFirstName().compareTo(second.getFirstName());
        }
        return cmp > 0;
    }
    public String getFirstName(){ return this.firstName;}
    public String getSurname(){return this.surname;}
    public String getFullName(){return (this.firstName + " " + this.surname);}
}
