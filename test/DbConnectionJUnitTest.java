/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Random;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
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
public class DbConnectionJUnitTest {
    private SessionFactory sessionFctry;
    
    @Before
    public void setUp() {
        sessionFctry = new Configuration().configure().buildSessionFactory();
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
            System.out.println(e);
            e.printStackTrace();
        } finally {
            
            session.close();
        }
        
    }
    
    @Test(expected=MappingException.class)
    public void TestSaveException01() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;

        tx = session.beginTransaction();

        String badinput = new String("bad input");
        session.save(badinput);

        tx.commit();

        session.close();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void TestSaveException02() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;

        tx = session.beginTransaction();

        String badinput = null;
        session.save(badinput);

        tx.commit();

        session.close();
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
    
    @Test(expected=QuerySyntaxException.class)
    public void TestReadException01() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;

        tx = session.beginTransaction();

        List students = session.createQuery("FROM nonexistingtable").list();

        tx.commit();
        session.close();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void TestReadException02() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;

        tx = session.beginTransaction();

        List students = session.createQuery("FROMasdasd Students").list();

        tx.commit();
        session.close();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void TestReadException03() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;

        tx = session.beginTransaction();

        List students = session.createQuery("23").list();

        tx.commit();
        session.close();
    }
    
    @Test
    public void updateStudentInDB() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        String randomAdress = "Wałbrszyska 34 m " + ((new Random()).nextInt(100) + 1);
        
        try{
            tx = session.beginTransaction();
            
            
            Student pierwszy = (Student) session.get(Student.class, 2);
            
            pierwszy.setAdress(randomAdress);
            
            session.update(pierwszy);
            
            tx.commit();
        } catch (Exception e) {
        System.out.println(e);
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
            
            Student pierwszy = (Student) session.get(Student.class, 2);
            
            adresPierwszeoPoAktualizacji = pierwszy.getAdress();
            
            tx.commit();
        } catch (Exception e) {
        System.out.println(e);
            if( tx != null ) tx.rollback();
            e.printStackTrace();
        } finally {
            
            session.close();
        }
        System.out.println(randomAdress);
        assertEquals(randomAdress, adresPierwszeoPoAktualizacji);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void TestupdateException01() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        String randomAdress = "Wałbrszyska 34 m " + ((new Random()).nextInt(100) + 1);
        
        tx = session.beginTransaction();


        Student pierwszy = (Student) session.get(Student.class, 2);

        pierwszy.setAdress(randomAdress);

        session.update(null);

        tx.commit();
        session.close();
        
        //Nowa sesja by sprawdzić czy adres danego studenta został zaktualizowany
        session = sessionFctry.openSession();
        tx = null;
        String adresPierwszeoPoAktualizacji = null;
        
        tx = session.beginTransaction();

        pierwszy = (Student) session.get(Student.class, 2);

        adresPierwszeoPoAktualizacji = pierwszy.getAdress();

        tx.commit();
        session.close();
        System.out.println(randomAdress);
        assertEquals(randomAdress, adresPierwszeoPoAktualizacji);
    }
    
    @Test(expected=StaleStateException.class)
    public void TestupdateException02() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        String randomAdress = "Wałbrszyska 34 m " + ((new Random()).nextInt(100) + 1);
        
        tx = session.beginTransaction();


        Student pierwszy = new Student("Andrzej", "Wajda", "Mietczyńska 34 00-242 Krk");

        session.update(pierwszy);

        tx.commit();
        session.close();
        
        //Nowa sesja by sprawdzić czy adres danego studenta został zaktualizowany
        session = sessionFctry.openSession();
        tx = null;
        String adresPierwszeoPoAktualizacji = null;
        
        tx = session.beginTransaction();

        pierwszy = (Student) session.get(Student.class, 2);

        adresPierwszeoPoAktualizacji = pierwszy.getAdress();

        tx.commit();
        session.close();
        System.out.println(randomAdress);
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
        
        Student poszukiwany = (Student) session.get(Student.class, idTemporaryStudent); //to powinno wyrzucić null
        
        assertNull(poszukiwany);
        
        tx.commit();
        session.close();
        
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void TestDeleteException01() {
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
        
        Student usuwany = (Student) session.get(Student.class, idTemporaryStudent);
        session.delete(null);

        tx.commit();

        session.close();
       
        
        
        /******************************/
        /*spróbuj pobrać tego studenta*/
        /******************************/
        session = sessionFctry.openSession();
        tx = session.beginTransaction();
        
        Student poszukiwany = (Student) session.get(Student.class, idTemporaryStudent); //to powinno wyrzucić null
        
        assertNull(poszukiwany);
        
        tx.commit();
        session.close();
        
    }
    @Test(expected=MappingException.class)
    public void TestDeleteException02() {
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
        
        String usuwany = new String("asd");
        session.delete(usuwany);

        tx.commit();

        session.close();
       
        
        
        /******************************/
        /*spróbuj pobrać tego studenta*/
        /******************************/
        session = sessionFctry.openSession();
        tx = session.beginTransaction();
        
        Student poszukiwany = (Student) session.get(Student.class, idTemporaryStudent); //to powinno wyrzucić null
        
        assertNull(poszukiwany);
        
        tx.commit();
        session.close();
        
    }
    
}
