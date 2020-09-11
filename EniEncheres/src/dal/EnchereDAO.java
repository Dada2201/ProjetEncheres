package dal;

import java.util.List;

import bo.Article;
import bo.Enchere;
import bo.Enchere.Statut;
import bo.Utilisateur;

public interface EnchereDAO {

	public void insert(Utilisateur utilisateur, Article article, Enchere enchere) throws BusinessException;

	public List<Enchere> selectAll() throws BusinessException;

	public List<Enchere> selectByUtilisateur(int idEnchere) throws BusinessException;

	public List<Enchere> selectByArticle(int idArticle) throws BusinessException;

	public void remove(int idEnchere) throws BusinessException;

	public void update(Enchere enchere, Article article) throws BusinessException;

	public List<Article> selectionFiltre(List<Statut> encheresStatut, Utilisateur utilisateur) throws BusinessException;

	public Enchere selectByArticleUtilisateur(int idArticle, int idUtilisateur) throws BusinessException;
}
