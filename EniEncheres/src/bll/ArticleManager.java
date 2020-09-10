package bll;

import java.util.List;

import bo.Article;
import bo.Article.Statut;
import bo.Categorie;
import bo.Enchere;
import bo.Utilisateur;
import dal.ArticleDAO;
import dal.BusinessException;
import dal.DAOFactory;

public class ArticleManager {

	private ArticleDAO articleDAO;

	public ArticleManager() {
		this.articleDAO=DAOFactory.getArticleDAO();
	}
	
	public List<Article> selectionTout() throws BusinessException{
		return this.articleDAO.selectAll();
	}
	
	public void removeArticle(int idArticle) throws BusinessException{
		this.articleDAO.remove(idArticle);
	}
	
	public Article selectById(int idArticle) throws BusinessException{
		return this.articleDAO.selectById(idArticle);
	}

	public Article ajouter(Article article, Utilisateur utilisateur, Categorie categorie) throws BusinessException {
		
		BusinessException exception = new BusinessException();
		this.validerArticles(article,exception);
			
		Article articleReturned = null;
		
		if(!exception.hasErreurs())
		{
			articleReturned = this.articleDAO.ajouter(article, utilisateur, categorie);
		}
		else
		{
			throw exception;
		}
		
		return articleReturned;
	}
	

	private void validerArticles(Article article, BusinessException businessException) {
		if(article==null)
		{
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_ERREUR);
		}
	}

	public List<Enchere> selectionFiltre(List<Statut> arcticleStatut, Utilisateur utilisateur)  throws BusinessException{
		return this.articleDAO.selectionFiltre(arcticleStatut, utilisateur);
	}
}
