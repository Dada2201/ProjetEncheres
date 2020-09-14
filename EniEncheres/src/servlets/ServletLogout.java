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

import bo.Common;
import bo.Utilisateur;

@WebServlet("/logout")
public class ServletLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletLogout() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		HttpSession session = request.getSession();
		Utilisateur utilisateurSession = (Utilisateur)session.getAttribute(Common.UTILISATEUR_NAME);
		if(utilisateurSession!=null) {
			if(cookies != null) {
		        for (Cookie cookie : cookies) {
		        	if(cookie.getName().equals(Common.UTILISATEUR_NAME)) {	
		        		cookie.setValue(null);
		        		cookie.setMaxAge(-1);
		        	}
		        }
			}
			session.removeAttribute(Common.UTILISATEUR_NAME);
		}
		response.sendRedirect(request.getContextPath());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}


}
