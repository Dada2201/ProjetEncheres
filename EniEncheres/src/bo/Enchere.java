package bo;

import java.util.Date;

public class Enchere {

	private Utilisateur utilisateur;
	private Date dateEnchere;
	private int montantEnchere;
	private Statut statut;
	
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
	
	public static Article.Statut getStatut(Article article, Utilisateur currentUtilisateur){
		if(currentUtilisateur != null) {
			if(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)).after(article.getDateFin()) && article.getEncheres()!=null && article.getEncheres().size()!=0 && article.getEncheres().get(0).getUtilisateur().getId().equals(currentUtilisateur.getId())){
				return Article.Statut.WIN_ENCHERE;
			}
			
			for(int i = 0; i < article.getEncheres().size(); i++) {
				if(article.getEncheres().get(i).getUtilisateur().getId().equals(currentUtilisateur.getId())) {
					return Article.Statut.EN_COURS_UTILISATEUR_ENCHERE;
				}
			}
		}
		
		if(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)).after(article.getDateFin())){
			return Article.Statut.CLOSE;
		}
		
		return Article.Statut.EN_COURS_ENCHERE;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}
}
