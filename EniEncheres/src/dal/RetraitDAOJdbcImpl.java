package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bll.ArticleManager;
import bo.Retrait;

class RetraitDAOJdbcImpl implements RetraitDAO {

    private static final String SELECT_ALL="SELECT no_article, rue, code_postal, ville FROM encheres.retraits";
    private static final String SELECT_BY_ID="SELECT no_article, rue, code_postal, ville FROM encheres.retraits WHERE no_article = ?";
    private static final String REMOVE = "DELETE FROM encheres.retraits WHERE no_article = ?";
    private static final String INSERT = "INSERT INTO encheres.retraits (no_article,rue,code_postal,ville)VALUES(?,?,?,?);";

    @Override
    public List<Retrait> selectAll() throws BusinessException {
        List<Retrait> listeRetraits = new ArrayList<Retrait>();
        try(Connection cnx = ConnectionProvider.getConnection())
        {
			cnx.setAutoCommit(false);
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Retrait retrait = retraitBuilder(rs);
                listeRetraits.add(retrait);
            }
            rs.close();
            pstmt.close();
            cnx.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_RETRAIT_ECHEC);
            throw businessException;
        }
        return listeRetraits;
    }
    
    @Override
    public Retrait selectByArticle(int idArticle) throws BusinessException {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
			cnx.setAutoCommit(false);
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
            pstmt.setLong(1, idArticle);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Retrait retrait = retraitBuilder(rs);
                return retrait;
            }
            rs.close();
            pstmt.close();
            cnx.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_RETRAIT_ECHEC);
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
            businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_RETRAIT_ERREUR);
            throw businessException;
        }
    }
    
    private Retrait retraitBuilder(ResultSet rs) throws SQLException, NumberFormatException, BusinessException {
        ArticleManager articleManager = new ArticleManager();
        Retrait retrait = new Retrait(articleManager.selectById(Integer.parseInt(rs.getString("no_article"))), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
        return retrait;
    }

    @Override
    public void ajouter(Retrait r) throws BusinessException {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
			cnx.setAutoCommit(false);
            PreparedStatement pstmt = cnx.prepareStatement(INSERT);
            pstmt.setInt(1, r.getArticle().getNoArticle());
            pstmt.setString(2, r.getRue());
            pstmt.setString(3, r.getCodePostal());
            pstmt.setString(4, r.getVille());
            pstmt.executeUpdate();
            pstmt.close();
            cnx.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.AJOUTER_RETRAIT_ECHEC);
            throw businessException;
        }
    }
}
