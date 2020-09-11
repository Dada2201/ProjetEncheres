package bo;

import java.util.Date;

public class Enchere {

	private Utilisateur utilisateur;
	private Date dateEnchere;
	private int montantEnchere;
	
	public enum Statut{
		NOT_READY,
		OPEN,
		EN_COURS,
		EN_COURS_UTILISATEUR,
		FINI,
		WIN,
		NULL, 
	}
	
	public Enchere(Utilisateur utilisateur, Date dateEnchere, int montantEnchere) {
		super();
		this.utilisateur = utilisateur;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	public Enchere() {
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	@Override
	public String toString() {
		return "Enchere [utilisateur=" + utilisateur  + ", dateEnchere=" + dateEnchere
				+ ", montantEnchere=" + montantEnchere + "]";
	}
}
