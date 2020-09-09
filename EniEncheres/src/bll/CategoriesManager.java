package bll;

import java.util.List;

import bo.Article;
import bo.Categorie;
import bo.Utilisateur;
import dal.ArticleDAO;
import dal.BusinessException;
import dal.CategorieDAO;
import dal.DAOFactory;

public class CategoriesManager {

	private CategorieDAO categorieDAO;

	public CategoriesManager() {
		this.categorieDAO=DAOFactory.getCategorieDAO();
	}
	
	public List<Categorie> selectionTout() throws BusinessException{
		return this.categorieDAO.selectAll();
	}
	
	public Categorie selectionById(int idArticle) throws BusinessException{
		return this.categorieDAO.selectById(idArticle);
	}
}