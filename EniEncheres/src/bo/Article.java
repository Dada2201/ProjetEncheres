package bo;

import java.util.Date;
import java.util.List;

public class Article {

	private int noArticle;
	private String nomArticle;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	private int prixInitial;
	private int prixVente;
	private Utilisateur utilisateur;
	private Categorie categorie;
	private List<Enchere> encheres;
	
	public static enum Statut{
		NOT_READY,
		EN_COURS,
		CLOSE,
		NULL
	}
	
	public Article(int noArticle, String nomArticle, String description, Date dateDebut, Date dateFin, int prixInitial,
			int prixVente, Utilisateur utilisateur, Categorie categorie) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}
	
	public static Enchere.Statut getStatut(Article article, Utilisateur currentUtilisateur, Utilisateur utilisateurEnchere){
		if(article.getDateDebut().compareTo(new Date()) > 0) {
			return Enchere.Statut.NOT_READY;
		}	
		else if(article.getDateFin().compareTo(new Date()) < 0){
			if(currentUtilisateur != null && utilisateurEnchere.getId().equals(currentUtilisateur.getId())) {
				return Enchere.Statut.WIN;
			}else {
				return Enchere.Statut.FINI;
			}
		}
		else if(article.getDateDebut().compareTo(new Date()) < 0 || article.getDateDebut().compareTo(new Date()) == 0) {
			return Enchere.Statut.OPEN;
		}
		return Enchere.Statut.NULL;
	}
}
