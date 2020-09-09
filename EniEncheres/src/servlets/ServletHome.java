package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.EnchereManager;
import bll.UtilisateurManager;
import bo.Utilisateur;
import dal.BusinessException;

public class ServletHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletHome() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accueil.jsp");
		Utilisateur u = (Utilisateur)request.getSession().getAttribute("utilisateur");
		if(request.getSession().getAttribute("utilisateur") != null) {
			request.setAttribute("logged", true);
			request.setAttribute("logged", false);
			EnchereManager em = new EnchereManager();
			try {
				request.setAttribute("listeEncheres",em.selectionParUtilisateur(u.getId()));
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			request.setAttribute("logged", false);
			EnchereManager em = new EnchereManager();
			try {
				request.setAttribute("listeEncheres",em.selectionTout());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		rd.forward(request, response);	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}


}
