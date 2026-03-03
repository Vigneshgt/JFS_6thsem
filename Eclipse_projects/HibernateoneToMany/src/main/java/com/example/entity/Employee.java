package com.example.entity;
import java.util.Objects;

import jakarta.persistence.*;
@Entity
public class Employee {
	public Employee(int id, String name, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
	}
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne
	@JoinColumn(name="dept_id")
	private Department department;
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
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public int hashCode() {
		return Objects.hash(department, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(department, other.department) && id == other.id && Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + "]";
	}
	
	
	
}
