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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(Common.UTILISATEUR_NAME, (Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME));
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/modifProfil.jsp");
		rd.forward(request, response);
		   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/modifProfil.jsp");
		rd.forward(request, response);
				
		if (request.getParameter("update_button") != null) {
			request.setCharacterEncoding("UTF-8");
	    	UtilisateurManager um = new UtilisateurManager();
	    	String pseudo, nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,confirmationMotDePasse;	    	
	    	
	    	pseudo = request.getParameter("pseudo");
			nom = request.getParameter("nom");
			prenom = request.getParameter("prenom");
			email = request.getParameter("email");
			telephone = request.getParameter("telephone");
			rue = request.getParameter("rue");
			codePostal = request.getParameter("codepostal");
			ville = request.getParameter("ville");
			motDePasse = request.getParameter("mdpnouveau");
			confirmationMotDePasse = request.getParameter("mdpconfirm");

			
			if(!motDePasse.equals(confirmationMotDePasse)) {
				BusinessException businessException = null;
				businessException.ajouterErreur(CodesResultatBLL.REGLE_MOT_DE_PASSE_ERREUR);
				try {
					throw businessException;
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
			
			HttpSession currentUserSession = request.getSession();
			Utilisateur currentUser = (Utilisateur) currentUserSession.getAttribute(Common.UTILISATEUR_NAME);
			
			Utilisateur updatedUser = new Utilisateur(currentUser.getId(),pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,currentUser.getCredit(),currentUser.getIsAdmin());
			try {
				um.update(updatedUser);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			currentUserSession.setAttribute(Common.UTILISATEUR_NAME, updatedUser);
			
	    } else if (request.getParameter("delete_button") != null) {
	    	Utilisateur u = (Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME);
	    	UtilisateurManager um = new UtilisateurManager();
	    	try {
				um.removeListe(u.getId());
			} catch (BusinessException e) {
				e.printStackTrace();
			} finally {}
	    }
	}

}
