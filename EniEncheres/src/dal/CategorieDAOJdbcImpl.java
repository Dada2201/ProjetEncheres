package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Categorie;

class CategorieDAOJdbcImpl implements CategorieDAO {

	private static final String SELECT_ALL = "SELECT categories.no_categorie, categories.libelle FROM encheres.categories";
	private static final String SELECT_BY_ID = "SELECT categories.no_categorie, categories.libelle FROM encheres.categories WHERE categories.no_categorie = ?";

	@Override
	public List<Categorie> selectAll() throws BusinessException {
		List<Categorie> listeCategories = new ArrayList<Categorie>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Categorie categorie = categorieBuilder(rs);
				listeCategories.add(categorie);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_CATEGORIE_ECHEC);
			throw businessException;
		}
		return listeCategories;
	}

	@Override
	public Categorie selectById(int idArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setLong(1, idArticle);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Categorie categorie = categorieBuilder(rs);
				return categorie;
			}
			rs.close();
			pstmt.close();
			cnx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_CATEGORIE_ECHEC);
			throw businessException;
		}
		return null;
	}

	static Categorie categorieBuilder(ResultSet rs) throws SQLException, NumberFormatException, BusinessException {
		Categorie categorie = new Categorie(rs.getInt("categories.no_categorie"), rs.getString("categories.libelle"));
		return categorie;
	}
}
