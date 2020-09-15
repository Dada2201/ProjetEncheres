package dal;

import java.util.List;

import bo.Article;
import bo.Article.Statut;
import bo.Categorie;
import bo.Enchere;
import bo.Utilisateur;

public interface ArticleDAO {
	
	public List<Article> selectAll() throws BusinessException;

	public void remove(int idArticle) throws BusinessException;

	public Article ajouter(Article a, Utilisateur utilisateur, Categorie categorie) throws BusinessException;

	public Article selectById(int idArticle) throws BusinessException;

	public List<Article> selectionFiltre(List<Statut> arcticleStatut, Utilisateur utilisateur, Categorie categorie) throws BusinessException;

	public Article update(Article article, Utilisateur utilisateur, Categorie categorie) throws BusinessException;
}
