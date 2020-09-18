package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Categorie> listeCategories = new ArrayList<>();
		Categorie categorieFiltre = null;
		try {
			listeCategories = categoriesManager.selectionTout();
			request.setAttribute("categories", listeCategories);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		EnchereManager enchereManager = new EnchereManager();
		ArticleManager articleManager = new ArticleManager();

		List<Enchere.Statut> encheresStatut = new ArrayList<>();
		List<Article.Statut> arcticleStatut = new ArrayList<>();

		List<Article> listeArticles = new ArrayList<>();
		int nbRows = 0;			
		String search = request.getParameter("search");
		String categorie = request.getParameter("categorie");
		String page = request.getParameter("page");
		try {
			if (request.getSession().getAttribute(Common.UTILISATEUR_NAME) == null) {
				if(search == null && categorie == null && !Common.isNumeric(categorie) || !Pattern.matches("^[a-zA-Z 0-9]+$", search)) {
					listeArticles = enchereManager.selectionArticles(page != null ? Integer.parseInt(page) - 1 : 0);
					nbRows = enchereManager.getNbRows();
					for (Article article : listeArticles) {
						Common.setImg(article, getServletContext());
						article.setStatut(Article.Statut.EN_COURS_ENCHERE);
					}
					request.setAttribute("listeArticles", listeArticles);
					request.setAttribute("nbItems", nbRows);
					
				}
				

				if (categorie != null && Common.isNumeric(categorie)) {
					categorieFiltre = categoriesManager.selectionById(Integer.parseInt(categorie));
					if (arcticleStatut.size() == 0 && encheresStatut.size() == 0 && categorieFiltre != null) {
						listeArticles.addAll(enchereManager.selectionFiltre(new ArrayList<Enchere.Statut>(),
								categorieFiltre, null, page != null ? Integer.parseInt(page) - 1 : 0));
						nbRows += enchereManager.getNbRows();
					}
					for (Article article : listeArticles) {
						Common.setImg(article, getServletContext());
						article.setStatut(Article.Statut.EN_COURS_ENCHERE);
					}
					request.setAttribute("listeArticles", listeArticles);
					request.setAttribute("nbItems", nbRows);
				}

				if (search != null && Pattern.matches("^[a-zA-Z 0-9]+$", search)) {
					search = search.trim();
					try {
						listeArticles.addAll(articleManager.selectionFiltre(null, null, null, search, page != null ? Integer.parseInt(page) - 1 : 0, Common.NB_ITEMS_PAGE));
						for (Article article : listeArticles) {
							Common.setImg(article, getServletContext());
							article.setStatut(Article.Statut.EN_COURS_ENCHERE);
						}
						nbRows += articleManager.getNbRows();
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					request.setAttribute("listeArticles", listeArticles);
					request.setAttribute("nbItems", nbRows);
				}

			} else {
				Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME);

				if (categorie != null && !categorie.equals("Toutes")) {
					categorieFiltre = categoriesManager.selectionById(Integer.parseInt(categorie));
					if (arcticleStatut.size() == 0 && encheresStatut.size() == 0 && categorieFiltre != null) {
						listeArticles = articleManager.selectionFiltre(new ArrayList<Article.Statut>(), categorieFiltre,
								utilisateur, null, page != null ? Integer.parseInt(page) - 1 : 0, Common.NB_ITEMS_PAGE);
						nbRows = articleManager.getNbRows();
						for (Article article : listeArticles) {
							Common.setImg(article, getServletContext());
							article.setStatut(Article.getStatut(article, utilisateur));
						}
						listeArticles.addAll(enchereManager.selectionFiltre(new ArrayList<Enchere.Statut>(),
								categorieFiltre, utilisateur, page != null ? Integer.parseInt(page) - 1 : 0));
						for (Article article : listeArticles) {
							Common.setImg(article, getServletContext());
							article.setEncheres(enchereManager.selectionParArticle(article.getNoArticle()));
							article.setStatut(Enchere.getStatut(article, utilisateur));
						}
						nbRows += enchereManager.getNbRows();
					}
					for (Article article : listeArticles) {
						Common.setImg(article, getServletContext());
					}
					request.setAttribute("listeArticles", listeArticles);
					request.setAttribute("nbItems", nbRows);
				}

				String s = request.getParameter("checkbox");
				if (s != null) {
					ObjectMapper mapper = new ObjectMapper();
					List<?> checkboxList = mapper.readValue(s, List.class);

					if (checkboxList.contains("encheresouvertes")) {
						arcticleStatut.add(Article.Statut.OPEN);
					}
					if (checkboxList.contains("enchereswin")) {
						encheresStatut.add(Enchere.Statut.WIN);
					}
					if (checkboxList.contains("encheresencours")) {
						encheresStatut.add(Enchere.Statut.EN_COURS_UTILISATEUR);
					}
					if (checkboxList.contains("ventesnon")) {
						arcticleStatut.add(Article.Statut.NOT_READY);
					}
					if (checkboxList.contains("ventesencours")) {
						arcticleStatut.add(Article.Statut.EN_COURS);
					}
					if (checkboxList.contains("ventesend")) {
						arcticleStatut.add(Article.Statut.CLOSE);
					}

					try {
						if (encheresStatut.size() != 0) {
							listeArticles.addAll(enchereManager.selectionFiltre(encheresStatut, categorieFiltre, utilisateur,
									page != null ? Integer.parseInt(page) - 1 : 0));
							nbRows += enchereManager.getNbRows();
						}
						if(listeArticles.size() != Common.NB_ITEMS_PAGE) {
							if (arcticleStatut.size() != 0) {
								listeArticles.addAll(articleManager.selectionFiltre(arcticleStatut, categorieFiltre, utilisateur,
										null, page != null ? Integer.parseInt((nbRows/Common.NB_ITEMS_PAGE)+page) - 1 : 0, Common.NB_ITEMS_PAGE-listeArticles.size()));
								nbRows += articleManager.getNbRows();
							}
						}

						for (Article article : listeArticles) {
							Common.setImg(article, getServletContext());
							if (checkboxList.contains("ventesend") || checkboxList.contains("ventesencours")
									|| checkboxList.contains("ventesnon")) {
								article.setStatut(Article.getStatut(article, utilisateur));
							} else {
								article.setEncheres(enchereManager.selectionParArticle(article.getNoArticle()));
								article.setStatut(Enchere.getStatut(article, utilisateur));
							}
						}

						request.setAttribute("listeArticles", listeArticles);
						request.setAttribute("nbItems", nbRows);

					} catch (BusinessException e) {
						e.printStackTrace();
					}
				}

				if (search != null && Pattern.matches("^[a-zA-Z 0-9]+$", search)) {
					search = search.trim();
					try {
						listeArticles.addAll(articleManager.selectionFiltre(null, null, null, search, page != null ? Integer.parseInt(page) - 1 : 0, Common.NB_ITEMS_PAGE));
						for (Article article : listeArticles) {
							Common.setImg(article, getServletContext());
						}
						nbRows += articleManager.getNbRows();
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					request.setAttribute("listeArticles", listeArticles);
					request.setAttribute("nbItems", nbRows);
				}
			}

		} catch (BusinessException e2) {
			e2.printStackTrace();
		}

		request.setAttribute(Common.PAGE_TITLE, "Accueil");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}