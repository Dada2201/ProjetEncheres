package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Utilisateur;

class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String INSERT="INSERT INTO encheres.utilisateurs(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur)VALUES(?,?,?,?,?,?,?,?,?,?,?);";
	private static final String DELETE_LISTE = "DELETE from encheres.utilisateurs where no_utilisateur=?";
	private static final String UPDATE="UPDATE encheres.utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=? WHERE no_utilisateur = ?";
	private static final String SELECT_ALL="SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM encheres.utilisateurs;";
	private static final String SELECT_BY_ID="SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM encheres.utilisateurs where no_utilisateur = ?;";
	private static final String SELECT_BY_PSEUDO="SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM encheres.utilisateurs where pseudo = ?;";
	private static final String SELECT_BY_PSEUDO_MOT_DE_PASSE="SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM encheres.utilisateurs where pseudo = ? and mot_de_passe = ?;";

	@Override
	public Utilisateur insert(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur == null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setLong(10, utilisateur.getCredit());
			pstmt.setBoolean(11, utilisateur.getIsAdmin());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				utilisateur.setId(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}	
		return utilisateur;
	}
	
	@Override
	public List<Utilisateur> selectAll() throws BusinessException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Utilisateur utilisateur = utilisateurBuilder(rs);
				listeUtilisateurs.add(utilisateur);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_ECHEC);
			throw businessException;
		}
		return listeUtilisateurs;
	}
	
	@Override
	public Utilisateur selectById(int id) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Utilisateur utilisateur = utilisateurBuilder(rs);
				return utilisateur;
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_ECHEC);
			throw businessException;
		}
		return null;
	}
	
	@Override
	public Utilisateur selectByPseudoPassword(String pseudo, String motDePasse) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO_MOT_DE_PASSE);
			pstmt.setString(1, pseudo);
			pstmt.setString(2, motDePasse);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Utilisateur utilisateur = utilisateurBuilder(rs);
				return utilisateur;
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_ECHEC);
			throw businessException;
		}
		return null;
	}
	
	@Override
	public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Utilisateur utilisateur = utilisateurBuilder(rs);
				return utilisateur;
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_ECHEC);
			throw businessException;
		}
		return null;
	}
	
	private Utilisateur utilisateurBuilder(ResultSet rs) throws SQLException {
		Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
				rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur") );
		return utilisateur;
	}

	@Override
	public void removeUtilisateur(int idUtilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_LISTE);
			pstmt.setInt(1, idUtilisateur);
			pstmt.executeUpdate();
			pstmt.close();
			cnx.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_UTILISATEUR_ERREUR);
			throw businessException;
		}
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
//UPDATE encheres.utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=? WHERE no_utilisateur = ?";
			
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setInt(10, utilisateur.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			//businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_UTILISATEUR_ERREUR);
			throw businessException;
		}		
	}
}
