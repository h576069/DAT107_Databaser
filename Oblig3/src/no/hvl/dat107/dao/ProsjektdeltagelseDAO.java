package no.hvl.dat107.dao;

//import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

//import no.hvl.dat107.Ansatt;
//import no.hvl.dat107.Prosjekt;
import no.hvl.dat107.Prosjektdeltagelse;

public class ProsjektdeltagelseDAO {

	EntityManagerFactory emf;
	ProsjektDAO prosjektDAO = new ProsjektDAO();

	public ProsjektdeltagelseDAO() {
		emf = Persistence.createEntityManagerFactory("arbeidsplassPU");
	}

	/**
	 * Metode som returnerer alle ansatte i et prosjekt inkludert individuelle
	 * roller og timer, og total antall timer registrert på prosjektet
	 * 
	 * @param proID, id til prosjektet
	 * @return en string som represneterer informasjonen.
	 */
	public String alleAnsatteForProsjekt(int proID) {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Prosjektdeltagelse> query = em.createQuery(
				"SELECT pd FROM Prosjektdeltagelse pd WHERE pd.prosjekt = :prosjektid", Prosjektdeltagelse.class);
		query.setParameter("prosjektid", prosjektDAO.finnProsjektID(proID));
		List<Prosjektdeltagelse> pdlisten = query.getResultList();

		int totTimer = 0;

		String ansattRolleTimer = "{";
		for (Prosjektdeltagelse pd : pdlisten) {
			ansattRolleTimer += ("(" + pd.getAnsatt().getNavn() + "|rolle=" + pd.getRolle() + "|timer=" + pd.getTimer()
					+ "), ");
			totTimer += pd.getTimer();
		}
		if (ansattRolleTimer.length() > 2) {
			ansattRolleTimer = ansattRolleTimer.substring(0, ansattRolleTimer.length() - 2);
		}
		ansattRolleTimer += ("|Totalt antall timer=" + totTimer + "}");

		em.close();
		return ansattRolleTimer;
	}
}
