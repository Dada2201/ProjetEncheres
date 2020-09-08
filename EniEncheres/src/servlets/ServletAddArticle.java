package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.UtilisateurManager;
import bo.Article;
import bo.Categorie;
import bo.Utilisateur;
import dal.BusinessException;

public class ServletAddArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletAddArticle() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
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
			UtilisateurManager utilisateurManager = new UtilisateurManager();
		
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
						
			Categorie categorie = new Categorie(0, "test");
			
			//Article article = new Article(0, nom, description, (Date)dateDebutEnchere, (Date)dateFinEnchere, prix, 0, (Utilisateur)request.getSession("utilisateur"), categorie)
		}
		//catch (BusinessException e) {
			//request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		/*}*/ catch (Exception e) {
			System.err.println(e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil.jsp");
		rd.forward(request, response);
	}


}
