package bll;

import java.util.List;

import bo.Retrait;
import dal.BusinessException;
import dal.DAOFactory;
import dal.RetraitDAO;

public class RetraitManager {

	private RetraitDAO retraitDAO;

	public RetraitManager() {
		this.retraitDAO = DAOFactory.getRetraitDAO();
	}

	public List<Retrait> selectionTout() throws BusinessException {
		return this.retraitDAO.selectAll();
	}

	public void removeArticle(int idArticle) throws BusinessException {
		this.retraitDAO.remove(idArticle);
	}

	public Retrait selectById(int idArticle) throws BusinessException {
		return this.retraitDAO.selectByArticle(idArticle);
	}

	public void update(Retrait retrait) throws BusinessException {
		this.retraitDAO.update(retrait);
	}

	public Retrait ajouter(Retrait retrait) throws BusinessException {

		BusinessException exception = new BusinessException();
		this.validerRetrait(retrait, exception);

		Retrait retraitReturned = null;

		if (!exception.hasErreurs()) {
			this.retraitDAO.ajouter(retrait);
		} else {
			throw exception;
		}

		return retraitReturned;
	}

	private void validerRetrait(Retrait retrait, BusinessException businessException) {
		if (retrait == null) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAIT_ERREUR);
		}
	}
}
