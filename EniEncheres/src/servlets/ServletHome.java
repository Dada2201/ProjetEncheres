package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import bll.ArticleManager;
import bll.EnchereManager;
import bo.Article;
import bo.Enchere;
import bo.Utilisateur;
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

		EnchereManager enchereManager = new EnchereManager();
		ArticleManager articleManager = new ArticleManager();
		
		List<Enchere.Statut> encheresStatut = new ArrayList<>();
		List<Article.Statut> arcticleStatut = new ArrayList<>();

		List<Article> listeArticles = new ArrayList<>();
		List<Enchere> listeEncheres = new ArrayList<>();
		try {
			  if(request.getSession().getAttribute("utilisateur") == null) {
	                encheresStatut.add(Enchere.Statut.EN_COURS);
	                listeArticles = enchereManager.selectionFiltre(encheresStatut, null);
	            }else {
	            	String s = request.getParameter("test");	
	        		if(s !=null) {
	        			ObjectMapper mapper = new ObjectMapper();
	        			List checkboxList = mapper.readValue(s, List.class);
	        			//debug
	        			System.out.println("--------");for(int i=0 ;i< checkboxList.size();i++) {System.out.println(checkboxList.get(i).toString());}
	        			
	        			if(checkboxList.contains("encheresouvertes")) {
	        				encheresStatut.add(Enchere.Statut.OPEN);
	        			}
	        			if(checkboxList.contains("enchereswin")) {
	        				encheresStatut.add(Enchere.Statut.WIN);
	        			}
	        			if(checkboxList.contains("encheresencours")) {
	        				encheresStatut.add(Enchere.Statut.EN_COURS_UTILISATEUR);
	        			}
	        			if(checkboxList.contains("ventesnon")) {
	        				arcticleStatut.add(Article.Statut.NOT_READY);
	        			}
	        			if(checkboxList.contains("ventesencours")) {
	        				arcticleStatut.add(Article.Statut.EN_COURS);
	        			}
	        			if(checkboxList.contains("ventesend")) {
	        				arcticleStatut.add(Article.Statut.CLOSE);
	        			}

	        			Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("utilisateur");
	        			
	        			try {
	        				listeArticles = encheresStatut.size() != 0 ? enchereManager.selectionFiltre(encheresStatut, utilisateur) : arcticleStatut.size() != 0 ? articleManager.selectionFiltre(arcticleStatut, utilisateur) : null;
	        				System.out.println(listeArticles);
	        				request.setAttribute("listeArticles",listeArticles);
	        				
	        			} catch (BusinessException e) {
	        				e.printStackTrace();
	        			}
	        			
	        		}
	            }

		} catch (BusinessException e2) {
			e2.printStackTrace();
		}
		
		if(request.getSession().getAttribute("utilisateur") != null) {
			request.setAttribute("logged", true);
		}
		else {
			request.setAttribute("logged", false);
			request.setAttribute("listeEncheres",listeEncheres);
		}
	
		System.out.println(listeEncheres);
		request.setAttribute("listeEncheres",listeEncheres);
        
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accueil.jsp");
		rd.forward(request, response);	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}