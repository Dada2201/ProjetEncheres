package bll;

import java.util.List;

import bo.Article;
import bo.Categorie;
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

	public void ajouterParListe(Article article, Utilisateur utilisateur, Categorie categorie) throws BusinessException {
		
		BusinessException exception = new BusinessException();
		this.validerArticles(article,exception);
				
		if(!exception.hasErreurs())
		{
			this.articleDAO.ajouter(article, utilisateur, categorie);
		}
		else
		{
			throw exception;
		}
	}
	

	private void validerArticles(Article article, BusinessException businessException) {
		if(article==null)
		{
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_ERREUR);
		}
	}
}
