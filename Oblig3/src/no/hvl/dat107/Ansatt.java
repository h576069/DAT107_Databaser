package no.hvl.dat107;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
@Table(schema = "oblig3")
public class Ansatt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ansattid;
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansattdato;
	private String stilling;
	private BigDecimal maanedslonn;
	
	
	// Avdeling foreign key:
	@ManyToOne
	@JoinColumn(name = "avdeling", referencedColumnName = "avdelingid")
	private Avdeling avdeling;
	
	@OneToMany(mappedBy="ansatt")
	private List<Prosjektdeltagelse> prosjektdeltagelse;
	
	
	public Ansatt() {}
	
	public Ansatt(String brukernavn, String fornavn, String etternavn, LocalDate ansattdato,
			String stilling, BigDecimal maanedslonn, Avdeling avdeling) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansattdato = ansattdato;
		this.stilling = stilling;
		this.maanedslonn = maanedslonn;
		this.avdeling = avdeling;
	}
	
	public int getAnsattid() {
		return ansattid;
	}
	public void setAnsattid(int ansattid) {
		this.ansattid = ansattid;
	}
	public String getBrukernavn() {
		return brukernavn;
	}
	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}
	public String getFornavn() {
		return fornavn;
	}
	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}
	public String getEtternavn() {
		return etternavn;
	}
	
	public String getNavn() {
		return fornavn + " " + etternavn;
	}
	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}
	public LocalDate getAnsattdato() {
		return ansattdato;
	}
	public void setAnsattdato(LocalDate ansattdato) {
		this.ansattdato = ansattdato;
	}
	public String getStilling() {
		return stilling;
	}
	public void setStilling(String stilling) {
		this.stilling = stilling;
	}
	public BigDecimal getMaanedslonn() {
		return maanedslonn;
	}
	public void setMaanedslonn(BigDecimal maanedslonn) {
		this.maanedslonn = maanedslonn;
	}
	public Avdeling getAvdeling() { 
		return avdeling;
	}
	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}
	public List<Prosjektdeltagelse> getProsjektdeltagelse() {
		return prosjektdeltagelse;
	}
	public void setProsjekt(List<Prosjektdeltagelse> prosjektdeltagelse) {
		this.prosjektdeltagelse = prosjektdeltagelse;
	}
	@Override
	public String toString() {
		return "Ansatt [ansattid=" + ansattid + ", brukernavn=" + brukernavn + ", fornavn=" + fornavn + ", etternavn="
				+ etternavn + ", ansattdato=" + ansattdato + ", stilling=" + stilling + ", maanedslonn=" + maanedslonn
				+ ", avdeling=" + avdeling + ", prosjekt=" + skrivPD() + "]";
	}
	
	public String skrivPD() {
		String res = "{ ";
		for (Prosjektdeltagelse pd : prosjektdeltagelse) {
			res += (pd + " ");
		}
		res += "}";
		return res;
	}
	
	
	public void leggTilProsjektDeltagelse(Prosjektdeltagelse pd) {
		this.prosjektdeltagelse.add(pd);
	}
	
	public void visProsjekter() {
		for (Prosjektdeltagelse pd : prosjektdeltagelse) {
			System.out.println(pd.getProsjekt().getProsjektid() + ": " + pd.getProsjekt().getNavn());
		}
	}
}
