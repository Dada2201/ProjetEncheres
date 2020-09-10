package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.EnchereManager;
import dal.BusinessException;

@WebServlet("/")
public class ServletHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletHome() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accueil.jsp");
		
		if(request.getSession().getAttribute("utilisateur") != null) {
			request.setAttribute("logged", true);
			//EnchereManager em = new EnchereManager();

		}
		else {
			request.setAttribute("logged", true);
			EnchereManager em = new EnchereManager();
			try {
				request.setAttribute("listeEncheres",em.selectionTout());
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		String test = request.getParameter("test");		

		System.out.println(test);
		rd.forward(request, response);	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}