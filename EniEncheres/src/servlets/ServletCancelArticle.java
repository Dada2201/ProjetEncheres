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
import bll.EnchereManager;
import bll.RetraitManager;
import bo.Article;
import bo.Categorie;
import bo.Common;
import bo.Retrait;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/cancelArticle")
public class ServletCancelArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CategoriesManager categoriesManager;
	
    public ServletCancelArticle() {
        super();
    	this.categoriesManager = new CategoriesManager();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int idArticle;
		
		RequestDispatcher rd = null;

		try
		{			
			idArticle = Integer.parseInt(request.getParameter("idArticle"));
			
			ArticleManager articleManager = new ArticleManager();
			EnchereManager enchereManager = new EnchereManager();
			RetraitManager retraitManager = new RetraitManager();
			
			Article article = articleManager.selectById(idArticle);

			if(article.getUtilisateur().getId().equals(((Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME)).getId())) {
								
				enchereManager.removeEnchere(article.getNoArticle());
				retraitManager.removeArticle(article.getNoArticle());
				articleManager.removeArticle(idArticle);
				response.sendRedirect(request.getContextPath());
			}
			else
			{
				rd = request.getRequestDispatcher("/WEB-INF/views/error404.jsp");
				rd.forward(request, response);
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}


}
