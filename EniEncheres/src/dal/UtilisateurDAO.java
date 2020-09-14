package dal;

import java.util.List;
import bo.Utilisateur;
import dal.BusinessException;

public interface UtilisateurDAO {

	public Utilisateur insert(Utilisateur utilisateur) throws BusinessException;

	public List<Utilisateur> selectAll() throws BusinessException;

	public Utilisateur selectById(int idUtilisateur) throws BusinessException;

	public void removeUtilisateur(int idUtilisateur) throws BusinessException;

	public Utilisateur selectByPseudo(String pseudo) throws BusinessException;

	public Utilisateur selectByPseudoPassword(String pseudo, String motDePasse) throws BusinessException;
	
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException;

	public Utilisateur selectByEmail(String email) throws BusinessException;

}
