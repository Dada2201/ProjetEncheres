package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.ArticleManager;
import bll.CategoriesManager;
import bll.RetraitManager;
import bo.Article;
import bo.Categorie;
import bo.Common;
import bo.Retrait;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/editArticle")
public class ServletEditArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Article article = null;
		
    public ServletEditArticle() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			CategoriesManager categoriesManager = new CategoriesManager();
			ArticleManager articleManager = new ArticleManager();
			RetraitManager retraitManager = new RetraitManager();
			
			this.article = articleManager.selectById(Integer.parseInt(request.getParameter("enchere")));

			List<Categorie> categories = categoriesManager.selectionTout();
			Retrait retrait = retraitManager.selectById(this.article.getNoArticle());

			request.setAttribute("article", this.article);
			request.setAttribute("categories", categories);
			request.setAttribute("retrait", retrait);			
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		request.setAttribute("title", "Modifier un article");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/editVente.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String nom, description, categorieSelected, photoArticle, dateDebutEnchere, dateFinEnchere, rue, codePostal, ville;
		int prix;
		try
		{			

			CategoriesManager categorieManager = new CategoriesManager();
			ArticleManager articleManager = new ArticleManager();
			RetraitManager retraitManager = new RetraitManager();
			Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME);
			
			nom = request.getParameter("nom");
			description = request.getParameter("description");
			photoArticle = request.getParameter("photoArticle");
			dateDebutEnchere = request.getParameter("dateDebutEnchere");
			dateFinEnchere = request.getParameter("dateFinEnchere");
			rue = request.getParameter("rue");
			codePostal = request.getParameter("codePostal");
			ville = request.getParameter("ville");
			prix = Integer.parseInt(request.getParameter("prix"));
			categorieSelected = request.getParameter("categorie");
			
			
			Categorie categorie = categorieManager.selectionById(Integer.parseInt(categorieSelected));
			

			this.article.setNomArticle(nom);
			this.article.setDescription(description);
			this.article.setDateDebut(new SimpleDateFormat("yyyy-MM-dd").parse(dateDebutEnchere));
			this.article.setDateFin(new SimpleDateFormat("yyyy-MM-dd").parse(dateFinEnchere));
			this.article.setPrixInitial(prix);
			this.article.setCategorie(categorie);

			Retrait retrait = new Retrait(article, rue, codePostal, ville);

			articleManager.update(article, utilisateur, categorie);
			retraitManager.update(retrait);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}

		System.out.println(this.article);
		response.sendRedirect("enchere?enchere="+this.article.getNoArticle());
	}


}
