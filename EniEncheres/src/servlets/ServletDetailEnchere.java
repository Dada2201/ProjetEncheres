package servlets;

import java.io.IOException;
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


@WebServlet("/enchere")
public class ServletDetailEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDetailEnchere() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	EnchereManager enchereManager = new EnchereManager();
    	RetraitManager retraitManager = new RetraitManager();
    	String h1 = null;
    	
    	try {
			Enchere enchere = enchereManager.selectionParArticle(Integer.parseInt(request.getParameter("enchere")));
			Retrait retrait = retraitManager.selectById(enchere.getArticle().getNoArticle());
			request.setAttribute("enchere", enchere);
			request.setAttribute("retrait", retrait);
			if(enchere.getArticle().getDateDebut().compareTo(new Date()) > 0) {
				h1 = "L'enchère n'a pas encore débuté";
			}	
			else if(enchere.getArticle().getDateFin().compareTo(new Date()) < 0){
				if(enchere.getUtilisateur().getId().equals(((Utilisateur)request.getSession().getAttribute("utilisateur")).getId())) {
					h1 = "Vous avez remporté l'enchère félicitations !";
				}else {
					h1 = "L'enchère à été remportéé par "+ enchere.getUtilisateur().getPseudo();
				}
			}
			else if(enchere.getArticle().getDateDebut().compareTo(new Date()) < 0 || enchere.getArticle().getDateDebut().compareTo(new Date()) == 0) {
				h1 = "L'enchère la plus haute pour le moment est de la part de " + enchere.getUtilisateur().getPseudo();
			}
			
			request.setAttribute("h1", h1);
			
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
		this.doGet(request, response);
	}

}
