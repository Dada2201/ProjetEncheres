package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.Common;
import bo.Utilisateur;

@WebServlet("/profil")
/**
 * Servlet implementation class ServletProfil
 */
public class ServletProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletProfil() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(Common.isConnected(request)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/profil.jsp");
			request.setAttribute(Common.UTILISATEUR_NAME,
					(Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME));
			request.setAttribute(Common.PAGE_TITLE, "Profil");
			rd.forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
