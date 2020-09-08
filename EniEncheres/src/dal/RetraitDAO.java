package dal;

import java.util.List;

import bo.Retrait;

public interface RetraitDAO {
	
	public List<Retrait> selectAll() throws BusinessException;

	public void remove(int idRetrait) throws BusinessException;

	public void ajouter(Retrait retrait) throws BusinessException;

	public Retrait selectByArticle(int idArticle) throws BusinessException;
}
