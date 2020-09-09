package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.ArticleManager;
import bll.CategoriesManager;
import bll.EnchereManager;
import bll.UtilisateurManager;
import bo.Article;
import bo.Categorie;
import bo.Enchere;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/ajoutArticle")
public class ServletAddArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CategoriesManager categoriesManager;
	
    public ServletAddArticle() {
        super();
    	this.categoriesManager = new CategoriesManager();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Categorie> categories = categoriesManager.selectionTout();
			request.setAttribute( "categories", categories);
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/nouvelleVente.jsp");
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
			EnchereManager enchereManager = new EnchereManager();
			Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("utilisateur");
			
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
			
			Article article = new Article(0, nom, description, new SimpleDateFormat("yyyy-MM-dd").parse(dateDebutEnchere), new SimpleDateFormat("yyyy-MM-dd").parse(dateFinEnchere)
					, prix, 0, utilisateur, categorie);
			Enchere enchere = new Enchere(utilisateur, article, new SimpleDateFormat("yyyy-MM-dd").parse(dateDebutEnchere), prix);

			article = articleManager.ajouter(article, utilisateur, categorie);
			enchereManager.ajouter(utilisateur, article, enchere);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accueil.jsp");
		rd.forward(request, response);
	}


}
