package com.skillnext2;

import java.sql.*;
import java.util.*;

public class StudentDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/skillnext2_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Mysqlroot"; 

    // Add employee
    public void addStudent(Student st) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        String sql = "INSERT INTO student (name, sem, department) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, st.getName());
        stmt.setInt(2, st.getSem());
        stmt.setString(3, st.getDepartment());
        stmt.executeUpdate();
        conn.close();
    }

    // Fetch all employees
    public List<Student> getAllStudents() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM student");

        List<Student> list = new ArrayList<>();
        while (rs.next()) {
            Student s = new Student();
            s.setId(rs.getInt("id"));
            s.setName(rs.getString("name"));
            s.setSem(rs.getInt("sem"));
            s.setDepartment(rs.getString("department"));
            list.add(s);
        }
        conn.close();
        return list;
    }

    // Delete employee
    public void deleteStudent(int id) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        String sql = "DELETE FROM student WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        conn.close();
    }

    // Update employee
    public void updateStudent(Student st) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        String sql = "UPDATE student SET name=?, sem=?, department=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, st.getName());
        stmt.setInt(2, st.getSem());
        stmt.setString(3, st.getDepartment());
        stmt.setInt(4, st.getId());
        stmt.executeUpdate();
        conn.close();
    }
}
