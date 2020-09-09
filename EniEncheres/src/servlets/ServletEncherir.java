package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.EnchereManager;
import bll.RetraitManager;
import bo.Enchere;
import bo.Retrait;
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
    	RetraitManager retraitManager = new RetraitManager();
    	
    	try {
			this.enchere = enchereManager.selectionParArticle(Integer.parseInt(request.getParameter("enchere")));
			Retrait retrait = retraitManager.selectById(this.enchere.getArticle().getNoArticle());
			request.setAttribute("article", this.enchere.getArticle());
			request.setAttribute("enchere", this.enchere);
			request.setAttribute("categorie", this.enchere.getArticle().getCategorie());
			request.setAttribute("utilisateurVendeur", this.enchere.getArticle().getUtilisateur());
			request.setAttribute("utilisateurEnchere", this.enchere.getUtilisateur());
			request.setAttribute("retrait", retrait);
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

    	Utilisateur utilisateur = ((Utilisateur)request.getSession().getAttribute("utilisateur"));

    	EnchereManager enchereManager = new EnchereManager();

		this.enchere.setDateEnchere(new Date());
		this.enchere.setMontantEnchere(Integer.parseInt(request.getParameter("prix")));
		this.enchere.setUtilisateur(utilisateur);

		try {
			enchereManager.update(this.enchere);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		response.sendRedirect("encherir?enchere="+this.enchere.getArticle().getNoArticle());
	}

}
