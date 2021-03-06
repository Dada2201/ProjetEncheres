package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bo.Article;
import bo.Categorie;
import bo.Common;
import bo.Utilisateur;

class ArticleDAOJdbcImpl implements ArticleDAO {
	private static final String STRING_UTILISATEUR = "numero_utilisateur";
	private static final String STRING_CATEGORIE = "numero_categorie";
	private static final String STRING_SEARCH = "search";

	private static final String SELECT_ALL = "SELECT sql_calc_found_rows  articles_vendus.no_article , articles_vendus.nom_article , articles_vendus.description , articles_vendus.date_debut_encheres , articles_vendus.date_fin_encheres , articles_vendus.prix_initial , articles_vendus.prix_vente , articles_vendus.no_utilisateur , articles_vendus.no_categorie , categories.libelle , categories.no_categorie , utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM articles_vendus inner join categories ON articles_vendus.no_categorie = categories.no_categorie inner join utilisateurs ON articles_vendus.no_utilisateur = utilisateurs.no_utilisateur LIMIT "
			+ Common.NB_ITEMS_PAGE + " OFFSET ?";
	private static final String SELECT_BY_ID = "SELECT sql_calc_found_rows  articles_vendus.no_article , articles_vendus.nom_article , articles_vendus.description , articles_vendus.date_debut_encheres , articles_vendus.date_fin_encheres , articles_vendus.prix_initial , articles_vendus.prix_vente , articles_vendus.no_utilisateur , articles_vendus.no_categorie , categories.libelle , categories.no_categorie , utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM articles_vendus inner join categories ON articles_vendus.no_categorie = categories.no_categorie inner join utilisateurs ON articles_vendus.no_utilisateur = utilisateurs.no_utilisateur WHERE articles_vendus.no_article = ?";
	private static final String SELECT_BY_UTILISATEUR = "SELECT sql_calc_found_rows  articles_vendus.no_article , articles_vendus.nom_article , articles_vendus.description , articles_vendus.date_debut_encheres , articles_vendus.date_fin_encheres , articles_vendus.prix_initial , articles_vendus.prix_vente , articles_vendus.no_utilisateur , articles_vendus.no_categorie , categories.libelle , categories.no_categorie , utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM articles_vendus inner join categories ON articles_vendus.no_categorie = categories.no_categorie inner join utilisateurs ON articles_vendus.no_utilisateur = utilisateurs.no_utilisateur WHERE utilisateurs.no_utilisateur = ?";
	private static final String SELECT_FILTRE = "SELECT sql_calc_found_rows  articles_vendus.no_article , articles_vendus.nom_article , articles_vendus.description , articles_vendus.date_debut_encheres , articles_vendus.date_fin_encheres , articles_vendus.prix_initial , articles_vendus.prix_vente , articles_vendus.no_utilisateur , articles_vendus.no_categorie , categories.libelle , categories.no_categorie , utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM articles_vendus inner join categories ON articles_vendus.no_categorie = categories.no_categorie inner join utilisateurs ON articles_vendus.no_utilisateur = utilisateurs.no_utilisateur WHERE (%s) LIMIT ? OFFSET ?";
	private static final String SELECT_ROWS = "select found_rows() as nbRows;";

	private static final String REMOVE = "DELETE FROM encheres.articles_vendus WHERE no_article = ?";
	private static final String INSERT = "INSERT INTO encheres.articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie)VALUES(?,?,?,?,?,?,?,?);";
	private static final String UPDATE = "UPDATE encheres.articles_vendus SET articles_vendus.nom_article = ?, articles_vendus.description = ?, articles_vendus.date_debut_encheres = ?, articles_vendus.date_fin_encheres = ?, articles_vendus.prix_initial = ?, articles_vendus.prix_vente = ?, articles_vendus.no_categorie = ? WHERE no_article = ?; ";

	private static final String FILTER_EN_COURS = String.format(
			"(articles_vendus.no_utilisateur = '%s' AND '%s' BETWEEN articles_vendus.date_debut_encheres AND articles_vendus.date_fin_encheres)",
			STRING_UTILISATEUR, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_NOT_READY = String.format(
			"(articles_vendus.no_utilisateur = '%s' AND DATEDIFF('%s' , articles_vendus.date_debut_encheres) < 0)",
			STRING_UTILISATEUR, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_CLOSE = String.format(
			"(articles_vendus.no_utilisateur = '%s' AND DATEDIFF('%s', articles_vendus.date_fin_encheres) > 0)",
			STRING_UTILISATEUR, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_CATEGORIE = String.format(
			"articles_vendus.no_utilisateur = '%s' AND (categories.no_categorie = %s)", STRING_UTILISATEUR,
			STRING_CATEGORIE);
	private static final String FILTER_OPEN = String.format(
			"('%s' BETWEEN articles_vendus.date_debut_encheres AND articles_vendus.date_fin_encheres)",
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

	private static final String FILTER_SEARCH = String.format(
			"(articles_vendus.nom_article LIKE '%%%s%%')", STRING_SEARCH);

	@Override
	public List<Article> selectAll() throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Article article = articleBuilder(rs);
				listeArticles.add(article);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_ECHEC);
			throw businessException;
		}
		return listeArticles;
	}

	@Override
	public Article selectById(int idArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setLong(1, idArticle);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Article article = articleBuilder(rs);
				return article;
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_ECHEC);
			throw businessException;
		}
		return null;
	}

	@Override
	public List<Article> selectByUser(int idUser) throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_UTILISATEUR);
			pstmt.setLong(1, idUser);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Article article = articleBuilder(rs);
				listeArticles.add(article);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_ECHEC);
			throw businessException;
		}
		return listeArticles;
	}

	@Override
	public void remove(int idArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(REMOVE);
			pstmt.setLong(1, idArticle);
			pstmt.executeUpdate();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ARTICLE_ERREUR);
			throw businessException;
		}
	}

	static Article articleBuilder(ResultSet rs) throws SQLException, NumberFormatException, BusinessException {
		Article article = new Article(rs.getInt("articles_vendus.no_article"),
				rs.getString("articles_vendus.nom_article"), rs.getString("articles_vendus.description"),
				rs.getDate("articles_vendus.date_debut_encheres"), rs.getDate("articles_vendus.date_fin_encheres"),
				rs.getInt("articles_vendus.prix_initial"), rs.getInt("articles_vendus.prix_vente"),
				UtilisateurDAOJdbcImpl.utilisateurBuilder(rs), CategorieDAOJdbcImpl.categorieBuilder(rs));
		return article;
	}

	@Override
	public Article ajouter(Article a, Utilisateur utilisateur, Categorie categorie) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
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
			if (rs.next()) {
				a.setNoArticle(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.AJOUTER_ARTICLE_ECHEC);
			throw businessException;
		}

		return a;
	}

	@Override
	public Article update(Article a, Utilisateur utilisateur, Categorie categorie) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, a.getNomArticle());
			pstmt.setString(2, a.getDescription());
			pstmt.setDate(3, new java.sql.Date(a.getDateDebut().getTime()));
			pstmt.setDate(4, new java.sql.Date(a.getDateFin().getTime()));
			pstmt.setDouble(5, a.getPrixInitial());
			pstmt.setDouble(6, a.getPrixVente());
			pstmt.setDouble(7, categorie.getNoCategorie());
			pstmt.setDouble(8, a.getNoArticle());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				a.setNoArticle(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.AJOUTER_ARTICLE_ECHEC);
			throw businessException;
		}

		return a;
	}

	@Override
	public List<Article> selectionFiltre(List<Article.Statut> arcticleStatut, Categorie categorie,
			Utilisateur utilisateur, String search, int page, int nbItems) throws BusinessException {
		List<Article> listeArticle = new ArrayList<Article>();
		String filter = "";
		if (arcticleStatut != null) {
			for (int i = 0; i < arcticleStatut.size(); i++) {
				switch (arcticleStatut.get(i)) {
				case OPEN:
					filter += FILTER_OPEN;
					break;
				case EN_COURS:
					filter += FILTER_EN_COURS.replace(STRING_UTILISATEUR, String.valueOf(utilisateur.getId()));
					break;
				case NOT_READY:
					filter += FILTER_NOT_READY.replace(STRING_UTILISATEUR, String.valueOf(utilisateur.getId()));
					break;
				case CLOSE:
					filter += FILTER_CLOSE.replace(STRING_UTILISATEUR, String.valueOf(utilisateur.getId()));
					break;
				default:
					break;
				}
				if (arcticleStatut.size() != (i + 1) && arcticleStatut.size() != 1) {
					filter += " OR ";
				}
			}
		}

		if (filter.equals("") && categorie != null) {
			filter = FILTER_CATEGORIE.replace(STRING_CATEGORIE, String.valueOf(categorie.getNoCategorie()));
		} else if (!filter.equals("") && categorie != null) {
			filter += " OR " + FILTER_CATEGORIE.replace(STRING_CATEGORIE, String.valueOf(categorie.getNoCategorie())).replace(STRING_UTILISATEUR, String.valueOf(utilisateur.getId()));
		}

		if (filter.equals("") && search != null) {
			filter = FILTER_SEARCH.replace(STRING_SEARCH, search);
		} else if (!filter.equals("") && search != null) {
			filter += " OR " + FILTER_SEARCH.replace(STRING_SEARCH, search);
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(String.format(SELECT_FILTRE, filter));
			pstmt.setLong(1, nbItems);
			pstmt.setLong(2, page * Common.NB_ITEMS_PAGE);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Article article = articleBuilder(rs);
				listeArticle.add(article);
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_ECHEC);
			throw businessException;
		}
		return listeArticle;
	}

	@Override
	public int foundRows() throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ROWS);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getInt("nbRows");
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_ECHEC);
			throw businessException;
		}
		return 0;
	}
}
