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

    private static final String SELECT_ALL="SELECT retraits.no_article, retraits.rue, retraits.code_postal, retraits.ville, articles_vendus.no_article , articles_vendus.nom_article , articles_vendus.description , articles_vendus.date_debut_encheres , articles_vendus.date_fin_encheres , articles_vendus.prix_initial , articles_vendus.prix_vente , articles_vendus.no_utilisateur , articles_vendus.no_categorie , categories.libelle , categories.no_categorie , utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM articles_vendus inner join categories ON articles_vendus.no_categorie = categories.no_categorie inner join utilisateurs ON articles_vendus.no_utilisateur = utilisateurs.no_utilisateur inner join retraits on retraits.no_article = articles_vendus.no_article;";
    private static final String SELECT_BY_ID="SELECT retraits.no_article, retraits.rue, retraits.code_postal, retraits.ville, articles_vendus.no_article , articles_vendus.nom_article , articles_vendus.description , articles_vendus.date_debut_encheres , articles_vendus.date_fin_encheres , articles_vendus.prix_initial , articles_vendus.prix_vente , articles_vendus.no_utilisateur , articles_vendus.no_categorie , categories.libelle , categories.no_categorie , utilisateurs.no_utilisateur , utilisateurs.pseudo , utilisateurs.nom , utilisateurs.prenom , utilisateurs.email , utilisateurs.telephone , utilisateurs.rue , utilisateurs.code_postal , utilisateurs.ville , utilisateurs.mot_de_passe , utilisateurs.credit , utilisateurs.administrateur FROM articles_vendus inner join categories ON articles_vendus.no_categorie = categories.no_categorie inner join utilisateurs ON articles_vendus.no_utilisateur = utilisateurs.no_utilisateur inner join retraits on retraits.no_article = articles_vendus.no_article WHERE articles_vendus.no_article = ?";
    private static final String REMOVE = "DELETE FROM encheres.retraits WHERE no_article = ?";
    private static final String INSERT = "INSERT INTO encheres.retraits (no_article,rue,code_postal,ville)VALUES(?,?,?,?);";
    private static final String UPDATE = "UPDATE encheres.retraits SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?; ";

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
        Retrait retrait = new Retrait(ArticleDAOJdbcImpl.articleBuilder(rs), rs.getString("retraits.rue"), rs.getString("retraits.code_postal"), rs.getString("retraits.ville"));
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

	@Override
	public void update(Retrait retrait) throws BusinessException {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
			cnx.setAutoCommit(false);
            PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
            pstmt.setString(1, retrait.getRue());
            pstmt.setString(2, retrait.getCodePostal());
            pstmt.setString(3, retrait.getVille());
            pstmt.setLong(4, retrait.getArticle().getNoArticle());
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
