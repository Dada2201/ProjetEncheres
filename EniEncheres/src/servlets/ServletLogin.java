package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.UtilisateurManager;
import bo.Utilisateur;
import dal.BusinessException;

public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletLogin() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pseudo, motDePasse;
		try
		{			
			UtilisateurManager utilisateurManager = new UtilisateurManager();
		
			pseudo = request.getParameter("pseudo");
			motDePasse = request.getParameter("motDePasse");
			
			Utilisateur utilisateur = utilisateurManager.selectionParPseudoMotDePasse(pseudo, Utilisateur.getMd5(motDePasse));
			
			if(request.getParameter("souvenir") != null) {
				Cookie cookieUtilisateur = new Cookie("utilisateur", utilisateur.getId().toString());
				response.addCookie(cookieUtilisateur);
			}
			
			if(utilisateur != null) {
				request.setAttribute("data", utilisateur);	
				HttpSession currentUserSession = request.getSession();
				currentUserSession.setAttribute("utilisateur", utilisateur);
				// 5 minutes
				currentUserSession.setMaxInactiveInterval(300);
			}else {
				throw new BusinessException();
			}
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil.jsp");
		rd.forward(request, response);
	}
}
