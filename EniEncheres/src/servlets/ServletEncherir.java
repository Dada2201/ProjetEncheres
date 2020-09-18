package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
import bo.Retrait;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/encherir")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Article article = null;
	Enchere enchere = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEncherir() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (Common.isConnected(request)) {
			EnchereManager enchereManager = new EnchereManager();
			RetraitManager retraitManager = new RetraitManager();
			ArticleManager articleManager = new ArticleManager();
			Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
			request.setAttribute("utilisateur", u);

			try {
				this.article = articleManager.selectById(Integer.parseInt(request.getParameter("enchere")));

				File f = new File(getServletContext().getRealPath("/") + "resources\\img\\articles\\"
						+ this.article.getNoArticle() + ".png");

				if (f.exists() && !f.isDirectory()) {
					this.article.setImg("resources\\img\\articles\\" + this.article.getNoArticle() + ".png");
				} else {
					this.article.setImg("resources\\img\\articles\\article.png");
				}

				List<Enchere> encheres = enchereManager.selectionParArticle(this.article.getNoArticle());

				this.enchere = encheres != null && encheres.size() != 0 ? encheres.get(0) : null;
				Retrait retrait = retraitManager.selectById(this.article.getNoArticle());
				request.setAttribute("article", this.article);
				request.setAttribute("enchere", this.enchere);
				request.setAttribute("retrait", retrait);
				request.setAttribute(Common.UTILISATEUR_NAME, request.getSession().getAttribute(Common.UTILISATEUR_NAME));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}

			request.setAttribute(Common.PAGE_TITLE, "Enchérir");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/encherirVente.jsp");
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

		Utilisateur utilisateur = ((Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME));
		UtilisateurManager um = new UtilisateurManager();
		EnchereManager enchereManager = new EnchereManager();
		int prix = Integer.parseInt(request.getParameter("prix"));
		if(prix > this.enchere.getMontantEnchere()) {
			Enchere enchere = new Enchere((Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME),
					new Date(), prix);
			
			try {
				enchereManager.ajouter(utilisateur, this.article, enchere);
				int credit = utilisateur.getCredit() - prix;
				um.updateCredit(utilisateur,credit);
				utilisateur.setCredit(credit);
				request.getSession().setAttribute(Common.UTILISATEUR_NAME, utilisateur);
				
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else {
			request.setAttribute("errorEnchere", true);
		}

		response.sendRedirect("encherir?enchere=" + this.article.getNoArticle());
	}

}
