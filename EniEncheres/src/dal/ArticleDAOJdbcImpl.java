package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bll.UtilisateurManager;
import bo.Article;
import bo.Categorie;
import bo.Utilisateur;

class ArticleDAOJdbcImpl implements ArticleDAO {

	private static final String SELECT_ALL="SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM encheres.articles_vendus";
	private static final String SELECT_BY_ID="SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM encheres.articles_vendus WHERE no_article = ?";
	private static final String REMOVE = "DELETE FROM encheres.articles_vendus WHERE no_article = ?";
	private static final String INSERT = "INSERT INTO encheres.articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie)VALUES(?,?,?,?,?,?,?,?);";

	@Override
	public List<Article> selectAll() throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Article article = articleBuilder(rs);
				listeArticles.add(article);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_ECHEC);
			throw businessException;
		}
		return listeArticles;
	}
	
	@Override
	public Article selectById(int idArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setLong(1, idArticle);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Article article = articleBuilder(rs);
				return article;
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_ECHEC);
			throw businessException;
		}
		return null;
	}

	@Override
	public void remove(int idArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(REMOVE);
			pstmt.setLong(1, idArticle);
			pstmt.executeUpdate();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ARTICLE_ERREUR);
			throw businessException;
		}
	}
	
	private Article articleBuilder(ResultSet rs) throws SQLException, NumberFormatException, BusinessException {
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		//TODO AJOUTER LE MANAGER CATEGORIES
		Article article = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), 
				rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), utilisateurManager.selectionParId(Integer.parseInt(rs.getString("no_utilisateur"))), new Categorie(1, "cat"));
		return article;
	}

	@Override
	public void ajouter(Article a, Utilisateur utilisateur, Categorie categorie) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(INSERT);
			pstmt.setString(1, a.getNomArticle());
			pstmt.setString(2, a.getDescription());
			pstmt.setDate(3, (java.sql.Date) a.getDateDebut());
			pstmt.setDate(4, (java.sql.Date) a.getDateFin());
			pstmt.setDouble(5, a.getPrixInitial());
			pstmt.setDouble(6, a.getPrixVente());
			pstmt.setDouble(7, utilisateur.getId());
			pstmt.setDouble(8, categorie.getNoCategorie());
			pstmt.executeUpdate();
			pstmt.close();
			cnx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.AJOUTER_ARTICLE_ECHEC);
			throw businessException;
		}
	}
}
