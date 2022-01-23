package users;

import java.sql.Date;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int salary;
    private Date dateOfBirth;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id: " + id + ", firsName : " + firstName + ", lastName : " + lastName + ", salary : " + salary + ", dateOfBirth : " + dateOfBirth + "}";
    }
}
