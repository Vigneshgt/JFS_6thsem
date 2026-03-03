package com.example.EmployeeCrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.EmployeeCrud.entity.Employee;
import com.example.EmployeeCrud.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public Employee addEmployee(@RequestBody Employee emp) {
        return service.saveEmployee(emp);
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable int id) {
        return service.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        service.deleteEmployee(id);
        return "Employee Deleted Successfully";
    }
}


























