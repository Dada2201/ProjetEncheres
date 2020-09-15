package dal;

import java.util.List;

import bo.Article;
import bo.Categorie;
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

	public List<Article> selectionFiltre(List<Statut> encheresStatut, Categorie categorieFiltre, Utilisateur utilisateur, int page) throws BusinessException;

	public Enchere selectByArticleUtilisateur(int idArticle, int idUtilisateur) throws BusinessException;

	public List<Article> selectArticles(int page) throws BusinessException;

	public int foundRows() throws BusinessException;
}
