package com.employee;

public class Employee {

    private int id;
    private String name;
    private int age;
    private String department;
    private float experience;
    private String state;
    private String country;


    public Employee() {
    }


    public Employee(
            int id,
            String name,
            int age,
            String department,
            float experience,
            String state,
            String country) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.experience = experience;
        this.state = state;
        this.country = country;
    }


    public Employee(
            String name,
            int age,
            String department,
            float experience,
            String state,
            String country) {

        this.name = name;
        this.age = age;
        this.department = department;
        this.experience = experience;
        this.state = state;
        this.country = country;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}