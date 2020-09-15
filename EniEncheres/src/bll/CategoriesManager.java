package bll;

import java.util.List;

import bo.Categorie;
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
	
	public Categorie selectionById(int idCategorie) throws BusinessException{
		return this.categorieDAO.selectById(idCategorie);
	}
}
