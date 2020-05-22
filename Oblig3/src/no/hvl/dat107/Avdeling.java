package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(schema = "oblig3")
public class Avdeling {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int avdelingid;
	private String navn;
	
	private int sjefsansatt;
	
	public Avdeling() {}

	public Avdeling(String navn, int sjefsansatt) {
		this.navn = navn;
		this.sjefsansatt = sjefsansatt;
	}

	public int getAvdelingid() {
		return avdelingid;
	}

	public void setAvdelingid(int avdelingid) {
		this.avdelingid = avdelingid;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public int getSjefsansatt() {
		return sjefsansatt;
	}

	public void setSjefsansatt(int sjefsansatt) {
		this.sjefsansatt = sjefsansatt;
	}
	@Override
	public String toString() {
		return "Avdeling [avdelingid=" + avdelingid + ", navn=" + navn + ", sjefsansatt=" + sjefsansatt + "]";
	}

}
