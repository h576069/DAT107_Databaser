package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Main3 {

	public static void main(String[] args) {

		Main3 crud = new Main3();

		System.out.println(crud.retrievePerson(1001));
		System.out.println(crud.retrievePerson(1002));
		System.out.println(crud.retrievePerson(1003));
		System.out.println("---");

		for (Person p : crud.retrieveAllePersoner2()) {
			System.out.println(p);
		}
		System.out.println("---");

		Person per = crud.retrievePerson(1001);
		System.out.println(per);

		per.setNavn("X");
		crud.updatePerson(per);
		per = crud.retrievePerson(1001);
		System.out.println(per);

		per.setNavn("Per Viskeler");
		crud.updatePerson(per);
		per = crud.retrievePerson(1001);
		System.out.println(per);
		System.out.println("---");

		Person mikke = new Person(1004, "Mikke Mus");
		crud.createPerson(mikke);
		mikke = crud.retrievePerson(1004);
		System.out.println(mikke);

		crud.deletePerson(mikke);
		mikke = crud.retrievePerson(1004);
		System.out.println(mikke);
		System.out.println("---");
	}

	// ------------------------------------------------------------------------

	private EntityManagerFactory emf;

	public Main3() {
			emf = Persistence.createEntityManagerFactory("personPersistenceUnit");
		}

	public void createPerson(Person p) { // Oppretter ny rad i databasen

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); // Husk at vi alltid må opprette en transaksjon for å utføre operasjoner

		try {
			tx.begin();
			em.persist(p); // kjerneoperasjonen for å lage ny rad i databasen
			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback(); // Hvis noe går galt må vi utføre en rollback
		} finally {
			em.close();
		}
	}

	public Person retrievePerson(int id) { // Ut fra primærnøkkelen id

		EntityManager em = emf.createEntityManager();

		Person p = null;
		try {
			p = em.find(Person.class, id);
		} finally {
			em.close();
		}

		return p;
	}

	public List<Person> retrieveAllePersoner() {
		
		// Bruker jpql når vi skal hente ut mange

		EntityManager em = emf.createEntityManager();

		List<Person> personer = null;
		try {
			TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
			personer = query.getResultList();
		} finally {
			em.close();
		}
		return personer;
	}

	public List<Person> retrieveAllePersoner2() {
		/* Tester ut NamedQuery */

		EntityManager em = emf.createEntityManager();

		List<Person> personer = null;
		try {
			TypedQuery<Person> query = em.createNamedQuery("hentAllePersoner", Person.class);
			personer = query.getResultList();
		} finally {
			em.close();
		}
		return personer;
	}

	public void updatePerson(Person p) {

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			// Tar person-objektet p, sjekker primærnøkkelen, søker det opp og returnerer tilbake et objekt som er en håndtert versjon av p
			Person q = em.merge(p); 

			boolean x = p.getNavn().equals("X");
			if (x)
				p.setNavn("Tull"); // Virker ikke siden p er detached
			if (x)
				q.setNavn("Tull"); // Virker siden q er managed
				// Her ^ vil navnet oppdateres automatisk i databasen
			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	public void deletePerson(Person p) {
		
		// Kan ikke ta en direkte remove på p, må bruke em.merge(p)

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.remove(em.merge(p)); // Kjerneoperasjonen, 
			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

}
