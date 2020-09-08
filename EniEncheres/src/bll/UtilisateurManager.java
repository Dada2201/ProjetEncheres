package bll;

import java.util.List;

import dal.BusinessException;
import bo.Utilisateur;
import dal.DAOFactory;
import dal.UtilisateurDAO;

public class UtilisateurManager {
	
	private UtilisateurDAO utilisateurDAO;
	
	public UtilisateurManager() {
		this.utilisateurDAO=DAOFactory.getUtilisateurDAO();
	}
	public Utilisateur ajouter(Utilisateur utilisateur) throws BusinessException
	{
		BusinessException exception = new BusinessException();
		
		this.validerUtilisateur(utilisateur, exception);
		System.out.println(utilisateur);
		this.utilisateurDAO.insert(utilisateur);
	System.out.println(utilisateur);
		return utilisateur;
	}	
	
	public List<Utilisateur> selectionTout() throws BusinessException{
		return this.utilisateurDAO.selectAll();
	}
	
	public Utilisateur selectionParId(int id) throws BusinessException{
		return this.utilisateurDAO.selectById(id);
	}
	
	public void removeListe(int idUtilisateur) throws BusinessException {
		this.utilisateurDAO.removeUtilisateur(idUtilisateur);
	}
	
	public Utilisateur selectionParPseudo(String pseudo) throws BusinessException {
		return this.utilisateurDAO.selectByPseudo(pseudo);		
	}
	
	private void validerUtilisateur (Utilisateur utilisateur, BusinessException businessException) {
		if(utilisateur==null)
		{
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEUR_ERREUR);
		}
		else
		{
			if(utilisateur.getEmail().length()>255)
			{
				businessException.ajouterErreur(CodesResultatBLL.REGLE_EMAIL_ERREUR);
			}
		}
	}
}
