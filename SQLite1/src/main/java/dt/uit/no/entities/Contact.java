package dt.uit.no.entities;

//En enkel "entitetsklasse"
public class Contact {
	private String etterNavn;
	private String forNavn;
	private String fbLink;
	private long id;

	public Contact(String etterNavn, String forNavn, String fbLink) {
		super();
		this.etterNavn = etterNavn;
		this.forNavn = forNavn;
		this.fbLink = fbLink;
	}

	public Contact() {
		super();
		this.etterNavn = "";
		this.forNavn = "";
		this.fbLink = "";
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEtterNavn() {
		return etterNavn;
	}

	public void setEtterNavn(String etterNavn) {
		this.etterNavn = etterNavn;
	}

	public String getForNavn() {
		return forNavn;
	}

	public void setForNavn(String forNavn) {
		this.forNavn = forNavn;
	}

	public String getFbLink() {
		return fbLink;
	}

	public void setFbLink(String fbLink) {
		this.fbLink = fbLink;
	}

	@Override
	public String toString() {
		return id + " " + etterNavn + " " + forNavn;
	}
}
