package domain;

// Generated 6-feb-2012 9:19:46 by Hibernate Tools 3.2.1.GA

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "Work")
public class Work {

	private Long workId;
	private String title;
	private String creator;
	private double hoogte;
	private double breedte;
	private Calendar datum;
	private String thema;
	private String medium;
	private String afbeeldingspad;
	private String opmerking;
	private String adresEigenaar;
	private String VorigeEigenaar;
	private String personen;

	private Set<Collection> collecties = new HashSet<Collection>();

	public Work() {
		title = "";
		creator = "";
		hoogte = 0.0;
		breedte = 0.0;
		datum = new GregorianCalendar(0, 1, 1);
		thema = "";
		medium = "";
		afbeeldingspad = "";
		opmerking = "";
		adresEigenaar = "";
		VorigeEigenaar = "";
		personen = "";
	}

	public Work(String title, String creator) {
		this();
		setTitle(title);
		setCreator(creator);
	}

	public Work(Long werkid, String titel, String kunstenaar) {
		this.workId = werkid;
		this.title = titel;
		this.creator = kunstenaar;
	}

	@Column
	public String getVorigeEigenaar() {
		return VorigeEigenaar;
	}

	public void setVorigeEigenaar(String VorigeEigenaar) {
		this.VorigeEigenaar = VorigeEigenaar;
	}

	@Column
	public String getAdresEigenaar() {
		return adresEigenaar;
	}

	public void setAdresEigenaar(String adresEigenaar) {
		this.adresEigenaar = adresEigenaar;
	}

	@Column
	public String getAfbeeldingspad() {
		return afbeeldingspad;
	}

	public void setAfbeeldingspad(String afbeeldingspad) {
		this.afbeeldingspad = afbeeldingspad;
	}

	@Column
	public double getBreedte() {
		return breedte;
	}

	public void setBreedte(double breedte) {
		this.breedte = breedte;
	}

	@Column
	public Calendar getDatum() {
		return datum;
	}

	public void setDatum(Calendar datum) {
		this.datum = datum;
	}

	@Column
	public double getHoogte() {
		return hoogte;
	}

	public void setHoogte(double hoogte) {
		this.hoogte = hoogte;
	}

	@Column
	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	@Column
	public String getOpmerking() {
		return opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	@Column
	public String getPersonen() {
		return personen;
	}

	public void setPersonen(String personen) {
		this.personen = personen;
	}

	@Column
	public String getThema() {
		return thema;
	}

	public void setThema(String thema) {
		this.thema = thema;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return this.workId;
	}

	public void setId(Long werkid) {
		this.workId = werkid;
	}

	@Column(name = "title" )
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String titel) {
		this.title = titel;
	}

	@Column
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
	@ManyToMany
	@JoinTable(name = "WerkenCollecties")
	public Set<Collection> getCollecties() {
		return collecties;
	}

	public void setCollecties(Set<Collection> collecties) {
		this.collecties = collecties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((VorigeEigenaar == null) ? 0 : VorigeEigenaar.hashCode());
		result = prime * result
				+ ((adresEigenaar == null) ? 0 : adresEigenaar.hashCode());
		result = prime * result
				+ ((afbeeldingspad == null) ? 0 : afbeeldingspad.hashCode());
		long temp;
		temp = Double.doubleToLongBits(breedte);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((collecties == null) ? 0 : collecties.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		temp = Double.doubleToLongBits(hoogte);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((medium == null) ? 0 : medium.hashCode());
		result = prime * result
				+ ((opmerking == null) ? 0 : opmerking.hashCode());
		result = prime * result
				+ ((personen == null) ? 0 : personen.hashCode());
		result = prime * result + ((thema == null) ? 0 : thema.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Work other = (Work) obj;
		if (VorigeEigenaar == null) {
			if (other.VorigeEigenaar != null)
				return false;
		} else if (!VorigeEigenaar.equals(other.VorigeEigenaar))
			return false;
		if (adresEigenaar == null) {
			if (other.adresEigenaar != null)
				return false;
		} else if (!adresEigenaar.equals(other.adresEigenaar))
			return false;
		if (afbeeldingspad == null) {
			if (other.afbeeldingspad != null)
				return false;
		} else if (!afbeeldingspad.equals(other.afbeeldingspad))
			return false;
		if (Double.doubleToLongBits(breedte) != Double
				.doubleToLongBits(other.breedte))
			return false;
		if (collecties == null) {
			if (other.collecties != null)
				return false;
		} else if (!collecties.equals(other.collecties))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (Double.doubleToLongBits(hoogte) != Double
				.doubleToLongBits(other.hoogte))
			return false;
		if (medium == null) {
			if (other.medium != null)
				return false;
		} else if (!medium.equals(other.medium))
			return false;
		if (opmerking == null) {
			if (other.opmerking != null)
				return false;
		} else if (!opmerking.equals(other.opmerking))
			return false;
		if (personen == null) {
			if (other.personen != null)
				return false;
		} else if (!personen.equals(other.personen))
			return false;
		if (thema == null) {
			if (other.thema != null)
				return false;
		} else if (!thema.equals(other.thema))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
