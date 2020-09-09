package dal;

import java.util.List;

import bo.Article;
import bo.Enchere;
import bo.Utilisateur;

public interface EnchereDAO {

	public void insert(Utilisateur utilisateur, Article article, Enchere enchere) throws BusinessException;

	public List<Enchere> selectAll() throws BusinessException;

	public List<Enchere> selectByUtilisateur(int idEnchere) throws BusinessException;

	public Enchere selectByArticle(int idArticle) throws BusinessException;

	public void remove(int idEnchere) throws BusinessException;

	public void update(Enchere enchere) throws BusinessException;
}
