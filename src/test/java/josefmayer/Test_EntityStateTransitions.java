package josefmayer;

import josefmayer.Model.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
/**
 * Created by Josef Mayer on 27.05.2017.
 */
public class Test_EntityStateTransitions {

    @Test
    public void makePersistent() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Address ad1 = new Address();
        ad1.setCity("Gent").setCountry("Belgium").setPostcode("3344").setStreet("Damstraat ");
        em.persist(ad1);

        Integer addressId = ad1.getId(); // Has been assigned
        Address address = em.find(Address.class, addressId);
        assertEquals(address.getCity(), "Gent");

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    @Test
    public void retrievePersistent() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

        // persist
        em.getTransaction().begin();

        Address ad1 = new Address();
        ad1.setCity("Gent").setCountry("Belgium").setPostcode("3344").setStreet("Damstraat ");
        em.persist(ad1);

        em.getTransaction().commit();
        em.close();

        Integer address_id = ad1.getId(); // Has been assigned

        // retreive from database, if not in persistence context, update
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Address ad2 = em.find(Address.class, address_id);
        if (ad1 != null){{
            ad2.setPostcode("4567");
        }}

        em.getTransaction().commit();
        em.close();


        // repeatable read
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Address addressA = em.find(Address.class, address_id);
        Address addressB = em.find(Address.class, address_id);   // repeatable read

        assertTrue(addressA == addressB);               // object identity
        assertTrue(addressA.equals(addressB));                    // object equality
        assertTrue(addressA.getId().equals(addressB.getId()));    // database identity


        em.getTransaction().commit();
        em.close();

        emf.close();
   }

   @Test
    public void retrievePersistentReference() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();
        // persist
        em.getTransaction().begin();

        Address ad1 = new Address();
        ad1.setCity("Gent").setCountry("Belgium").setPostcode("3344").setStreet("Damstraat ");
        em.persist(ad1);

        em.getTransaction().commit();
        em.close();

        Integer address_id = ad1.getId(); // Has been assigned


        // retrieve a reference
        em = emf.createEntityManager();
        em.getTransaction().begin();

       // no access of database, creates proxy
        Address ad2 = em.getReference(Address.class, address_id);

        // Utility to detect if working with uninitialised proxy
        PersistenceUnitUtil persistenceUtil = emf.getPersistenceUnitUtil();

        assertFalse(persistenceUtil.isLoaded(ad2));
        em.getTransaction().commit();
        em.close();

        emf.close();
    }

    @Test
    public void makeTransient() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

        // persist
        em.getTransaction().begin();

        Address ad1 = new Address();
        ad1.setCity("Gent").setCountry("Belgium").setPostcode("3344").setStreet("Damstraat ");
        em.persist(ad1);

        em.getTransaction().commit();
        em.close();

        Integer address_id = ad1.getId();

        // remove from db, hold in memory
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Address ad2 = em.find(Address.class, address_id);

        // entity in removed state
        em.remove(ad2);

        // entity is no longer in persistent state
        assertFalse(em.contains(ad2));

        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    @Test
    public void detach(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

        // persist
        em.getTransaction().begin();

        Address ad1 = new Address();
        ad1.setCity("Gent").setCountry("Belgium").setPostcode("3344").setStreet("Damstraat ");
        em.persist(ad1);

        em.getTransaction().commit();
        em.close();

        Integer address_id = ad1.getId();

        em = emf.createEntityManager();
        em.getTransaction().begin();

        Address ad2 = em.find(Address.class, address_id);
        // no longer in persistence context
        em.detach(ad2);

        assertFalse(em.contains(ad2));

        em.getTransaction().commit();


        em.close();
        emf.close();


    }

}
