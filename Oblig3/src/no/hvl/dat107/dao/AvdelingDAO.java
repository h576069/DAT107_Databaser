package no.hvl.dat107.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.Avdeling;

//import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class AvdelingDAO {
	private EntityManagerFactory emf;

	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("arbeidsplassPU");
	}

	/**
	 * Finner en avdeling ut fra avdelings id
	 * 
	 * @param id, id til avdelingen
	 * @return avdelingen, null hvis den ikke fins
	 */
	public Avdeling finnAvdelingMedId(int id) {
		EntityManager em = emf.createEntityManager();
		Avdeling a = null;
		try {
			a = em.find(Avdeling.class, id);
		} finally {
			em.close();
		}
		return a;
	}

	/**
	 * Metode som skriver ut en oversikt over alle avdelingene
	 */
	public void skrivUtAvdelinger() {
		List<Avdeling> alle = hentUtAlle();

		System.out.println("\n Alle avdelingene: ");
		for (Avdeling a : alle) {
			System.out.println(a);
		}
		System.out.println("---");
	}

	/**
	 * Metode som returnerer en liste over alle avdelingene
	 * @return liste over alle avdelingene
	 */
	private List<Avdeling> hentUtAlle() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Avdeling> query = em.createQuery("SELECT a FROM Avdeling a", Avdeling.class);
		List<Avdeling> alle = query.getResultList();
		em.close();
		return alle;
	}
	
	/**
	 * Metode som returnerer en tabell med ansattid til alle som er sjef
	 * @return ansatt id til alle sjefene
	 */
	public int[] alleSjefer() {
		List<Avdeling> alle = hentUtAlle();
		int len = alle.size();
		int[] sjefer = new int[len];
		int i = 0;
		for (Avdeling a : alle) {
			sjefer[i] = a.getSjefsansatt();
			i++;
		}
		return sjefer;
	}

	/**
	 * Metode som legger til en ny avdeling
	 * @param ny, den nye avdelingen
	 * @return id til den nye avdelingen
	 */
	public int leggTilNy(Avdeling ny) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(ny);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return ny.getAvdelingid();
	}

	/**
	 * Metode som viser en liste over alle avdeligene som alternativ, brukes i main metoden.
	 */
	public void visAlternativer() {
		List<Avdeling> alle = hentUtAlle();
		
		for (Avdeling a : alle) {
			System.out.println(a.getAvdelingid() + ": " + a.getNavn());
		}
	}
	
}
