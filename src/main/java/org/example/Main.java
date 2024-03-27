package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws  SQLException{

        Configuration configuration = new Configuration().configure();
        try(SessionFactory sessionFactory = configuration.buildSessionFactory()){
            addStudentsToTable(sessionFactory);
            System.out.println("===========================================");
            showStudents(sessionFactory);
            System.out.println("===========================================");
            findStudentById(sessionFactory);
            System.out.println("===========================================");
            mergeStudentById(sessionFactory);
            System.out.println("===========================================");
            removeStudentById(sessionFactory);
            System.out.println("===========================================");
            showStudents(sessionFactory);
            System.out.println("===========================================");
            showStudentsElder20Years(sessionFactory);
            System.out.println("===========================================");
        }
    }
    public static List<Student> createStudents(){
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1);
        student1.setFirstName("Giuseppe");
        student1.setSecondName("Meazza");
        student1.setAge(22);
        Student student2 = new Student();
        student2.setId(2);
        student2.setFirstName("Giovanni");
        student2.setSecondName("Di Lorenzo");
        student2.setAge(23);
        Student student3 = new Student();
        student3.setId(3);
        student3.setFirstName("Francesco");
        student3.setSecondName("Totti");
        student3.setAge(20);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        return students;
    }
    public static void addStudentsToTable(SessionFactory sessionFactory){
        List<Student> students = createStudents();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            for (Student st: students) {
                session.persist(st);
            }
            tx.commit();
        }
    }
    public static void findStudentById(SessionFactory sessionFactory){
        try(Session session = sessionFactory.openSession()){
            Student student = session.find(Student.class, 1);
            System.out.println(student);
        }
    }
    public static void mergeStudentById(SessionFactory sessionFactory){
        try(Session session = sessionFactory.openSession()){
            Student student = session.find(Student.class, 1);
            //System.out.println(student);

            Transaction tx = session.beginTransaction();
            student.setAge(25);
            session.merge(student);
            tx.commit();
            System.out.println(student);
        }
    }
    public static void removeStudentById(SessionFactory sessionFactory){
        try(Session session = sessionFactory.openSession()){
            Student student = session.find(Student.class, 2);
            System.out.println("Студент: " + student + " будет удален");
            Transaction tx = session.beginTransaction();
            session.remove(student);
            tx.commit();
        }
    }
    public static void showStudents(SessionFactory sessionFactory){
        try(Session session = sessionFactory.openSession()){
            List<Student> students = session.createQuery("SELECT a FROM Student a", Student.class).getResultList();
            for (Student s: students) {
                System.out.println(s);
            }
        }
    }
    public static void showStudentsElder20Years(SessionFactory sessionFactory){
        try(Session session = sessionFactory.openSession()){
            List<Student> students = session.createQuery("SELECT a FROM Student a WHERE age > 20", Student.class).getResultList();
            for (Student s: students) {
                System.out.println(s);
            }
        }
    }
}