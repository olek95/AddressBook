package addressbook;

public class Person implements Comparable<Person>{
    private String name, lastName, email;
    public Person(String name, String lastName, String email){
        this.name = name;
        this.lastName = lastName; 
        this.email = email; 
    }
    public int compareTo(Person p){
        if(name.compareTo(p.name) < 0)return -1;
        else if(name.compareTo(p.name) > 0) return 1; 
        else if(name.compareTo(p.name) == 0)
            if(lastName.compareTo(p.lastName) < 0) return -1;
            else if(lastName.compareTo(p.lastName) > 0) return 1; 
            else if(lastName.compareTo(p.lastName) == 0)
                if(email.compareTo(p.email) < 0) return -1; 
                else if(email.compareTo(p.email) > 0) return 1;
        return 0;
    }
    public String getName(){
        return name;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
}
