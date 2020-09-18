package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.UtilisateurManager;
import bo.Common;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletLogin() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UtilisateurManager utilisateurManager = new UtilisateurManager();
		String cookie = Common.getCookie(request, Common.UTILISATEUR_NAME);

		if (request.getSession().getAttribute(Common.UTILISATEUR_NAME) != null) {
			response.sendRedirect(request.getContextPath());
		} else if (cookie != null) {
			Utilisateur utilisateur;
			try {
				utilisateur = utilisateurManager.selectionParId(Integer.parseInt(cookie));
				HttpSession currentUserSession = request.getSession();
				currentUserSession.setAttribute(Common.UTILISATEUR_NAME, utilisateur);
				// 5 minutes
				currentUserSession.setMaxInactiveInterval(300);
				response.sendRedirect(request.getContextPath());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute(Common.PAGE_TITLE, "Connexion");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pseudo, motDePasse;
		try {
			UtilisateurManager utilisateurManager = new UtilisateurManager();

			pseudo = request.getParameter("pseudo");
			motDePasse = request.getParameter("motDePasse");

			Utilisateur utilisateur = utilisateurManager.selectionParPseudoMotDePasse(pseudo,
					Common.getMd5(motDePasse));

			if (request.getParameter("souvenir") != null) {
				Cookie cookieUtilisateur = new Cookie(Common.UTILISATEUR_NAME, utilisateur.getId().toString());
				response.addCookie(cookieUtilisateur);
			}

			if (utilisateur != null) {
				HttpSession currentUserSession = request.getSession();
				currentUserSession.setAttribute(Common.UTILISATEUR_NAME, utilisateur);
				// 5 minutes
				currentUserSession.setMaxInactiveInterval(300);
				request.setAttribute(Common.PAGE_TITLE, "Connexion");
				response.sendRedirect(request.getContextPath());
			} else {
				request.setAttribute("error", true);
				request.setAttribute(Common.PAGE_TITLE, "Connexion");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
				rd.forward(request, response);
			}
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
