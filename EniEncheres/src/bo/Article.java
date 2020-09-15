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
	private Statut statut;
	private String img;
	
	public static enum Statut{
		NOT_READY,
		EN_COURS,
		CLOSE,
		NULL,
		
		WIN_ENCHERE, 
		EN_COURS_UTILISATEUR_ENCHERE, 
		EN_COURS_ENCHERE
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
	
	public static Article.Statut getStatut(Article article, Utilisateur currentUtilisateur){
		if(article.getDateDebut().compareTo(new Date()) > 0) {
			return Article.Statut.NOT_READY;
		}	
		else if(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)).after(article.getDateFin())){
			return Article.Statut.CLOSE;
		}
		else {
			return Article.Statut.EN_COURS;
		}
	}

	public Statut getStatut() {
		return statut;
	}

	public void setImg(Article.Statut statut) {
		this.statut = statut;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "Article [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", prixInitial=" + prixInitial + ", prixVente="
				+ prixVente + ", utilisateur=" + utilisateur + ", categorie=" + categorie + ", encheres=" + encheres
				+ ", statut=" + statut + ", img=" + img + "]";
	}
}
