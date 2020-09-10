package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bll.ArticleManager;
import bll.UtilisateurManager;
import bo.Article;
import bo.Enchere;
import bo.Enchere.Statut;
import bo.Utilisateur;

class EnchereDAOJdbcImpl implements EnchereDAO {
	private static final String STRING_UTILISATEUR = "numero_utilisateur";
	private static final String INSERT="INSERT INTO encheres.encheres(no_utilisateur,no_article,date_enchere,montant_enchere)VALUES(?,?,?,?);";
	private static final String SELECT_BY_UTILISATEUR="SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM encheres.encheres WHERE no_utilisateur = ?;";
	private static final String SELECT_BY_ARTICLE="SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM encheres.encheres WHERE no_article = ?;";
	private static final String REMOVE = "DELETE from LISTES where id=?";
	private static final String SELECT_ALL="SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM encheres.encheres;";
	private static final String SELECT_FILTRE="SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM encheres.encheres INNER JOIN WHERE ?;";
	private static final String UPDATE="UPDATE encheres.encheres SET no_utilisateur = ?, date_enchere = ?, montant_enchere = ? WHERE no_article = ?";

	private static final String FILTER_OPEN= String.format("(%s BETWEEN articles_vendus.date_debut_encheres AND articles_vendus.date_fin_encheres)", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_EN_COURS=String.format("(no_utilisateur = %s AND %s BETWEEN articles_vendus.date_debut_encheres AND articles_vendus.date_fin_encheres)", STRING_UTILISATEUR, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_WIN=String.format("(no_utilisateur = %s AND %s > articles_vendus.date_fin_encheres)", STRING_UTILISATEUR, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	
	@Override
	public void insert(Utilisateur utilisateur, Article article, Enchere enchere) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(INSERT);
			pstmt.setInt(1, utilisateur.getId());
			pstmt.setInt(2, article.getNoArticle());
			pstmt.setDate(3, new java.sql.Date(enchere.getDateEnchere().getTime()));
			pstmt.setInt(4, enchere.getMontantEnchere());
			pstmt.executeUpdate();
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
	}
	
	@Override
	public List<Enchere> selectAll() throws BusinessException {
		List<Enchere> listeEnchere= new ArrayList<Enchere>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Enchere enchere = enchereBuilder(rs);
				listeEnchere.add(enchere);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_ECHEC);
			throw businessException;
		}
		return listeEnchere;
	}
	
	@Override
	public List<Enchere> selectByUtilisateur(int id) throws BusinessException {
		List<Enchere> listeEnchere= new ArrayList<Enchere>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_UTILISATEUR);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Enchere enchere = enchereBuilder(rs);
				listeEnchere.add(enchere);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_ECHEC);
			throw businessException;
		}
		return listeEnchere;
	}
	
	@Override
	public Enchere selectByArticle(int id) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ARTICLE);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Enchere enchere = enchereBuilder(rs);
				return enchere;
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_ECHEC);
			throw businessException;
		}
		return null;
	}
	
	public static Enchere enchereBuilder(ResultSet rs) throws BusinessException, NumberFormatException, SQLException {
		ArticleManager articleManager = new ArticleManager();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Enchere enchere = new Enchere(utilisateurManager.selectionParId(Integer.parseInt(rs.getString("no_utilisateur"))), articleManager.selectById(Integer.parseInt(rs.getString("no_article")))
				, rs.getDate("date_enchere"), rs.getInt("montant_enchere"));
		return enchere;
	}

	@Override
	public void remove(int id) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(REMOVE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			cnx.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ENCHERE_ERREUR);
			throw businessException;
		}
	}

	@Override
	public void update(Enchere enchere) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			
			pstmt.setLong(1, enchere.getUtilisateur().getId());
			pstmt.setDate(2, new java.sql.Date(enchere.getDateEnchere().getTime()));
			pstmt.setLong(3, enchere.getMontantEnchere());
			pstmt.setLong(4, enchere.getArticle().getNoArticle());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_ENCHERE_ERREUR);
			throw businessException;
		}		
	}

	@Override
	public List<Enchere> selectionFiltre(List<Statut> encheresStatut, Utilisateur utilisateur) throws BusinessException {
		List<Enchere> listeEnchere= new ArrayList<Enchere>();
		String filter = null;
		
		for (int i = 0; i < encheresStatut.size(); i++) {
			switch (encheresStatut.get(i)) {
			case OPEN:
				filter += EnchereDAOJdbcImpl.FILTER_OPEN;
				break;
			case EN_COURS:
				filter += EnchereDAOJdbcImpl.FILTER_EN_COURS.replace(STRING_UTILISATEUR, utilisateur.getId().toString());
				break;
			case WIN:
				filter += EnchereDAOJdbcImpl.FILTER_WIN.replace(STRING_UTILISATEUR, utilisateur.getId().toString());
				break;
			default:
				break;
			}
			
			if(encheresStatut.size() != i && encheresStatut.size()!=1) {
				filter +=" OR ";
			}
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_FILTRE);
			pstmt.setString(1, filter);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Enchere enchere = enchereBuilder(rs);
				listeEnchere.add(enchere);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_ECHEC);
			throw businessException;
		}
		return listeEnchere;
	}
}
