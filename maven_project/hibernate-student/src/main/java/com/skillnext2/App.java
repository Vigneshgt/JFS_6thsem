/* 
package com.skillnext2;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.skillnext2.Student;
import com.skillnext2.HibernateUtil;

public class App {
    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student emp = new Student(
                "Shiva",
                6,
                "Computer Sci" 
        );


        session.persist(emp);

        tx.commit();
        session.close();

        System.out.println("student details inserted successfully!");
    }
}
    */



package com.skillnext2;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.skillnext2.Student;
import com.skillnext2.HibernateUtil;


public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. Insert Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Students");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Semester: ");
                    int sem = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();

                    Student s = new Student(name, sem, dept);
                    dao.insertStudent(s);
                    System.out.println("Student inserted successfully!");
                    break;

                case 2:
                    System.out.print("Enter ID to update: ");
                    int uid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New Name: ");
                    name = sc.nextLine();

                    System.out.print("New Semester: ");
                    sem = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New Department: ");
                    dept = sc.nextLine();

                    dao.updateStudent(uid, name, sem, dept);
                    System.out.println("Student updated!");
                    break;

                case 3:
                    System.out.print("Enter ID to delete: ");
                    int did = sc.nextInt();
                    dao.deleteStudent(did);
                    System.out.println("Student deleted!");
                    break;

                case 4:
                    dao.getAllStudents().forEach(st ->
                        System.out.println(
                            st.getId() + " | " +
                            st.getName() + " | " +
                            st.getSem() + " | " +
                            st.getDepartment()
                        )
                    );
                    break;

                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}


 
