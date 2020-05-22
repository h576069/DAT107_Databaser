package no.hvl.dat107.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.Ansatt;
import no.hvl.dat107.Avdeling;
import no.hvl.dat107.Prosjekt;
import no.hvl.dat107.Prosjektdeltagelse;

public class AnsattDAO {

	private EntityManagerFactory emf;
	AvdelingDAO avdelingDAO = new AvdelingDAO();
	ProsjektDAO prosjektDAO = new ProsjektDAO();

	public AnsattDAO() {
		emf = Persistence.createEntityManagerFactory("arbeidsplassPU");
	}

	/**
	 * Metode for å finne en ansatt i databasen basert på ansatt id
	 * 
	 * @param id, ansatt id til den du vil finne
	 * @return den ansatte, null hvis de ikke finnes
	 */
	public Ansatt finnAnsattID(int id) {
		EntityManager em = emf.createEntityManager();
		Ansatt a = null;
		try {
			a = em.find(Ansatt.class, id);
		} finally {
			em.close();
		}
		return a;
	}

	/**
	 * Metode for å finne en ansatt i databasen basert på brukernavnet
	 * 
	 * @param brukernavn, brukernavnet til den du vil finne
	 * @return den ansatte, null hvis de ikke finnes
	 */
	public Ansatt finnAnsattBN(String brukernavn) {
		EntityManager em = emf.createEntityManager();
		Ansatt a = null;
		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn = :bnavn",
					Ansatt.class);
			query.setParameter("bnavn", brukernavn);
			a = query.getSingleResult();
		} catch (NoResultException e) {
			// Da returneres null
		} finally {
			em.close();
		}
		return a;
	}

	/**
	 * Skriver ut en oversikt over alle de ansatte og deres informasjon.
	 */
	public void skrivUtAlle() {
		List<Ansatt> alle = hentUtAlle();

		System.out.println("\n Alle de ansatte: ");
		for (Ansatt a : alle) {
			System.out.println(a);
		}
		System.out.println("---");
	}

	/**
	 * Privat metode som returnerer alle de ansatte på liste-format
	 * 
	 * @return Liste over alle de ansatte
	 */
	private List<Ansatt> hentUtAlle() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a", Ansatt.class);
		List<Ansatt> alle = query.getResultList();
		em.close();
		return alle;
	}

	/**
	 * Oppdaterer stillingen til en ansatt
	 * 
	 * @param id,         ansatt id for den ansatte du vil oppdatere stillingen til
	 * @param nyStilling, den nye stillingen til en ansatt
	 * @return sant hvis oppdateringen ble gjennomført, usant ellers.
	 */
	public boolean oppdaterStilling(int id, String nyStilling) {
		EntityManager em = emf.createEntityManager();

		// Finner den ansatte:
		Ansatt a = em.find(Ansatt.class, id);

		if (a != null) {
			try {
				em.getTransaction().begin();

				a.setStilling(nyStilling);
				em.getTransaction().commit();

			} catch (Throwable e) {
				e.printStackTrace();
				em.getTransaction().rollback();
			} finally {
				em.close();
			}
			return true;

		} else {
			System.out.println("Kunne ikke oppdatere for ansatt " + id + " fordi den ikke eksisterer.");
			return false;
		}

	}

	/**
	 * Metode som oppdaterer antall timer for en ansatt på et prosjekt
	 * 
	 * @param ansID,    id til den ansatte
	 * @param proID,    id til prosjektet
	 * @param nyeTimer, de nye timene som skal registreres
	 */
	public void oppdaterTimer(int ansID, int proID, int nyeTimer) {
		EntityManager em = emf.createEntityManager();

		Prosjektdeltagelse pd = finnProsjektdeltagelse(ansID, proID);

		try {
			em.getTransaction().begin();

			// Må hente ut den managede versjonen:
			pd = em.merge(pd);

			pd.setTimer(nyeTimer);
			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	/**
	 * Oppdaterer månedslønnen til en ansatt
	 * 
	 * @param id,     ansatt id for den ansatte du vil oppdatere lønnen til
	 * @param nyLonn, den nye månedslønnen
	 * @return sant hvis oppdateringen ble gjennomført, usant ellers.
	 */
	public boolean oppdaterLonn(int id, BigDecimal nyLonn) {
		EntityManager em = emf.createEntityManager();

		// Finner den ansatte:
		Ansatt a = em.find(Ansatt.class, id);

		if (a != null) {
			try {
				em.getTransaction().begin();

				// Må hente ut den managede versjonen:
				Ansatt b = em.merge(a);

				b.setMaanedslonn(nyLonn);
				em.getTransaction().commit();

			} catch (Throwable e) {
				e.printStackTrace();
				em.getTransaction().rollback();
			} finally {
				em.close();
			}
			return true;

		} else {
			System.out.println("Kunne ikke oppdatere for ansatt " + id + " fordi den ikke eksisterer.");
			return false;
		}
	}

	/**
	 * Metode for å legge til en ny ansatt i databasen
	 * 
	 * @param ny, den nye ansatte
	 * @return ansattid til den nye
	 */
	public int leggTilNy(Ansatt ny) {
		EntityManager em = emf.createEntityManager();

		boolean eksisterer = eksistererBN(ny.getBrukernavn());

		if (!eksisterer) {
			try {
				em.getTransaction().begin();
				em.persist(ny);
				em.getTransaction().commit();
			} finally {
				em.close();
			}
			return ny.getAnsattid();
		} else {
			System.out.println("Duplikat av brukernavn, prøv et annet");
			return -1;
		}

	}

	/**
	 * Metode som sjekker om et brukernavn allerede er i bruk
	 * 
	 * @param bnavn, brukernavnet vi sjekker for
	 * @return sant hvis den er i bruk, usant hvis ikke
	 */
	public boolean eksistererBN(String bnavn) {
		List<Ansatt> alle = hentUtAlle();

		for (Ansatt a : alle) {
			if (a.getBrukernavn().equals(bnavn)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metode som henter ut alle de ansatte på en spesifisert avdeling
	 * 
	 * @param avdID, avdelingsid til avdelingen
	 * @return liste over de ansatte
	 */
	public List<Ansatt> hentAnsattePaaAvdeling(int avdID) {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.avdeling.avdelingid = ?1",
				Ansatt.class);
		query.setParameter(1, avdID);
		return query.getResultList();
	}

	/**
	 * Oppdaterer avdelingen til den ansatte
	 * 
	 * @param ansID, ansattid til den ansatte
	 * @param avdID, avdelingsid til den nye avdelingen
	 * @return sant hvis oppdateringen ble gjennomført, usant ellers
	 */
	public boolean oppdaterAvdeling(int ansID, int avdID) {
		Avdeling avd = avdelingDAO.finnAvdelingMedId(avdID);
		if (avd != null && !erSjef(ansID)) {
			EntityManager em = emf.createEntityManager();
			try {
				em.getTransaction().begin();

				// Må hente ut den managede versjonen:
				Ansatt b = em.merge(finnAnsattID(ansID));

				b.setAvdeling(avd);
				em.getTransaction().commit();
				return true;

			} catch (Throwable e) {
				e.printStackTrace();
				em.getTransaction().rollback();
				return false;

			} finally {
				em.close();
			}
		}

		if (erSjef(ansID)) {
			System.out.println("Ansatt med id " + ansID + " er sjef og kan ikke bytte avdeling.");
		}
		if (avd == null) {
			System.out.println("Avdelingen med id " + avdID + " fins ikke.");
		}

		return false;
	}

	/**
	 * Metode som sjekker om en ansatt er sjef for en hvilken som helst avdeling
	 * 
	 * @param ansID, ansatt id-en
	 * @return sant hvis sjef, usant ellers
	 */
	private boolean erSjef(int ansID) {
		int[] sjefer = avdelingDAO.alleSjefer();
		for (int s : sjefer) {
			if (ansID == s) {
				return true;
			}
		}
		return false;
	}

	/**
	 * En litt luguber metode for å oppdatere avdelingen til en ansatt som er
	 * registrert som sjef. Dette må gjøres når man oppretter en ny avdeling.
	 * 
	 * @param ansID, id til den ansatte
	 * @param nyAvd, id til den nye avdelingen
	 */
	public void oppdaterAvdelingUlovlig(int ansID, int nyAvd) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<Ansatt> query = em.createQuery("UPDATE Ansatt a SET a.avdeling = ?1 WHERE a.ansattid = ?2",
					Ansatt.class);
			query.setParameter(1, avdelingDAO.finnAvdelingMedId(nyAvd));
			query.setParameter(2, ansID);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	/**
	 * Metode for å registrere ny prosjektdeltagelse
	 * 
	 * @param a,     den ansatte
	 * @param p,     prosjektet
	 * @param rolle, rollen til den ansatte i prosjektet
	 */
	public void registrerProsjektdeltagelse(Ansatt a, Prosjekt p, String rolle) {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			a = em.merge(a);
			p = em.merge(p);

			Prosjektdeltagelse pd = new Prosjektdeltagelse(a, p, rolle);

			a.leggTilProsjektDeltagelse(pd);
			p.leggTilProsjektDeltagelse(pd);

			em.persist(pd);
			em.getTransaction().commit();

		} finally {
			em.close();
		}
	}

	/**
	 * Metode for å finne prosjektdeltagelsen for en ansatt på et spesifikt prosjekt
	 * 
	 * @param aID, ansatt id
	 * @param pID, prosjekt id
	 * @return prosjektdeltagelse objektet
	 */
	private Prosjektdeltagelse finnProsjektdeltagelse(int aID, int pID) {
		EntityManager em = emf.createEntityManager();
		String queryText = "SELECT pd FROM Prosjektdeltagelse pd WHERE pd.ansatt = :ans AND pd.prosjekt = :pro";
		TypedQuery<Prosjektdeltagelse> query = em.createQuery(queryText, Prosjektdeltagelse.class);
		query.setParameter("ans", finnAnsattID(aID));
		query.setParameter("pro", prosjektDAO.finnProsjektID(pID));

		Prosjektdeltagelse res = query.getSingleResult();

		em.close();
		return res;
	}

	/**
	 * Viser alternativer til når man skal velge ny sjef ved opprettelse av en ny
	 * avdeling. Viser altså alle de ansatte som ikke er sjef.
	 */
	public void visAlternativer() {
		List<Ansatt> alle = hentUtAlle();

		for (Ansatt a : alle) {
			if (!erSjef(a.getAnsattid())) {
				System.out.println(a.getAnsattid() + ": " + a.getNavn());
			}
		}
	}

}
