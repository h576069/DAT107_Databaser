package no.hvl.dat107;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;

import java.util.List;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static AnsattDAO ansattDAO = new AnsattDAO();
	private static AvdelingDAO avdelingDAO = new AvdelingDAO();
	private static ProsjektDAO prosjektDAO = new ProsjektDAO();

	public static void main(String[] args) {
		System.out.println("Velkommen til min database!");
		System.out.println("*~*~*~*~*~*~*~*~*~*~*~*~*~*");

		String input = "";

		while (!input.equals("0")) {
			mainMenu();
			input = sc.nextLine();

			if (input.equals("1")) {
				sok();
			} else if (input.equals("2")) {
				skriv();
			} else if (input.equals("3")) {
				oppdater();
			} else if (input.equals("4")) {
				leggTil();
			} else if (input.equals("0")) {
				System.out.println("Avslutter...");
			} else {
				System.out.println("Ugyldig verdi, prøv igjen!");
			}
		}
		sc.close();

	}

	private static void mainMenu() {
		System.out.println("\n\tHOVEDMENY");

		System.out.println("1: Søk etter ...");
		System.out.println("2: Skriv ut ...");
		System.out.println("3: Oppdater ...");
		System.out.println("4: Legg til ny(tt)");
		System.out.println("0: Avslutt");
		System.out.print("\nDitt valg: ");
	}

	private static void sok() {
		sokMeny();
		String valg = sc.nextLine();

		if (valg.equals("1")) { // Ansatt
			System.out.println("\tSØK ETTER ANSATT");
			System.out.println("1: Ved ansatt id");
			System.out.println("2: Ved brukernavn");
			System.out.print("\nDitt valg: ");

			Ansatt a = null;

			String input = sc.nextLine();
			if (input.equals("1")) { // VED ID:
				System.out.print("Ansatt id: ");
				int id = Integer.parseInt(sc.nextLine());
				a = ansattDAO.finnAnsattID(id);
			} else if (input.equals("2")) { // VED BRUKERNAVN
				System.out.print("Brukernavn: ");
				String brnavn = sc.nextLine();
				a = ansattDAO.finnAnsattBN(brnavn);
			}
			// Sjekker at vi faktisk fant noe.
			if (a == null) {
				System.out.println("Fant ikke den ansatte!");
			} else {
				System.out.println(a);
			}
		} else if (valg.equals("2")) { // Avdeling
			System.out.println("\tSØK ETTER AVDELING");

			Avdeling a = null;

			System.out.print("Avdeling id: ");
			int id = Integer.parseInt(sc.nextLine());
			a = avdelingDAO.finnAvdelingMedId(id);

			if (a == null) {
				System.out.println("Fant ikke avdelingen!");
			} else {
				System.out.println(a);
			}
		} else if (valg.equals("3")) { // Prosjekt
			System.out.print("Prosjekt id: ");
			int id = Integer.parseInt(sc.nextLine());
			Prosjekt p = prosjektDAO.finnProsjektID(id);
			if (p == null) {
				System.out.println("Fant ikke prosjektet!");
			} else {
				System.out.println(p);
			}
		}
	}
	private static void sokMeny() {
		System.out.println("\tSØK ETTER ");
		System.out.println("1: Ansatt");
		System.out.println("2: Avdeling");
		System.out.println("3: Prosjekt");
		System.out.print("\nDitt valg: ");
	}

	private static void skriv() {
		skrivMeny();
		String valg = sc.nextLine();

		if (valg.equals("1")) { // Alle ansatte
			ansattDAO.skrivUtAlle();
		} else if (valg.equals("2")) { // Alle avdelinger
			avdelingDAO.skrivUtAvdelinger();
		} else if (valg.equals("3")) { // Alle prosjekter
			prosjektDAO.skrivUtAlle();
		} else if (valg.equals("4")) { // Ansatte på en avdeling
			System.out.println("\nAvdelinger å velge mellom:");
			avdelingDAO.visAlternativer();
			System.out.print("\nAvdeling id: ");
			int id = Integer.parseInt(sc.nextLine());

			// Finner avdelingen:
			Avdeling a = avdelingDAO.finnAvdelingMedId(id);
			while (a == null) {
				System.out.println("Ikke gyldig verdi! Prøv igjen.");
				id = Integer.parseInt(sc.nextLine());
				a = avdelingDAO.finnAvdelingMedId(id);
			}

			List<Ansatt> ansatte = ansattDAO.hentAnsattePaaAvdeling(id);
			System.out.println("Ansatte på avdelingen: " + a.getAvdelingid() + " - " + a.getNavn());
			for (Ansatt ans : ansatte) {
				if (ans.getAnsattid() == a.getSjefsansatt()) {
					System.out.println("SJEF: " + ans);
				} else {
					System.out.println(ans);
				}
			}
		} else if (valg.equals("5")) { // Om et prosjekt
			System.out.print("Prosjekt id: ");
			int id = Integer.parseInt(sc.nextLine());
			
			// Finner prosjektet:
			Prosjekt p = prosjektDAO.finnProsjektID(id);
			while (p == null) {
				System.out.println("Ikke gyldig verdi! Prøv igjen.");
				id = Integer.parseInt(sc.nextLine());
				p = prosjektDAO.finnProsjektID(id);
			}
			
			System.out.println(p);
		}
	}
	private static void skrivMeny() {
		System.out.println("\tSKRIV UT ");
		System.out.println("1: Alle ansatte");
		System.out.println("2: Alle avdelinger");
		System.out.println("3: Alle prosjekter");
		System.out.println("4: Alle ansatte på en avdeling");
		System.out.println("5: Info om et prosjekt");
		System.out.print("\nDitt valg:");
	}

	private static void oppdater() {
		oppdaterMeny();
		String valg = sc.nextLine();
		
		System.out.print("Ansatt id: ");
		int id = Integer.parseInt(sc.nextLine());
		
		if (valg.equals("1")) { // Stilling
			System.out.print("Ny stilling: ");
			String nyStilling = sc.nextLine();
			ansattDAO.oppdaterStilling(id, nyStilling);
		} else if (valg.equals("2")) { // Lønn
			System.out.print("Ny månedslønn: ");
			BigDecimal nyLonn = new BigDecimal(sc.nextLine());
			ansattDAO.oppdaterLonn(id, nyLonn);
		} else if (valg.equals("3")) { // Avdeling
			System.out.print("Ny avdeling id: ");
			int avdId = Integer.parseInt(sc.nextLine());
			ansattDAO.oppdaterAvdeling(id, avdId);
		} else if (valg.equals("4")) { // Timer på et prosjekt
			System.out.println("\nProsjekter å velge mellom:");
			ansattDAO.finnAnsattID(id).visProsjekter();
			System.out.print("\nProsjekt id: ");
			int proId = Integer.parseInt(sc.nextLine());
			System.out.print("Nye timer: ");
			int nyeTimer = Integer.parseInt(sc.nextLine());
			ansattDAO.oppdaterTimer(id, proId, nyeTimer);
		}
	}
	private static void oppdaterMeny() {
		System.out.println("\tOPPDATER ");
		System.out.println("1: Ansatt sin stilling");
		System.out.println("2: Ansatt sin lønn");
		System.out.println("3: Ansatt sin avdeling");
		System.out.println("4: Timer på et prosjekt");
		System.out.print("\nDitt valg:");
	}
	
	private static void leggTil() {
		leggTilMeny();
		String valg = sc.nextLine();
		
		if (valg.equals("1")) { // Ansatt
			System.out.print("Fornavn: ");
			String fornavn = sc.nextLine();
			System.out.print("Etternavn: ");
			String etternavn = sc.nextLine();

			// Finner brukernavn ut fra navnet
			String brukernavn = (fornavn.charAt(0) + etternavn.substring(0, 2)).toLowerCase();
			if (ansattDAO.eksistererBN(brukernavn)) {
				brukernavn = (fornavn.substring(0, 2) + etternavn.substring(0, 2)).toLowerCase();
			}
			System.out.print("Stilling: ");
			String stilling = sc.nextLine();
			System.out.print("Avdeling id: ");
			int avdID = Integer.parseInt(sc.nextLine());
			Avdeling avdeling = avdelingDAO.finnAvdelingMedId(avdID);
			while (avdeling == null) {
				System.out.println("Ugyldig avdeling id! Prøv igjen");
				System.out.print("Avdeling id: ");
				avdID = Integer.parseInt(sc.nextLine());
				avdeling = avdelingDAO.finnAvdelingMedId(avdID);
			}
			System.out.println("Velg et av prosjektene:");
			prosjektDAO.visAlternativer();
			System.out.print("Prosjekt id: ");
			int proID = Integer.parseInt(sc.nextLine());
			System.out.print("Prosjekt rolle: ");
			String rolle = sc.nextLine();
			
			// Oppretter den ansatte:
			Ansatt ny = new Ansatt(brukernavn, fornavn, etternavn, LocalDate.now(), stilling, new BigDecimal("30000"),
					avdeling);

			ansattDAO.leggTilNy(ny);
			ansattDAO.registrerProsjektdeltagelse(ny, prosjektDAO.finnProsjektID(proID), rolle);
		} else if (valg.equals("2")) { // Avdeling
			System.out.print("Navn: ");
			String navn = sc.nextLine();

			// Sjef for avdelingen med alternativer:
			System.out.println("Alternative sjefer til nye avdelingen: ");
			ansattDAO.visAlternativer();
			System.out.print("\nAnsatt id: ");
			int sjefID = Integer.parseInt(sc.nextLine());

			// Oppretter avdelingen:
			Avdeling ny = new Avdeling(navn, sjefID);
			// Legger inn avdelingen
			avdelingDAO.leggTilNy(ny);

			// Oppdaterer den som skal bli sjef:
			ansattDAO.oppdaterAvdelingUlovlig(sjefID, ny.getAvdelingid());
		} else if (valg.equals("3")) { // Prosjekt
			System.out.print("Prosjektnavn: ");
			String navn = sc.nextLine();
			System.out.print("Prosjektbeskrivelse: ");
			String besk = sc.nextLine();

			// Oppretter prosjektet:
			System.out.println("Oppretter prosjekt...");
			Prosjekt p = new Prosjekt(navn, besk);

			// Legger til prosjektet:
			System.out.println("Legger til prosjektet...");
			prosjektDAO.leggTilProsjekt(p);
		} else if (valg.equals("4")) { // Prosjektdeltagelse
			System.out.print("Prosjekt id: ");
			int pID = Integer.parseInt(sc.nextLine());

			System.out.print("Ansatt id: ");
			int aID = Integer.parseInt(sc.nextLine());

			System.out.print("Rolle: ");
			String rolle = sc.nextLine();

			ansattDAO.registrerProsjektdeltagelse(ansattDAO.finnAnsattID(aID), prosjektDAO.finnProsjektID(pID), rolle);
		}
	}
	private static void leggTilMeny() {
		System.out.println("\tLEGG TIL NY(TT) ");
		System.out.println("1: Ansatt");
		System.out.println("2: Avdeling");
		System.out.println("3: Prosjekt");
		System.out.println("4: Prosjektdeltagelse");
		System.out.print("\nDitt valg:");
	}

}
