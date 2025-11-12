package com.app.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.app.config.HibernateUtil;
import com.app.model.Student;

public class Test {

    // Scanner for user input
    Scanner sc = new Scanner(System.in);
    
    // Hibernate SessionFactory (singleton, created from HibernateUtil)
    SessionFactory sf = HibernateUtil.getSessionFactory();

    /**
     * ✅ Method to insert multiple Student records into the database
     */
    public void insertValue() {
        // Open new Hibernate session
        Session ss = sf.openSession();
        // Begin transaction before performing database operations
        Transaction tx = ss.beginTransaction();

        // Create list to temporarily store Student objects
        List<Student> list = new ArrayList<>();

        // Take input for 5 students (loop can be adjusted)
        for (int i = 1; i <= 5; i++) {
            Student s = new Student(); // ✅ Create new object each time
            System.out.println("\nEnter values for Student " + i);

            // Taking user inputs
//            System.out.print("Enter roll no: "); // optional if rollno is auto-generated
//            s.setRollno(sc.nextInt());

            System.out.print("Enter first name: ");
            s.setFname(sc.next());

            System.out.print("Enter last name: ");
            s.setLname(sc.next());

            System.out.print("Enter address: ");
            s.setAddress(sc.next());

            System.out.print("Enter mobile number: ");
            s.setMobno(sc.nextLong());

            // Add object to list for later saving
            list.add(s);
        }

        // Save each Student object to the database
        for (Student student : list) {
            ss.persist(student);
        }

        // Commit transaction to finalize changes
        tx.commit();
        // Close session to free resources
        ss.close();

        System.out.println("\n✅ All student data inserted successfully!");
    }

    /**
     * ✅ Method to update existing student record using roll number
     */
    public void updateValue() {
        Session ss = sf.openSession();
        Transaction tx = ss.beginTransaction();

        System.out.print("Enter roll no to update: ");
        int rn = sc.nextInt();

        // Retrieve student using Hibernate get() method
        Student s = ss.get(Student.class, rn);

        if (s == null) {
            System.out.println("❌ Student not found!");
        } else {
            // Take updated values from user
            System.out.print("Enter first name: ");
            s.setFname(sc.next());

            System.out.print("Enter last name: ");
            s.setLname(sc.next());

            System.out.print("Enter address: ");
            s.setAddress(sc.next());

            System.out.print("Enter mobile number: ");
            s.setMobno(sc.nextLong());

            // Save updated record
            ss.update(s);
            tx.commit();

            System.out.println("✅ Student updated successfully!");
        }

        ss.close();
    }

    /**
     * ✅ Method to delete a student record by roll number
     */
    public void deleteValue() {
        Session ss = sf.openSession();
        Transaction tx = ss.beginTransaction();

        System.out.print("Enter roll no to delete: ");
        int del = sc.nextInt();

        // Retrieve student record using primary key
        Student s = ss.get(Student.class, del);

        // If student exists, delete record
        if (s != null) {
            ss.delete(s);
            tx.commit();
            System.out.println("✅ Student deleted successfully!");
        } else {
            System.out.println("❌ Student not found!");
        }

        ss.close();
    }

    /**
     * ✅ Method to retrieve and display multiple student records
     * Uses Hibernate's `get()` method to fetch data by ID.
     */
    public void retrieveData() {
        Session ss = sf.openSession();

        System.out.print("Enter how many student records to view: ");
        int n = sc.nextInt(); // Number of records to display

        System.out.println("\n===== Student Records =====");
        // Loop to fetch student records by ID
        for (int i = 1; i <= n; i++) {
            Student stu = ss.get(Student.class, i); // ✅ get() returns null if not found

            if (stu != null) {
                // Print student details
                System.out.println("Roll No: " + stu.getRollno());
                System.out.println("Name: " + stu.getFname() + " " + stu.getLname());
                System.out.println("Address: " + stu.getAddress());
                System.out.println("Mobile: " + stu.getMobno());
                System.out.println("----------------------------");
            } else {
                System.out.println("⚠️ No record found for Roll No: " + i);
            }
        }

        ss.close();
    }

    /**
     * ✅ Menu-driven program for managing student records
     */
    public void menu() {
        while (true) {
            System.out.println("****************************************");
            System.out.println("       Student Management System ");
            System.out.println("========================================");
            System.out.println("1. Insert Data");
            System.out.println("2. Delete Data");
            System.out.println("3. Update Data");
            System.out.println("4. Retrieve Data");
            System.out.println("5. Exit");
            System.out.print("Enter Your Choice: ");

            int ch = sc.nextInt();

            // Switch case for handling user choices
            switch (ch) {
                case 1:
                    insertValue();
                    break;

                case 2:
                    deleteValue();
                    break;

                case 3:
                    updateValue();
                    break;

                case 4:
                    retrieveData();
                    break;

                case 5:
                    System.out.println("Exiting... Thank You!");
                    sf.close();  // Close SessionFactory before exit
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Invalid choice! Please enter 1–5.");
            }
        }
    }

    /**
     * ✅ Main method — entry point of the program
     */
    public static void main(String[] args) {
        Test t = new Test();
        t.menu(); // Start the menu loop
    }
}
