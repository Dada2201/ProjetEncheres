package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.CodesResultatBLL;
import bll.UtilisateurManager;
import bo.Utilisateur;
import dal.BusinessException;

public class ServletAddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletAddUser() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				utilisateur = utilisateurManager.ajouter(new Utilisateur(0, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, getMd5(motDePasse), 0, false));	
				HttpSession currentUserSession = request.getSession();
				currentUserSession.setAttribute("utilisateur", utilisateur);
			}else {
				
			}
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifProfil.jsp");
		rd.forward(request, response);
	}

	public static String getMd5(String input) 
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
}
