package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import no.hvl.dat107.dao.ProsjektdeltagelseDAO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
//import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
//import javax.persistence.JoinTable;
//import javax.persistence.JoinColumn;
import java.util.List;

@Entity
@Table(schema = "oblig3")
public class Prosjekt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosjektid;

	private String navn;

	private String beskrivelse;

	@OneToMany(mappedBy = "prosjekt")
	private List<Prosjektdeltagelse> prosjektdeltagelse;
	
	public Prosjekt() {
	}

	public Prosjekt(String navn, String beskrivelse) {
		this.navn = navn;
		this.beskrivelse = beskrivelse;
	}

	@Override
	public String toString() {
		ProsjektdeltagelseDAO pdDAO = new ProsjektdeltagelseDAO();
		return "Prosjekt [prosjektid=" + prosjektid + ", navn=" + navn + ", beskrivelse=" + beskrivelse + ", deltagere="
				+ pdDAO.alleAnsatteForProsjekt(this.prosjektid) + "]";
	}

	public String skrivFint() {
		return "Prosjekt [prosjektid=" + prosjektid + ", navn=" + navn + ", beskrivelse=" + beskrivelse + "]";
	}

	public int getProsjektid() {
		return prosjektid;
	}

	public void setProsjektid(int prosjektid) {
		this.prosjektid = prosjektid;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public void leggTilProsjektDeltagelse(Prosjektdeltagelse pd) {
		this.prosjektdeltagelse.add(pd);
	}

}
