package bll;

import java.util.List;

import bo.Article;
import bo.Categorie;
import bo.Enchere;
import bo.Enchere.Statut;
import bo.Utilisateur;
import dal.BusinessException;
import dal.DAOFactory;
import dal.EnchereDAO;

public class EnchereManager {

	private EnchereDAO enchereDAO;

	public EnchereManager() {
		this.enchereDAO = DAOFactory.getEnchereDAO();
	}

	public Enchere ajouter(Utilisateur utilisateur, Article article, Enchere enchere) throws BusinessException {
		BusinessException exception = new BusinessException();
		this.validerEnchere(utilisateur, article, enchere, exception);

		if (!exception.hasErreurs()) {
			this.enchereDAO.insert(utilisateur, article, enchere);
		} else {
			throw exception;
		}
		return enchere;
	}

	public List<Enchere> selectionTout() throws BusinessException {
		return this.enchereDAO.selectAll();
	}

	public List<Enchere> selectionParArticle(int idArticle) throws BusinessException {
		return this.enchereDAO.selectByArticle(idArticle);
	}

	public Enchere selectionParArticleUtilisateur(int idArticle, int idUtilisateur) throws BusinessException {
		return this.enchereDAO.selectByArticleUtilisateur(idArticle, idUtilisateur);
	}

	public List<Enchere> selectionParUtilisateur(int idUtilisateur) throws BusinessException {
		return this.enchereDAO.selectByArticle(idUtilisateur);
	}

	private void validerEnchere(Utilisateur utilisateur, Article article, Enchere enchere,
			BusinessException businessException) {
		if (enchere == null || utilisateur == null || article == null) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ENCHERE_ERREUR);
		}
	}

	public void removeEnchere(int idEnchere) throws BusinessException {
		this.enchereDAO.remove(idEnchere);
	}

	public void update(Enchere enchere, Article article) throws BusinessException {
		this.enchereDAO.update(enchere, article);
	}

	public List<Article> selectionFiltre(List<Statut> encheresStatut, Categorie categorieFiltre,
			Utilisateur utilisateur, int page) throws BusinessException {
		return this.enchereDAO.selectionFiltre(encheresStatut, categorieFiltre, utilisateur, page);
	}

	public List<Article> selectionArticles(int page) throws BusinessException {
		return this.enchereDAO.selectArticles(page);
	}

	public int getNbRows() throws BusinessException {
		return this.enchereDAO.foundRows();
	}

	public void removeEnchereUtilisateur(Integer idUtilisateur) throws BusinessException {
		this.enchereDAO.removeUtilisateur(idUtilisateur);
	}
}
