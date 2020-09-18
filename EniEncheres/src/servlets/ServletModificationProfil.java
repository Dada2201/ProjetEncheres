package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.CodesResultatBLL;
import bll.UtilisateurManager;
import bo.Common;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/modificationProfil")
/**
 * Servlet implementation class ServletModificationProfil
 */
public class ServletModificationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletModificationProfil() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (Common.isConnected(request)) {
			request.setAttribute(Common.UTILISATEUR_NAME,
					(Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME));
			request.setAttribute(Common.PAGE_TITLE, "Modification du profil");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/modifProfil.jsp");
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
		Boolean erreurModif = false;	
		if (Common.isConnected(request)) {
			if (request.getParameter("update_button") != null) {
				request.setCharacterEncoding("UTF-8");
				UtilisateurManager um = new UtilisateurManager();
				String pseudo, nom, prenom, email, telephone, rue, codePostal, ville, actuelMdp ,motDePasse,
						confirmationMotDePasse;

				pseudo = request.getParameter("pseudo");
				nom = request.getParameter("nom");
				prenom = request.getParameter("prenom");
				email = request.getParameter("email");
				telephone = request.getParameter("telephone");
				rue = request.getParameter("rue");
				codePostal = request.getParameter("codepostal");
				ville = request.getParameter("ville");
				actuelMdp = request.getParameter("mdpactuel");
				motDePasse = request.getParameter("mdpnouveau");
				confirmationMotDePasse = request.getParameter("mdpconfirm");

				
				HttpSession currentUserSession = request.getSession();
				Utilisateur currentUser = (Utilisateur) currentUserSession.getAttribute(Common.UTILISATEUR_NAME);
				
				if(currentUser.getMotDePasse().equals(Common.getMd5(motDePasse))) {
					erreurModif = true;
					request.setAttribute("errorOldNewSame", true);
				}
				
				if(Common.getMd5(actuelMdp).equals(currentUser.getMotDePasse())) {
					Utilisateur updatedUser = new Utilisateur(currentUser.getId(), pseudo, nom, prenom, email, telephone,
							rue, codePostal, ville, Common.getMd5(motDePasse), currentUser.getCredit(), currentUser.getIsAdmin());
					try {
						um.update(updatedUser);
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					currentUserSession.setAttribute(Common.UTILISATEUR_NAME, updatedUser);
				}else {
					erreurModif = true;
					request.setAttribute("errorMdp", true);
				}
			}
			else if (request.getParameter("delete_button") != null) {
					Utilisateur u = (Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME);
					UtilisateurManager um = new UtilisateurManager();
					try {
						um.remove(u.getId());
					} catch (BusinessException e) {
						e.printStackTrace();
					} finally {
					}
			}
			
			if(erreurModif) {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/modifProfil.jsp");
				rd.forward(request, response);
			}
			else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/profil.jsp");
				rd.forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

}
