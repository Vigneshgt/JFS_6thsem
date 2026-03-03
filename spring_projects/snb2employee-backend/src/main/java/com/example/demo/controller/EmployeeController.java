package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/snb2employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repo;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee emp) {
        return repo.save(emp);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee emp) {
        Employee e = repo.findById(id).orElse(null);
        e.setName(emp.getName());
        e.setEmail(emp.getEmail());
        e.setSalary(emp.getSalary());
        return repo.save(e);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) {
        repo.deleteById(id);
    }
}



















