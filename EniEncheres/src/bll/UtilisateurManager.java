package bll;

import java.util.List;

import dal.BusinessException;
import bo.Utilisateur;
import dal.DAOFactory;
import dal.UtilisateurDAO;

public class UtilisateurManager {

	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public Utilisateur ajouter(Utilisateur utilisateur) throws BusinessException {
		BusinessException exception = new BusinessException();

		this.validerUtilisateur(utilisateur, exception);

		this.utilisateurDAO.insert(utilisateur);

		return utilisateur;
	}

	public List<Utilisateur> selectionTout(int idUser) throws BusinessException {
		return this.utilisateurDAO.selectAll(idUser);
	}

	public Utilisateur selectionParId(int id) throws BusinessException {
		return this.utilisateurDAO.selectById(id);
	}

	public void remove(int idUtilisateur) throws BusinessException {
		this.utilisateurDAO.removeUtilisateur(idUtilisateur);
	}

	public void update(Utilisateur utilisateur) throws BusinessException {
		this.utilisateurDAO.updateUtilisateur(utilisateur);
	}

	public Utilisateur selectionParPseudo(String pseudo) throws BusinessException {
		return this.utilisateurDAO.selectByPseudo(pseudo);
	}

	public Utilisateur selectionParPseudoMotDePasse(String pseudo, String motDePasse) throws BusinessException {
		return this.utilisateurDAO.selectByPseudoPassword(pseudo, motDePasse);
	}

	private void validerUtilisateur(Utilisateur utilisateur, BusinessException businessException) {
		if (utilisateur == null) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEUR_ERREUR);
		} else {
			if (utilisateur.getEmail().length() > 255) {
				businessException.ajouterErreur(CodesResultatBLL.REGLE_EMAIL_ERREUR);
			}
		}
	}

	public Utilisateur selectionParEmail(String email) throws BusinessException {
		return this.utilisateurDAO.selectByEmail(email);
	}
	
	public void updateCredit(Utilisateur utilisateur, int credit) throws BusinessException{
		this.utilisateurDAO.updateCredit(utilisateur,credit);
	}
}
