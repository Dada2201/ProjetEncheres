package servlets;

import java.io.File;
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
import bo.Article;
import bo.Common;
import bo.Enchere;
import bo.Retrait;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/article")
public class ServletDetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDetailVente() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RetraitManager retraitManager = new RetraitManager();
		ArticleManager articleManager = new ArticleManager();
		EnchereManager enchereManager = new EnchereManager();
		String h1 = null;

		try {
			Article article = articleManager.selectById(Integer.parseInt(request.getParameter("idArticle")));
			if (Common.isConnected(request) && article != null) {
				File f = new File(getServletContext().getRealPath("/") + "resources\\img\\articles\\"
						+ article.getNoArticle() + ".png");

				if (f.exists() && !f.isDirectory()) {
					article.setImg("resources\\img\\articles\\" + article.getNoArticle() + ".png");
				} else {
					article.setImg("resources\\img\\articles\\article.png");
				}

				Retrait retrait = retraitManager.selectById(article.getNoArticle());
				request.setAttribute("retrait", retrait);
				request.setAttribute("article", article);
				request.setAttribute(Common.UTILISATEUR_NAME,
						request.getSession().getAttribute(Common.UTILISATEUR_NAME));

				Article.Statut statut = Article.getStatut(article,
						(Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME));

				switch (statut) {
				case NOT_READY:
					h1 = "L'enchère n'a pas encore débuté !";
					break;
				case CLOSE:
					h1 = "L'enchère à été remportéé par " + article.getEncheres().get(0).getUtilisateur().getPseudo()
							+ " !";
					break;
				case EN_COURS:
					h1 = "L'enchère est en cours !";
					if (Common.isConnected(request)) {
						Utilisateur utilisateur = (Utilisateur) request.getSession()
								.getAttribute(Common.UTILISATEUR_NAME);
						if (utilisateur.getId().equals(article.getUtilisateur().getId())) {
							article.setEncheres(enchereManager.selectionParArticle(article.getNoArticle()));
							List<Utilisateur> listUtilisateurs = new ArrayList<Utilisateur>();
							for (Enchere enchere : article.getEncheres()) {
								if (!enchere.getUtilisateur().getId().equals(utilisateur.getId())) {
									for (Utilisateur utilisateurTemp : listUtilisateurs) {
											listUtilisateurs.add(enchere.getUtilisateur());
									}
								}
							}

							request.setAttribute("utilisateursEncheres", listUtilisateurs);
						}
					}
					break;

				default:
					break;
				}

				request.setAttribute("h1", h1);

				request.setAttribute(Common.PAGE_TITLE, "Détail d'une enchère");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/detailVente.jsp");
				rd.forward(request, response);

			} else {
				response.sendRedirect(request.getContextPath());
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
