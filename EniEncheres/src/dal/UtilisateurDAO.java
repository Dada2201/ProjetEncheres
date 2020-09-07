package dal;

import java.util.List;
import bo.Utilisateur;
import dal.BusinessException;

public interface UtilisateurDAO {

	public void insert(Utilisateur utilisateur) throws BusinessException;

	public List<Utilisateur> selectAll() throws BusinessException;

	public Utilisateur selectById(int idUtilisateur) throws BusinessException;

	public void removeUtilisateur(int idUtilisateur) throws BusinessException;

	public Utilisateur selectByPseudo(String pseudo) throws BusinessException;
}
