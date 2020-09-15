package servlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import bll.ArticleManager;
import bll.CategoriesManager;
import bll.EnchereManager;
import bo.Article;
import bo.Categorie;
import bo.Common;
import bo.Enchere;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/")
public class ServletHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoriesManager categoriesManager;

    public ServletHome() {
        super();
    	this.categoriesManager = new CategoriesManager();

    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Categorie> listeCategories = new ArrayList<>();
		Categorie categorieFiltre = null;
		try {
			listeCategories = categoriesManager.selectionTout();
			request.setAttribute( "categories", listeCategories);
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		EnchereManager enchereManager = new EnchereManager();
		ArticleManager articleManager = new ArticleManager();
		
		List<Enchere.Statut> encheresStatut = new ArrayList<>();
		List<Article.Statut> arcticleStatut = new ArrayList<>();

		List<Article> listeArticles = new ArrayList<>();
		List<Enchere> listeEncheres = new ArrayList<>();
		try {
			  if(request.getSession().getAttribute(Common.UTILISATEUR_NAME) == null) {	        			

	                encheresStatut.add(Enchere.Statut.EN_COURS);
	                listeArticles = enchereManager.selectionArticles();
	                
	                for (Article article : listeArticles) {
    					File f = new File(getServletContext().getRealPath("/")+"resources\\img\\articles\\"+article.getNoArticle()+".png");

						if(f.exists() && !f.isDirectory()) {
        					article.setImg("resources\\img\\articles\\"+article.getNoArticle()+".png");
						}else {
							article.setImg("resources\\img\\articles\\article.png");	
						}
					}
    				request.setAttribute("listeArticles",listeArticles);
	            }else {
					Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME);
					
	            	String categorie = request.getParameter("categorie");
	            	if(categorie != null) {
	            		categorieFiltre = categoriesManager.selectionById(Integer.parseInt(categorie));        					
        				if(arcticleStatut.size() == 0 && encheresStatut.size() == 0 && categorieFiltre != null) {
        					listeArticles = articleManager.selectionFiltre(new ArrayList<Article.Statut>(), categorieFiltre, utilisateur);
        					listeArticles.addAll(enchereManager.selectionFiltre(new ArrayList<Enchere.Statut>(), categorieFiltre, utilisateur));
        				}	                
        				for (Article article : listeArticles) {
        					File f = new File(getServletContext().getRealPath("/")+"resources\\img\\articles\\"+article.getNoArticle()+".png");

    						if(f.exists() && !f.isDirectory()) {
            					article.setImg("resources\\img\\articles\\"+article.getNoArticle()+".png");
    						}else {
    							article.setImg("resources\\img\\articles\\article.png");	
    						}
    					}
        				request.setAttribute("listeArticles",listeArticles);
	            	}
	            	
	            	String s = request.getParameter("test");	
	        		if(s !=null) {
	        			ObjectMapper mapper = new ObjectMapper();
	        			List<?> checkboxList = mapper.readValue(s, List.class);
        			
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
	        			
	        			try {
	        				listeArticles = encheresStatut.size() != 0 ? enchereManager.selectionFiltre(encheresStatut, categorieFiltre, utilisateur) : arcticleStatut.size() != 0 ? articleManager.selectionFiltre(arcticleStatut, categorieFiltre, utilisateur) : null;
	        				
	        				for (Article article : listeArticles) {
	        					File f = new File(getServletContext().getRealPath("/")+"resources\\img\\articles\\"+article.getNoArticle()+".png");

								if(f.exists() && !f.isDirectory()) {
		        					article.setImg("resources\\img\\articles\\"+article.getNoArticle()+".png");
								}else {
									article.setImg("resources\\img\\articles\\article.png");	
								}
								if(checkboxList.contains("ventesend") || checkboxList.contains("ventesencours") || checkboxList.contains("ventesnon")) {
									article.setStatut(Article.getStatut(article, (Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME)));
								}else {
									article.setStatut(Enchere.getStatut(article, (Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME)));
								}
							}
	        				
	        				request.setAttribute("listeArticles",listeArticles);
	        				
	        			} catch (BusinessException e) {
	        				e.printStackTrace();
	        			}
	        		}
	            }

		} catch (BusinessException e2) {
			e2.printStackTrace();
		}
		
		if(request.getSession().getAttribute(Common.UTILISATEUR_NAME) != null) {
			request.setAttribute("logged", true);
		}
		else {
			request.setAttribute("logged", false);
			request.setAttribute("listeEncheres",listeEncheres);
		}
	
		request.setAttribute("listeEncheres",listeEncheres);

		request.setAttribute("title", "Accueil");
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