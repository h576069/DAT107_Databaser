package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


@Entity
@Table(name="prosjektdeltagelse", schema="oblig3")
public class Prosjektdeltagelse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pdid;
	
	@ManyToOne
	@JoinColumn(name="ansattid")
	private Ansatt ansatt;
	
	@ManyToOne
	@JoinColumn(name="prosjektid")
	private Prosjekt prosjekt;
	private String rolle;
	private int timer;
	
	public Prosjektdeltagelse() {}
	
	public Prosjektdeltagelse(Ansatt ansatt, Prosjekt prosjekt, String rolle) {
		this.ansatt = ansatt;
		this.prosjekt = prosjekt;
		this.rolle = rolle;
		this.timer = 0;
	}
	
	
	public int getPdid() {
		return pdid;
	}

	public void setPdid(int pdid) {
		this.pdid = pdid;
	}

	public Ansatt getAnsatt() {
		return ansatt;
	}

	public void setAnsatt(Ansatt ansatt) {
		this.ansatt = ansatt;
	}

	public Prosjekt getProsjekt() {
		return prosjekt;
	}

	public void setProsjekt(Prosjekt prosjekt) {
		this.prosjekt = prosjekt;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	@Override
	public String toString() {
		return "Prosjektdeltagelse [pdid=" + pdid + ", prosjekt=" + prosjekt.skrivFint() + ", rolle=" + rolle + ", timer=" + timer
				+ "]";
	}
	
	

}
