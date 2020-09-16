package servlets;

import java.io.File;
import java.io.IOException;

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

@WebServlet("/enchere")
public class ServletDetailEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDetailEnchere() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EnchereManager enchereManager = new EnchereManager();
		RetraitManager retraitManager = new RetraitManager();
		ArticleManager articleManager = new ArticleManager();
		String h1 = null;

		try {
			Article article = articleManager.selectById(Integer.parseInt(request.getParameter("enchere")));

			File f = new File(getServletContext().getRealPath("/") + "resources\\img\\articles\\"
					+ article.getNoArticle() + ".png");

			if (f.exists() && !f.isDirectory()) {
				article.setImg("resources\\img\\articles\\" + article.getNoArticle() + ".png");
			} else {
				article.setImg("resources\\img\\articles\\article.png");
			}

			Retrait retrait = retraitManager.selectById(article.getNoArticle());
			Enchere enchere = enchereManager.selectionParArticle(article.getNoArticle()).get(0);
			request.setAttribute("enchere", enchere);
			request.setAttribute("retrait", retrait);
			request.setAttribute("article", article);

			Article.Statut statut = Enchere.getStatut(article,
					(Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME));

			switch (statut) {
			case EN_COURS_ENCHERE:
				h1 = "L'enchère la plus haute pour le moment est de la part de " + enchere.getUtilisateur().getPseudo()
						+ " !";
				break;
			case CLOSE:
				h1 = "L'enchère à été remportéé par " + enchere.getUtilisateur().getPseudo() + " !";
				break;
			case WIN_ENCHERE:
				h1 = "Vous avez remporté l'enchère félicitations !";
				break;

			default:
				break;
			}

			request.setAttribute("h1", h1);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		request.setAttribute("title", "Détail d'un article");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/detailEnchere.jsp");
		rd.forward(request, response);
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
