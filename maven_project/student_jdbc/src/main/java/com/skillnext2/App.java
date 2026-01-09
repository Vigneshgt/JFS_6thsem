package com.skillnext2;

import java.util.*;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\n STUDENT MANAGEMENT MENU ");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            try {
                switch (choice) {

                    case 1: // Add
                        System.out.print("Enter Name: ");
                        sc.nextLine(); // consume newline
                        String name = sc.nextLine();

                        System.out.print("Enter Semester: ");
                        int sem = sc.nextInt();

                        System.out.print("Enter Department: ");
                        sc.nextLine();
                        String dept = sc.nextLine();

                        Student s = new Student(name, sem, dept);
                        dao.addStudent(s);
                        System.out.println("Student added successfully!");
                        break;

                    case 2: // Display
                        List<Student> students = dao.getAllStudents();
                        if (students.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            for (Student st : students) {
                                System.out.println(st);
                            }
                        }
                        break;

                    case 3: // Update
                        System.out.print("Enter Student ID to update: ");
                        int uid = sc.nextInt();

                        System.out.print("Enter New Name: ");
                        sc.nextLine();
                        String uname = sc.nextLine();

                        System.out.print("Enter New Semester: ");
                        int usem = sc.nextInt();

                        System.out.print("Enter New Department: ");
                        sc.nextLine();
                        String udept = sc.nextLine();

                        Student us = new Student(uname, usem, udept);
                        us.setId(uid);
                        dao.updateStudent(us);
                        System.out.println("Student updated successfully!");
                        break;

                    case 4: // Delete
                        System.out.print("Enter Student ID to delete: ");
                        int did = sc.nextInt();

                        dao.deleteStudent(did);
                        System.out.println("Student deleted successfully!");
                        break;

                    case 5: // Exit
                        System.out.println("Exiting program. Bye!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}









/* 
package com.skillnext2;
import java.util.*;

public class App 
{
    public static void main( String[] args )
    {
       try {
            StudentDAO dao = new StudentDAO();

            // Add employee
            Student s1 = new Student("John Doe", 2, "IT");
            dao.addStudent(s1);
            System.out.println("Student Added Successfully!");

            // Fetch employees
            List<Student> students = dao.getAllStudents();
            for (Student s : students) {
                System.out.println(s);
            }

        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
    */

