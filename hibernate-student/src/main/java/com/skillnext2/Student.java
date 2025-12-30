package com.skillnext2;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int sem;
    private String department;

    public Student() {}

    public Student(String name, int sem, String department) {
        this.name = name;
        this.sem = sem;
        this.department = department;
    }

    // getters & setters
    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSem() { return sem; }
    public void setSem(int sem) { this.sem = sem; }

    public String getDepartment() { return department; }
    public void setDepartment(String Department) { this.department = department; }
}
