package com.skillnext2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class StudentDAO {

    public void insertStudent(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(student);

        tx.commit();
        session.close();
    }

    public void updateStudent(int id, String name, int sem, String dept) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student s = session.get(Student.class, id);
        if (s != null) {
            s.setName(name);
            s.setSem(sem);
            s.setDepartment(dept);
            session.update(s);
        }

        tx.commit();
        session.close();
    }

    public void deleteStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student s = session.get(Student.class, id);
        if (s != null) {
            session.delete(s);
        }

        tx.commit();
        session.close();
    }

    public List<Student> getAllStudents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> list =
                session.createQuery("from Student", Student.class).list();
        session.close();
        return list;
    }
}
