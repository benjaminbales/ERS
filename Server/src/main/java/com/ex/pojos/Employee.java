package com.ex.pojos;

import org.bson.types.ObjectId;

public class Employee {
    public Employee() {
    }

    public Employee(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    //TODO I want to reference a mongo collection with a list of objectids to filter for the reimbursement requeests belong to this employee
    //alternatively, could just keep a reference in each remimbursement request document to the employee objectId and collectionName.
    //basically give it an owner field.  {owner: {collectionName: Employees, ID: objectId}}
//    private List<ReimbursmentRequest> reimbursmentRequestList;

    private ObjectId id;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", id=" + id +
                '}';
    }
}
