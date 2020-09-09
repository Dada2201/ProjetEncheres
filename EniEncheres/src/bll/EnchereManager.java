package bll;

import java.util.List;

import bo.Article;
import bo.Enchere;
import bo.Utilisateur;
import dal.BusinessException;
import dal.DAOFactory;
import dal.EnchereDAO;


public class EnchereManager {
	
	private EnchereDAO enchereDAO;
	
	public EnchereManager() {
		this.enchereDAO=DAOFactory.getEnchereDAO();
	}

	public Enchere ajouter(Utilisateur utilisateur, Article article, Enchere enchere) throws BusinessException
	{
		BusinessException exception = new BusinessException();
		this.validerEnchere(utilisateur,article,enchere,exception);
				
		if(!exception.hasErreurs())
		{
			this.enchereDAO.insert(utilisateur, article, enchere);
		}
		else
		{
			throw exception;
		}
		return enchere;
	}	
	
	public List<Enchere> selectionTout() throws BusinessException{
		return this.enchereDAO.selectAll();
	}
	
	public Enchere selectionParArticle(int idArticle) throws BusinessException{
		return this.enchereDAO.selectByArticle(idArticle);
	}
	
	public Enchere selectionParUtilisateur(int idUtilisateur) throws BusinessException{
		return this.enchereDAO.selectByArticle(idUtilisateur);
	}
		
	private void validerEnchere(Utilisateur utilisateur, Article article, Enchere enchere, BusinessException businessException) {
		if(enchere==null || utilisateur==null || article==null)
		{
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ENCHERE_ERREUR);
		}
	}
	
	public void removeEnchere(int idEnchere) throws BusinessException {
		this.enchereDAO.remove(idEnchere);
	}

	public void update(Enchere enchere) throws BusinessException {
		this.enchereDAO.update(enchere);
	}
}
