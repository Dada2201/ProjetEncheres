package bo;

import java.util.Date;

public class Enchere {

	private Utilisateur utilisateur;
	private Article article;
	private Date dateEnchere;
	private int montantEnchere;
	
	public enum Statut{
		NOT_READY,
		OPEN,
		EN_COURS,
		FINI,
		WIN,
		NULL
	}
	
	public Enchere(Utilisateur utilisateur, Article article, Date dateEnchere, int montantEnchere) {
		super();
		this.utilisateur = utilisateur;
		this.article = article;
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
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
		return "Enchere [utilisateur=" + utilisateur + ", article=" + article + ", dateEnchere=" + dateEnchere
				+ ", montantEnchere=" + montantEnchere + "]";
	}

	public static Statut getStatut(Enchere enchere, Utilisateur currentUtilisateur) {
		if(enchere.getArticle().getDateDebut().compareTo(new Date()) > 0) {
			return Statut.NOT_READY;
		}	
		else if(enchere.getArticle().getDateFin().compareTo(new Date()) < 0){
			if(currentUtilisateur != null && enchere.getUtilisateur().getId().equals(currentUtilisateur.getId())) {
				return Statut.WIN;
			}else {
				return Statut.FINI;
			}
		}
		else if(enchere.getArticle().getDateDebut().compareTo(new Date()) < 0 || enchere.getArticle().getDateDebut().compareTo(new Date()) == 0) {
			return Statut.OPEN;
		}
		return Statut.NULL;
	}
}
