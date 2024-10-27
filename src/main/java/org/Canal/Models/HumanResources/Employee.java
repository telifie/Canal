package org.Canal.Models.HumanResources;

public class Employee {

    private String id;
    private String org; //Org ID
    private String location; //Location ID if any
    private String name; //EMployee full name, for now
    private String supervisor; //Employee ID of Supervisor
    private String startDate; //Employee employment date
    private String endDate; //Employee termination date
    private String createDate;

    public Employee(String id, String org, String location, String name) {
        this.id = id;
        this.org = org;
        this.location = location;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}