package no.hvl.dat107;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main2 {

	public static void main(String[] args) {
		
		Person p = finnPersonMedId(1001);
		System.out.println(p);

	}
	
	public static Person finnPersonMedId(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("personPersistenceUnit");
		EntityManager em = emf.createEntityManager();
		
		Person p = null;
		
		try {
			p = em.find(Person.class, id);
		} finally {
			em.close();
			emf.close();
		}
		return p;
	}

}
