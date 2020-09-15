package servlets;

import java.io.File;
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


@WebServlet("/encherir")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Article article = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEncherir() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	EnchereManager enchereManager = new EnchereManager();
    	RetraitManager retraitManager = new RetraitManager();
    	ArticleManager articleManager = new ArticleManager();
    	
    	Enchere enchere = null;
    	
    	try {
    		this.article = articleManager.selectById(Integer.parseInt(request.getParameter("enchere")));
    		
			File f = new File(getServletContext().getRealPath("/")+"resources\\img\\articles\\"+this.article.getNoArticle()+".png");

			if(f.exists() && !f.isDirectory()) {
				this.article.setImg("resources\\img\\articles\\"+this.article.getNoArticle()+".png");
			}else {
				this.article.setImg("resources\\img\\articles\\article.png");	
			}
    		
    		enchere = enchereManager.selectionParArticle(this.article.getNoArticle()).get(0);
			Retrait retrait = retraitManager.selectById(this.article.getNoArticle());
			request.setAttribute("article", this.article);
			request.setAttribute("enchere", enchere);
			request.setAttribute("retrait", retrait);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}		

		request.setAttribute("title", "Enchérir");
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/encherirVente.jsp");	
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	Utilisateur utilisateur = ((Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME));

    	EnchereManager enchereManager = new EnchereManager();

    	Enchere enchere = new Enchere((Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME), new Date(), Integer.parseInt(request.getParameter("prix")));

		try {
			enchereManager.ajouter(utilisateur, this.article, enchere);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		response.sendRedirect("encherir?enchere="+this.article.getNoArticle());
	}

}
