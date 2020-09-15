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
import bo.Common;
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

		UtilisateurManager utilisateurManager = new UtilisateurManager();
		String cookie = Common.getCookie(request, Common.UTILISATEUR_NAME);
		
		if(cookie != null) {
			Utilisateur utilisateur;
			try {
				utilisateur = utilisateurManager.selectionParId(Integer.parseInt(cookie));
				HttpSession currentUserSession = request.getSession();
				currentUserSession.setAttribute(Common.UTILISATEUR_NAME, utilisateur);
				System.out.println("test");
				System.out.println(utilisateur.toString());
				// 5 minutes
				currentUserSession.setMaxInactiveInterval(300);
				response.sendRedirect("/WEB-INF/views/accueil.jsp");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else {
			request.setAttribute("title", "Connexion");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			rd.forward(request, response);	
		}
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
			
			Utilisateur utilisateur = utilisateurManager.selectionParPseudoMotDePasse(pseudo, Common.getMd5(motDePasse));
			
			if(request.getParameter("souvenir") != null) {
				Cookie cookieUtilisateur = new Cookie(Common.UTILISATEUR_NAME, utilisateur.getId().toString());
				response.addCookie(cookieUtilisateur);
			}
			
			if(utilisateur.getId() > 0){
				request.setAttribute("data", utilisateur);	
				HttpSession currentUserSession = request.getSession();
				currentUserSession.setAttribute(Common.UTILISATEUR_NAME, utilisateur);
				// 5 minutes
				currentUserSession.setMaxInactiveInterval(300);
				request.setAttribute("title", "Profil");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/profil.jsp");
				rd.forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error404.jsp");
				rd.forward(request, response);
				//throw new BusinessException();
			}
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
