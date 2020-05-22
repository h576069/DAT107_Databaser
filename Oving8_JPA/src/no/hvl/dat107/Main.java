package no.hvl.dat107;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(("AnsattPersistenceUnit"));
		String brnavn = "";

		while (!brnavn.equals("zzz")) {
			EntityManager em = emf.createEntityManager();
			System.out.println("Skriv inn et brukernavn (3-4 tegn), zzz for å avslutte:");
			brnavn = sc.nextLine();
			
			Ansatt a = null;

			try {
				a = em.find(Ansatt.class, brnavn.toUpperCase());
			} finally {
				em.close();
			}
			
			if (a != null)
				System.out.println(a);
			else if (brnavn.equals("zzz"))
				System.out.println("Lukker programmet");
			else
				System.out.println("Bruker finnes ikke");
		}

	}

}
