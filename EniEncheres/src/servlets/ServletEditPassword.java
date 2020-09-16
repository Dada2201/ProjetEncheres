package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.CodesResultatBLL;
import bll.UtilisateurManager;
import bo.Common;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/editPassword")
public class ServletEditPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletEditPassword() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!Common.isConnected(request)) {
			request.setAttribute(Common.PAGE_TITLE, "Modifier mon mot de passe");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/editPassword.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String email, motDePasse, confirmationMotDePasse;
		try {
			UtilisateurManager utilisateurManager = new UtilisateurManager();

			email = request.getParameter("email");
			motDePasse = request.getParameter("motDePasse");
			confirmationMotDePasse = request.getParameter("confirmationMotDePasse");

			if (!motDePasse.equals(confirmationMotDePasse)) {
				BusinessException businessException = null;
				businessException.ajouterErreur(CodesResultatBLL.REGLE_MOT_DE_PASSE_ERREUR);
				throw businessException;
			}

			Utilisateur utilisateur = utilisateurManager.selectionParEmail(email);

			if (utilisateur == null) {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error404.jsp");
				rd.forward(request, response);
			} else {
				utilisateur.setMotDePasse(Common.getMd5(motDePasse));
				utilisateurManager.update(utilisateur);
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
