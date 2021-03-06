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
import bo.Enchere;
import bo.Enchere.Statut;
import bo.Utilisateur;

class EnchereDAOJdbcImpl implements EnchereDAO {
	private static final String STRING_UTILISATEUR = "numero_utilisateur";
	private static final String STRING_CATEGORIE = "numero_categorie";

	private static final String SELECT_BY_UTILISATEUR = "SELECT sql_calc_found_rows encheres.no_utilisateur, encheres.no_article, encheres.date_enchere, encheres.montant_enchere, utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM encheres.encheres INNER JOIN utilisateurs ON encheres.no_utilisateur = utilisateurs.no_utilisateur WHERE utilisateurs.no_utilisateur = ?;";
	private static final String SELECT_BY_ARTICLE = "SELECT sql_calc_found_rows encheres.no_utilisateur, encheres.no_article, encheres.date_enchere, encheres.montant_enchere, utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM encheres.encheres INNER JOIN utilisateurs ON encheres.no_utilisateur = utilisateurs.no_utilisateur WHERE encheres.no_article = ? order by encheres.montant_enchere desc;";
	private static final String SELECT_BY_ARTICLE_UTILISATEUR = "SELECT sql_calc_found_rows categories.no_categorie, categories.libelle, encheres.no_utilisateur, encheres.no_article, encheres.date_enchere, encheres.montant_enchere, utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM encheres.encheres INNER JOIN utilisateurs ON encheres.no_utilisateur = utilisateurs.no_utilisateur inner join categories ON articles_vendus.no_categorie = categories.no_categorie WHERE no_article = ? AND no_utilisateur = ?;";
	private static final String SELECT_ALL = "SELECT sql_calc_found_rows encheres.no_utilisateur, encheres.no_article, encheres.date_enchere, encheres.montant_enchere, utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM encheres.encheres INNER JOIN utilisateurs ON encheres.no_utilisateur = utilisateurs.no_utilisateur";
	private static final String SELECT_ARTICLE = "SELECT sql_calc_found_rows articles_vendus.no_article, articles_vendus.nom_article, articles_vendus.description, articles_vendus.date_debut_encheres, articles_vendus.date_fin_encheres, prix_initial, articles_vendus.prix_vente, articles_vendus.no_utilisateur, articles_vendus.no_categorie, categories.no_categorie, categories.libelle, encheres.no_utilisateur, encheres.no_article, encheres.date_enchere, encheres.montant_enchere, utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM encheres.encheres INNER JOIN articles_vendus on encheres.no_article = articles_vendus.no_article INNER JOIN utilisateurs ON encheres.no_utilisateur = utilisateurs.no_utilisateur inner join categories ON articles_vendus.no_categorie = categories.no_categorie GROUP BY encheres.no_article LIMIT "
			+ Common.NB_ITEMS_PAGE + " OFFSET ?";
	private static final String SELECT_FILTRE = "SELECT sql_calc_found_rows categories.no_categorie, categories.libelle, encheres.no_utilisateur, encheres.no_article, encheres.date_enchere, MAX(encheres.montant_enchere) as 'encheres.montant_enchere', utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur, articles_vendus.no_article, articles_vendus.nom_article, articles_vendus.description, articles_vendus.date_debut_encheres, articles_vendus.date_fin_encheres, prix_initial, articles_vendus.prix_vente, articles_vendus.no_utilisateur, articles_vendus.no_categorie FROM encheres.encheres INNER JOIN utilisateurs ON encheres.no_utilisateur = utilisateurs.no_utilisateur INNER JOIN encheres.articles_vendus ON articles_vendus.no_article = encheres.no_article inner join categories ON articles_vendus.no_categorie = categories.no_categorie WHERE %s GROUP BY encheres.no_article LIMIT "
			+ Common.NB_ITEMS_PAGE + " OFFSET ?";

	private static final String INSERT = "INSERT INTO encheres.encheres(no_utilisateur,no_article,date_enchere,montant_enchere)VALUES(?,?,?,?);";
	private static final String REMOVE_ARTICLE = "DELETE from encheres.encheres where no_article=?";
	private static final String REMOVE_UTILISATEUR = "DELETE from encheres.encheres where no_utilisateur=?";
	private static final String UPDATE = "UPDATE encheres.encheres SET no_utilisateur = ?, date_enchere = ?, montant_enchere = ? WHERE no_article = ?";

	private static final String FILTER_EN_COURS_UTILISATEUR = String.format(
			"(encheres.no_utilisateur = %s AND '%s' BETWEEN articles_vendus.date_debut_encheres AND articles_vendus.date_fin_encheres)",
			STRING_UTILISATEUR, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_EN_COURS = String.format(
			"('%s' BETWEEN articles_vendus.date_debut_encheres AND articles_vendus.date_fin_encheres)",
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_WIN = String.format(
			"(encheres.no_utilisateur = %s AND DATEDIFF('%s' , articles_vendus.date_fin_encheres) >0 )",
			STRING_UTILISATEUR, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	private static final String FILTER_CATEGORIE = String.format("(categories.no_categorie = %s)", STRING_CATEGORIE);
	private static final String SELECT_ROWS = "select found_rows() as nbRows;";

	@Override
	public void insert(Utilisateur utilisateur, Article article, Enchere enchere) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(INSERT);
			pstmt.setInt(1, utilisateur.getId());
			pstmt.setInt(2, article.getNoArticle());
			pstmt.setDate(3, new java.sql.Date(enchere.getDateEnchere().getTime()));
			pstmt.setInt(4, enchere.getMontantEnchere());
			pstmt.executeUpdate();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
	}

	@Override
	public List<Enchere> selectAll() throws BusinessException {
		List<Enchere> listeEnchere = new ArrayList<Enchere>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = enchereBuilder(rs);
				listeEnchere.add(enchere);
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
		return listeEnchere;
	}

	@Override
	public List<Enchere> selectByUtilisateur(int id) throws BusinessException {
		List<Enchere> listeEnchere = new ArrayList<Enchere>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_UTILISATEUR);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = enchereBuilder(rs);
				listeEnchere.add(enchere);
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
		return listeEnchere;
	}

	@Override
	public Enchere selectByArticleUtilisateur(int idArticle, int idUtilisateur) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ARTICLE_UTILISATEUR);
			pstmt.setInt(1, idArticle);
			pstmt.setInt(2, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = enchereBuilder(rs);
				return enchere;
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
		return null;
	}

	@Override
	public List<Enchere> selectByArticle(int id) throws BusinessException {
		List<Enchere> listeEnchere = new ArrayList<Enchere>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ARTICLE);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Enchere enchere = enchereBuilder(rs);
				listeEnchere.add(enchere);
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
		return listeEnchere;
	}

	Enchere enchereBuilder(ResultSet rs) throws BusinessException, NumberFormatException, SQLException {
		Enchere enchere = new Enchere(UtilisateurDAOJdbcImpl.utilisateurBuilder(rs),
				rs.getDate("encheres.date_enchere"), rs.getInt("encheres.montant_enchere"));
		return enchere;
	}

	@Override
	public void remove(int id) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(REMOVE_ARTICLE);
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
	public void removeUtilisateur(int id) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(REMOVE_UTILISATEUR);
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
	public void update(Enchere enchere, Article article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);

			pstmt.setLong(1, enchere.getUtilisateur().getId());
			pstmt.setDate(2, new java.sql.Date(enchere.getDateEnchere().getTime()));
			pstmt.setLong(3, enchere.getMontantEnchere());
			pstmt.setLong(4, article.getNoArticle());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_ENCHERE_ERREUR);
			throw businessException;
		}
	}

	@Override
	public List<Article> selectionFiltre(List<Statut> encheresStatut, Categorie categorie, Utilisateur utilisateur,
			int page) throws BusinessException {
		List<Article> listeArticle = new ArrayList<Article>();
		String filter = "";
		if (encheresStatut != null) {
			for (int i = 0; i < encheresStatut.size(); i++) {
				switch (encheresStatut.get(i)) {
				case EN_COURS_UTILISATEUR:
					filter += EnchereDAOJdbcImpl.FILTER_EN_COURS_UTILISATEUR.replace(STRING_UTILISATEUR,
							utilisateur.getId().toString());
					break;
				case EN_COURS:
					filter += EnchereDAOJdbcImpl.FILTER_EN_COURS;
					break;
				case WIN:
					filter += EnchereDAOJdbcImpl.FILTER_WIN.replace(STRING_UTILISATEUR, utilisateur.getId().toString());
					break;
				default:
					break;
				}
				if (encheresStatut.size() != (i + 1) && encheresStatut.size() != 1) {
					filter += " OR ";
				}
			}
		}

		if (filter.equals("") && categorie != null) {
			filter = FILTER_CATEGORIE.replace(STRING_CATEGORIE, String.valueOf(categorie.getNoCategorie()));
		} else if (!filter.equals("") && categorie != null) {
			filter += " OR " + FILTER_CATEGORIE.replace(STRING_CATEGORIE, String.valueOf(categorie.getNoCategorie()));
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(String.format(SELECT_FILTRE, filter));
			pstmt.setLong(1, page * Common.NB_ITEMS_PAGE);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Article article = ArticleDAOJdbcImpl.articleBuilder(rs);
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
	public List<Article> selectArticles(int page) throws BusinessException {
		List<Article> listeArticle = new ArrayList<Article>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE);
			pstmt.setLong(1, page * Common.NB_ITEMS_PAGE);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Article article = ArticleDAOJdbcImpl.articleBuilder(rs);
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
