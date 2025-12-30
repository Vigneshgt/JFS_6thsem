
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



 
