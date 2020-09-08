package dal;

import java.util.List;

import bo.Article;
import bo.Categorie;
import bo.Enchere;
import bo.Utilisateur;

public interface CategorieDAO {
	
	public List<Categorie> selectAll() throws BusinessException;

	public Categorie selectById(int idCategorie) throws BusinessException;
}
