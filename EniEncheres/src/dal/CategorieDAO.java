package dal;

import java.util.List;

import bo.Categorie;

public interface CategorieDAO {
	
	public List<Categorie> selectAll() throws BusinessException;

	public Categorie selectById(int idCategorie) throws BusinessException;
}
