package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.EnchereManager;
import bo.Enchere;
import bo.Utilisateur;
import dal.BusinessException;


@WebServlet("/encherir")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Enchere enchere = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEncherir() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	EnchereManager enchereManager = new EnchereManager();
    	
    	try {
			this.enchere = enchereManager.selectionParArticle(Integer.parseInt(request.getParameter("enchere")));
			request.setAttribute("article", enchere.getArticle());
			request.setAttribute("enchere", enchere);
			request.setAttribute("categorie", enchere.getArticle().getCategorie());
			request.setAttribute("utilisateurVendeur", enchere.getArticle().getUtilisateur());
			request.setAttribute("utilisateurEnchere", enchere.getUtilisateur());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    	
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/detailVente.jsp");
    	
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
