package servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bll.ArticleManager;
import bll.CategoriesManager;
import bll.RetraitManager;
import bo.Article;
import bo.Categorie;
import bo.Common;
import bo.Retrait;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/ajoutArticle")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5,
maxRequestSize = 1024 * 1024 * 5 * 5)
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
		
		request.setAttribute("title", "Ajout d'un article");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/nouvelleVente.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String nom, description, categorieSelected, dateDebutEnchere, dateFinEnchere, rue, codePostal, ville;
		int prix;
		try
		{			

			CategoriesManager categorieManager = new CategoriesManager();
			ArticleManager articleManager = new ArticleManager();
			RetraitManager retraitManager = new RetraitManager();
			Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME);
			
			nom = request.getParameter("nom");
			description = request.getParameter("description");
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
			Retrait retrait = new Retrait(article, rue, codePostal, ville);

			article = articleManager.ajouter(article, utilisateur, categorie);

	        Part part = request.getPart("photoArticle");
	        String nomFichier = Common.getNomFichier(part);

	        if (nomFichier != null && !nomFichier.isEmpty()) {
	             nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
	                    .substring(nomFichier.lastIndexOf('\\') + 1);

	            Common.ecrireFichier(part, article.getNoArticle()+".png", getServletContext().getRealPath("/")+"resources\\img\\articles\\");
	        }
			
			retraitManager.ajouter(retrait);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		response.sendRedirect(request.getContextPath());
	}
}
