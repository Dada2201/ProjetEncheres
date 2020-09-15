package servlets;

import java.io.IOException;
import java.util.Date;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	RetraitManager retraitManager = new RetraitManager();
    	ArticleManager articleManager = new ArticleManager();
    	String h1 = null;
    	
    	try {
			Article article = articleManager.selectById(Integer.parseInt(request.getParameter("idArticle")));
			Retrait retrait = retraitManager.selectById(article.getNoArticle());
			request.setAttribute("retrait", retrait);
			request.setAttribute("article", article);			
			
			Article.Statut statut = Article.getStatut(article, (Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME));
			
			switch (statut) {
			case NOT_READY:
				h1 = "L'enchère n'a pas encore débuté !";
				break;
			case CLOSE:
				h1 = "L'enchère à été remportéé par "+ article.getEncheres().get(0).getUtilisateur().getPseudo() + " !";
				break;
			case EN_COURS:
				h1 = "L'enchère est en cours !";
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

		request.setAttribute("title", "Détail d'une enchère");
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/detailEnchere.jsp");	
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
