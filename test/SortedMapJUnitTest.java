/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Random;
import java.util.TreeMap;
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
public class SortedMapJUnitTest {
    private SessionFactory sessionFctry;
   
    @Before
    public void setUp() {
        sessionFctry = new Configuration().configure().buildSessionFactory();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void createNewCar() {
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        Integer returned_id = null;
        try {
            tx = session.beginTransaction();

            TreeMap carAdditions = new TreeMap();

            carAdditions.put("Skórzana kierownica", new CarAddition("MVC239Kierownica"));
            carAdditions.put("Skórzane siedzenia", new CarAddition("Cw3GDSSiedzenia"));
            carAdditions.put("Podgrzewane lusterka", new CarAddition("OPEL23432 lustra"));
            carAdditions.put("Chromowane felgi SkullCar 321", new CarAddition("SKULLCAR321"));

            Car newCar = new Car("Opel", "Corsa Mk2", 1998, 3700, "Diesel 2.0", carAdditions);

            returned_id = (Integer) session.save(newCar);
            tx.commit();
        } catch(Exception e) {
             e.printStackTrace();
        } 
        session.close();
        
       
        //Sprawdz czy istnieje w bazie
        session = sessionFctry.openSession();
        tx = session.beginTransaction();
        
        Car getCar = (Car) session.get(Car.class, returned_id);
        tx.commit();
        
        assertNotNull(getCar); //gdy nie pusty to w bazie istnieje pojazd o tym id czyli ok
        session.close();
        
    }
    
    @Test
    public void readCar() {
        /*zakładamy że w bazie isntnieje samochód o id 1 */
        final Integer carIDToTest = 5;
        
        Session session = sessionFctry.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Car car = 
                    (Car)session.get(Car.class, carIDToTest); 
         
         assertNotNull(car);
          
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
    }
    
     @Test
    public void updateCar() {
        /*zakładamy że w bazie isntnieje samochód o id 1 */
      final Integer carIDToTest = 5;
      
      int random_year_production = (new Random()).nextInt(100)+1900;
      Session session = sessionFctry.openSession();
      Transaction tx = null;
      try{ 
         tx = session.beginTransaction();
         Car car = 
                    (Car)session.get(Car.class, carIDToTest); 
         
         assertNotNull(car); //do aktualizacji musi istnieć w bazie obiekt o id 1 
         
         car.setProduction_year(random_year_production);
         session.update(car);
         
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
      
      /* sprawdzenie czy obiekt się zaktualizował */
      session = sessionFctry.openSession();
      int readed_production_year = 0;
      try{ 
         tx = session.beginTransaction();
         Car car = 
                    (Car)session.get(Car.class, carIDToTest); 
         
         assertNotNull(car); //do aktualizacji musi istnieć w bazie obiekt o id 1 
         
         readed_production_year = car.getProduction_year();
         
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
      
         assertEquals(random_year_production, readed_production_year);
    }
    
    @Test
    public void deleteCar() {
        //tworzymy obiekt
        Session session = sessionFctry.openSession();
        Transaction tx = null;
        Integer returned_id = null;
        
        try{ 
           tx = session.beginTransaction();
           
            TreeMap carAdditions = new TreeMap();

            carAdditions.put("Skórzana kierownica", new CarAddition("MVC239Kierownica"));
            carAdditions.put("Skórzane siedzenia", new CarAddition("Cw3GDSSiedzenia"));
            carAdditions.put("Podgrzewane lusterka", new CarAddition("OPEL23432 lustra"));
            carAdditions.put("Chromowane felgi SkullCar 321", new CarAddition("SKULLCAR321"));

            Car newCar = new Car("Opel", "Corsa Mk2", 1998, 3700, "Diesel 2.0", carAdditions);

            returned_id = (Integer) session.save(newCar);
            
            tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        
        //usuwamy obiekt
        session = sessionFctry.openSession();
          try{ 
           tx = session.beginTransaction();
           
            Car car = (Car) session.get(Car.class, returned_id);
             assertNotNull(car); //sprawdzamy czy nie pusty (bo nie powinien być pusty!)
            session.delete(car);
            
            tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        
        //sprawdzamy czy został usunięty z bazy
        session = sessionFctry.openSession();
          try{ 
           tx = session.beginTransaction();
           
            Car car = (Car) session.get(Car.class, returned_id);
            assertNull(car); //powinno być puste ponieważ usuneliśmy wcześniej ten obiekt
            
            tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        
    }
}
