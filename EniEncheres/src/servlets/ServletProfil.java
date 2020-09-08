package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.Utilisateur;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		Utilisateur u = new Utilisateur(1,"Jojo","Jean","Pierre,","jp@gmail.com","06","rue cj","13090","aix","mmm",0,true);
		// TODO envoyer l'utilisateur de la bdd qui correspond au clic effectuer 
		try {
			request.setAttribute("data", u);
			System.out.println(request.getAttribute("data")+"gg");
			rd = request.getRequestDispatcher("/WEB-INF/profil.jsp");
			rd.forward(request, response);
		}finally {
			
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
