package no.hvl.dat107.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import javax.persistence.TypedQuery;

//import no.hvl.dat107.Ansatt;
import no.hvl.dat107.Prosjekt;

public class ProsjektDAO {

	EntityManagerFactory emf;

	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("arbeidsplassPU");
	}

	/**
	 * Metode for å finne et prosjekt i databasen basert på prosjekt id
	 * 
	 * @param id, prosjekt id til den du vil finne
	 * @return prosjektet, null hvis det ikke finnes
	 */
	public Prosjekt finnProsjektID(int id) {
		EntityManager em = emf.createEntityManager();
		Prosjekt p = null;
		try {
			p = em.find(Prosjekt.class, id);
		} finally {
			em.close();
		}
		return p;
	}

	/**
	 * Metode som legger til et nytt prosjekt
	 * @param ny, det nye prosjektet som skal legges til
	 */
	public void leggTilProsjekt(Prosjekt ny) {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(ny);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
	/**
	 * Metode som skriver ut alle prosjektene
	 */
	public void skrivUtAlle() {
		List<Prosjekt> alle = hentUtAlle();

		System.out.println("\n Alle prosjekter: ");
		for (Prosjekt p : alle) {
			System.out.println(p);
		}
		System.out.println("---");
	}
	
	/**
	 * Privat metode som returnerer en liste som inneholder alle prosjektene
	 * @return listen som inneholder alle prosjektene i databasen
	 */
	private List<Prosjekt> hentUtAlle() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Prosjekt> query = em.createQuery("SELECT p FROM Prosjekt p", Prosjekt.class);
		List<Prosjekt> alle = query.getResultList();
		em.close();
		return alle;
	}
	
	/**
	 * Metode som viser alle prosjektene som alternativer slik at brukeren kan velge en av de i main
	 */
	public void visAlternativer() {
		List<Prosjekt> alle = hentUtAlle();
		for (Prosjekt p : alle) {
			System.out.println(p.getProsjektid() + ": " + p.getNavn());
		}
	}
}
