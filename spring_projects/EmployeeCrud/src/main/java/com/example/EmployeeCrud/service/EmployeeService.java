package com.example.EmployeeCrud.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EmployeeCrud.entity.Employee;
import com.example.EmployeeCrud.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    public Employee saveEmployee(Employee emp) {
        return repo.save(emp);
    }

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public Employee getEmployeeById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteEmployee(int id) {
        repo.deleteById(id);
    }
}

























