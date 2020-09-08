package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.UtilisateurManager;
import bo.Utilisateur;
import dal.BusinessException;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifProfil.jsp");
		rd.forward(request, response);
		   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifProfil.jsp");
				rd.forward(request, response);
				
		if (request.getParameter("update_button") != null) {
			request.setCharacterEncoding("UTF-8");
	    	UtilisateurManager um = new UtilisateurManager();
	    	String pseudo, nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,confirmationMotDePasse;
	    	int no_utilisateur;
	    	
	    	
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
			
			HttpSession currentUserSession = request.getSession();
			Utilisateur currentUser = (Utilisateur) currentUserSession.getAttribute("utilisateur");
			
			Utilisateur updatedUser = new Utilisateur(currentUser.getId(),pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,currentUser.getCredit(),currentUser.getIsAdmin());
			try {
				um.update(updatedUser);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			currentUserSession.setAttribute("utilisateur", updatedUser);
			
	    } else if (request.getParameter("delete_button") != null) {
	    	Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
	    	System.out.println(u.getPseudo());
	    	UtilisateurManager um = new UtilisateurManager();
	    	try {
				um.removeListe(u.getId());
			} catch (BusinessException e) {
				e.printStackTrace();
			} finally {}
	    }
	}

}
