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

@WebServlet("/ajoutUtilisateur")
public class ServletAddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletAddUser() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "Ajout d'un utilisateur");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/account.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, confirmationMotDePasse;
		try
		{			
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			
			pseudo = request.getParameter("pseudo");
			nom = request.getParameter("nom");
			prenom = request.getParameter("prenom");
			email = request.getParameter("email");
			telephone = request.getParameter("telephone");
			rue = request.getParameter("rue");
			codePostal = request.getParameter("codePostal");
			ville = request.getParameter("ville");
			motDePasse = request.getParameter("motDePasse");
			confirmationMotDePasse = request.getParameter("confirmationMotDePasse");

			
			if(!motDePasse.equals(confirmationMotDePasse)) {
				BusinessException businessException = null;
				businessException.ajouterErreur(CodesResultatBLL.REGLE_MOT_DE_PASSE_ERREUR);
				throw businessException;
			}
			
			Utilisateur utilisateur = utilisateurManager.selectionParPseudo(pseudo);
			
			if(utilisateur == null) {
				utilisateur = utilisateurManager.ajouter(new Utilisateur(0, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, Common.getMd5(motDePasse), 0, false));	
				HttpSession currentUserSession = request.getSession();
				currentUserSession.setAttribute("utilisateur", utilisateur);
				// 5 minutes
				currentUserSession.setMaxInactiveInterval(300);
			}else {
				
			}
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/modifProfil.jsp");
		rd.forward(request, response);
	}
}
