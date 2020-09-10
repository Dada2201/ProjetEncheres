package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import bll.EnchereManager;
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
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accueil.jsp");
  
		String today = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(today);
		String date = simpleDateFormat.format(new Date());


		EnchereManager emanager = new EnchereManager();
		List<Enchere> listeEncheres = new ArrayList<>();
		try {
			listeEncheres = emanager.selectionTout();
		} catch (BusinessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(request.getSession().getAttribute("utilisateur") != null) {
			request.setAttribute("logged", true);
		}
		else {
			request.setAttribute("logged", false);
			request.setAttribute("listeEncheres",listeEncheres);
		}
		
		List<Enchere> listeEncheresFiltre = new ArrayList<>();
		String s = request.getParameter("test");	
		if(s !=null) {
			ObjectMapper mapper = new ObjectMapper();
			List checkboxList = mapper.readValue(s, List.class);
			//debug
			System.out.println("--------");for(int i=0 ;i< checkboxList.size();i++) {System.out.println(checkboxList.get(i).toString());}
			
			List<String> conditions = new ArrayList<>();
			
				if(checkboxList.contains("encheresouvertes")) {
						conditions.add(">"+date);
						listeEncheresFiltre.add(e);
				}
				if(checkboxList.contains("enchereswin")) {
					if(e.getArticle().getDateFin().before(new Date()) && e.getUtilisateur() == (Utilisateur) request.getSession().getAttribute("utilisateur")){
						conditions.add("<"+date);
						conditions.add("=")
						listeEncheresFiltre.add(e);
					}
				}
				if(checkboxList.contains("encheresencours")) {
					if(e.getUtilisateur() == (Utilisateur) request.getSession().getAttribute("utilisateur") && e.getArticle().getDateFin().after(today.getTime())) {
						listeEncheresFiltre.add(e);
					}
				}
				if(checkboxList.contains("ventesnon")) {
					Utilisateur u =  (Utilisateur) request.getSession().getAttribute("utilisateur");
					if(e.getArticle().getUtilisateur() == u && e.getArticle().getDateDebut().after(today.getTime())) {
						listeEncheresFiltre.add(e);
					}
				}
				if(checkboxList.contains("ventesencours")) {
					Utilisateur u =  (Utilisateur) request.getSession().getAttribute("utilisateur");
					if(e.getArticle().getUtilisateur() == u && e.getArticle().getDateDebut().after(today.getTime()) && e.getArticle().getDateFin().before(today.getTime())) {
						listeEncheresFiltre.add(e);
					}
				}
				if(checkboxList.contains("ventesend")) {
					Utilisateur u =  (Utilisateur) request.getSession().getAttribute("utilisateur");
					if(e.getArticle().getUtilisateur() == u  && e.getArticle().getDateFin().before(today.getTime())) {
						listeEncheresFiltre.add(e);
					}
				}
			}
		for(int i=0;i<listeEncheresFiltre.size();i++) {
			System.out.println(listeEncheresFiltre.get(i).getArticle().getNomArticle());
		}
		request.setAttribute("listeEncheresF",listeEncheresFiltre);
		rd.forward(request, response);	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}