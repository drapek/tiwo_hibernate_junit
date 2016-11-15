/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Random;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author drapek
 */
public class StudentJUnitTest {
    private SessionFactory sessionFctry;
    
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        sessionFctry = new Configuration().configure().buildSessionFactory();
    }
    
    @After
    public void tearDown() {
    }

    
    /* These test are made on Student class */
    @Test 
    public void saveStudentToExistingTableTest() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        Integer id = null;
        
        try{
            tx = session.beginTransaction();
            
            Student new_student = new Student("Andrzej", "Wajda", "Mietczyńska 34 00-242 Krk");
            id = (Integer) session.save(new_student);
            
            tx.commit();
        } catch (Exception e) {
            if( tx != null ) tx.rollback();
            e.printStackTrace();
        } finally {
            
            session.close();
        }
        
    }
    
    /* These test are made on Student class */
    @Test 
    public void readStudentsFromDB() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        
        try{
            tx = session.beginTransaction();
            
            List students = session.createQuery("FROM Student").list();
            
            tx.commit();
        } catch (Exception e) {
            if( tx != null ) tx.rollback();
            e.printStackTrace();
        } finally {
            
            session.close();
        }
        
    }
    
    @Test
    public void updateStudentInDB() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        String randomAdress = "Wałbrszyska 34 m " + ((new Random()).nextInt(100) + 1);
        
        try{
            tx = session.beginTransaction();
            
            
            Student pierwszy = (Student) session.get(Student.class, 1);
            
            pierwszy.setAdress(randomAdress);
            
            session.update(pierwszy);
            
            tx.commit();
        } catch (Exception e) {
            if( tx != null ) tx.rollback();
            e.printStackTrace();
        } finally {
            
            session.close();
        }
        
        //Nowa sesja by sprawdzić czy adres danego studenta został zaktualizowany
        session = sessionFctry.openSession();
        tx = null;
        String adresPierwszeoPoAktualizacji = null;
        
        try{
            tx = session.beginTransaction();
            
            Student pierwszy = (Student) session.get(Student.class, 1);
            
            adresPierwszeoPoAktualizacji = pierwszy.getAdress();
            
            tx.commit();
        } catch (Exception e) {
            if( tx != null ) tx.rollback();
            e.printStackTrace();
        } finally {
            
            session.close();
        }
        
        assertEquals(randomAdress, adresPierwszeoPoAktualizacji);
    }
    
    @Test
    public void usuwanieStudenta() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        Integer idTemporaryStudent = null;
       
        /***********************/
        /*dodaj studenta do db*/
        /**********************/
        tx = session.beginTransaction();
            
        Student temporaryStudent = new Student("Karol", "Marzyciel", "Malinowa 23"); 
        idTemporaryStudent = (Integer) session.save(temporaryStudent);
            
        tx.commit();
        session.close();

        
        /****************************/
        /*usuń nowododanego studenta*/
        /****************************/
        session = sessionFctry.openSession();
        tx = session.beginTransaction();
        try {
            Student usuwany = (Student) session.get(Student.class, idTemporaryStudent);
            session.delete(usuwany);

            tx.commit();
        } catch (Exception e) {
            if( tx != null ) tx.rollback();
            e.printStackTrace();
        } finally {
            
            session.close();
        }
       
        
        
        /******************************/
        /*spróbuj pobrać tego studenta*/
        /******************************/
        session = sessionFctry.openSession();
        tx = session.beginTransaction();
        
        Student poszukiwany = (Student) session.get(Student.class, idTemporaryStudent); //to powinno wyrzucić wyjątek
        
        assertNull(poszukiwany);
        
        tx.commit();
        session.close();
        
    }
    
    /*** Testy Sorted Maps ******/
    
    
}
