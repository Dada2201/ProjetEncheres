package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.ArticleManager;
import bll.EnchereManager;
import bll.RetraitManager;
import bll.UtilisateurManager;
import bo.Article;
import bo.Common;
import bo.Enchere;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/gestionUtilisateurs")
public class ServletGestionUsersAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletGestionUsersAdmin() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME);

		if (Common.isConnected(request) && utilisateur.getIsAdmin()) {

			UtilisateurManager utilisateurManager = new UtilisateurManager();

			try {
				List<Utilisateur> utilisateurs = utilisateurManager.selectionTout();
				request.setAttribute("utilisateurs", utilisateurs);
			} catch (BusinessException e) {
				e.printStackTrace();
			}

			request.setAttribute(Common.PAGE_TITLE, "Gestion des utilisateurs");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/listUsers.jsp");
			rd.forward(request, response);

		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME);

		String u = request.getParameter("idUtilisateur");

		if (Common.isConnected(request) && utilisateur.getIsAdmin() && u!= null && Common.isNumeric(u)) {
			UtilisateurManager utilisateurManager = new UtilisateurManager();

			try {
				Utilisateur utilisateurRemoved = utilisateurManager.selectionParId(Integer.parseInt(u));
				if(utilisateurRemoved!=null && utilisateurRemoved.getId() != utilisateur.getId()) {
					ArticleManager articleManager = new ArticleManager();
					RetraitManager retraitManager = new RetraitManager();
					EnchereManager enchereManager = new EnchereManager();
					
					List<Article> listArticles = articleManager.selectByUser(utilisateurRemoved.getId());
					for (Article article : listArticles) {
						enchereManager.removeEnchere(article.getNoArticle());
						retraitManager.removeArticle(article.getNoArticle());
						articleManager.removeArticle(article.getNoArticle());
					}
					utilisateurManager.removeListe(utilisateurRemoved.getId());
				}
			} catch (BusinessException e) {
				e.printStackTrace();
			}

		} else {
			response.sendRedirect(request.getContextPath());
		}
		
		this.doGet(request, response);

	}
}
