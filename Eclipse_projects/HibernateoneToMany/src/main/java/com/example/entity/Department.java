package com.example.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Department{
	public Department(int id, String name, List<Employee> employees) {
		super();
		this.id = id;
		this.name = name;
		this.employees = employees;
	}
	
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@OneToMany(mappedBy="department", cascade=CascadeType.ALL)
	private List<Employee> employees;
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
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	@Override
	public int hashCode() {
		return Objects.hash(employees, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(employees, other.employees) && id == other.id && Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", employees=" + employees + "]";
	}
	
	
	
}


