package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bll.CategoriesManager;
import bll.UtilisateurManager;
import bo.Article;
import bo.Categorie;
import bo.Enchere;
import bo.Utilisateur;
import bo.Enchere.Statut;

class ArticleDAOJdbcImpl implements ArticleDAO {

	private static final String SELECT_ALL="SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM encheres.articles_vendus";
	private static final String SELECT_BY_ID="SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM encheres.articles_vendus WHERE no_article = ?";
	private static final String REMOVE = "DELETE FROM encheres.articles_vendus WHERE no_article = ?";
	private static final String INSERT = "INSERT INTO encheres.articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie)VALUES(?,?,?,?,?,?,?,?);";
	private static final String SELECT_FILTRE="SELECT encheres.no_utilisateur, encheres.no_article, encheres.date_enchere, encheres.montant_enchere FROM encheres.encheres INNER JOIN encheres.articles_vendus ON articles_vendus.no_article = encheres.no_article WHERE articles_vendus.no_utilisateur = ? AND (%s)";

	private static final String FILTER_EN_COURS = String.format("('%s' BETWEEN articles_vendus.date_debut_encheres AND articles_vendus.date_fin_encheres)", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_NOT_READY = String.format("(DATEDIFF('%s' , articles_vendus.date_debut_encheres) < 0)", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_CLOSE = String.format("(DATEDIFF('%s', articles_vendus.date_fin_encheres) > 0)", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

	
	@Override
	public List<Article> selectAll() throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
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
			cnx.setAutoCommit(false);
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
			cnx.setAutoCommit(false);
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
		CategoriesManager categoriesManager = new CategoriesManager();
		Article article = new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), 
				rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), 
				utilisateurManager.selectionParId(Integer.parseInt(rs.getString("no_utilisateur"))), categoriesManager.selectionById(Integer.parseInt(rs.getString("no_categorie"))));
		return article;
	}

	@Override
	public Article ajouter(Article a, Utilisateur utilisateur, Categorie categorie) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, a.getNomArticle());
			pstmt.setString(2, a.getDescription());
			pstmt.setDate(3, new java.sql.Date(a.getDateDebut().getTime()));
			pstmt.setDate(4, new java.sql.Date(a.getDateFin().getTime()));
			pstmt.setDouble(5, a.getPrixInitial());
			pstmt.setDouble(6, a.getPrixVente());
			pstmt.setDouble(7, utilisateur.getId());
			pstmt.setDouble(8, categorie.getNoCategorie());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				a.setNoArticle(rs.getInt(1));
			}
			rs.close();
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
		
		return a;
	}	
	
	@Override
	public List<Enchere> selectionFiltre(List<Article.Statut> arcticleStatut, Utilisateur utilisateur) throws BusinessException {
		List<Enchere> listeEnchere= new ArrayList<Enchere>();
		String filter = "";
		
		for (int i = 0; i < arcticleStatut.size(); i++) {
			switch (arcticleStatut.get(i)) {
			case EN_COURS:
				filter += FILTER_EN_COURS;
				break;
			case NOT_READY:
				filter += FILTER_NOT_READY;
				break;
			case CLOSE:
				filter += FILTER_CLOSE;
				break;
			default:
				break;
			}
			if(arcticleStatut.size() != (i+1) && arcticleStatut.size()!=1) {
				filter +=" OR ";
			}
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(String.format(SELECT_FILTRE, filter));
			pstmt.setLong(1, utilisateur.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Enchere enchere = EnchereDAOJdbcImpl.enchereBuilder(rs);
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
