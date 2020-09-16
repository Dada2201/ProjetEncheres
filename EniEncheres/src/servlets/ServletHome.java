package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		String page = request.getParameter("page");

		try {
			if (request.getSession().getAttribute(Common.UTILISATEUR_NAME) == null) {

				encheresStatut.add(Enchere.Statut.EN_COURS);
				listeArticles = enchereManager.selectionArticles(page != null ? Integer.parseInt(page)-1 : 0);
				nbRows = enchereManager.getNbRows();

				for (Article article : listeArticles) {
					Common.setImg(article, getServletContext());
				}
				request.setAttribute("listeArticles", listeArticles);
				request.setAttribute("nbItems", nbRows);

			} else {
				Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute(Common.UTILISATEUR_NAME);

				String categorie = request.getParameter("categorie");
				if (categorie != null) {
					categorieFiltre = categoriesManager.selectionById(Integer.parseInt(categorie));
					if (arcticleStatut.size() == 0 && encheresStatut.size() == 0 && categorieFiltre != null) {
						listeArticles = articleManager.selectionFiltre(new ArrayList<Article.Statut>(), categorieFiltre,
								utilisateur, page != null ? Integer.parseInt(page)-1 : 0);
						nbRows = articleManager.getNbRows();
						listeArticles.addAll(enchereManager.selectionFiltre(new ArrayList<Enchere.Statut>(),
								categorieFiltre, utilisateur, page != null ? Integer.parseInt(page)-1 : 0));
						nbRows += enchereManager.getNbRows();
					}
					for (Article article : listeArticles) {
						Common.setImg(article, getServletContext());
					}
					request.setAttribute("listeArticles", listeArticles);
					request.setAttribute("nbItems", nbRows);
				}

				String s = request.getParameter("test");
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
						listeArticles = encheresStatut.size() != 0
								? enchereManager.selectionFiltre(encheresStatut, categorieFiltre, utilisateur,
										page != null ? Integer.parseInt(page)-1 : 0)
								: arcticleStatut.size() != 0 ? articleManager.selectionFiltre(arcticleStatut,
										categorieFiltre, utilisateur, page != null ? Integer.parseInt(page)-1 : 0)
										: new ArrayList<Article>();
						nbRows = encheresStatut.size() != 0 ? enchereManager.getNbRows()
								: arcticleStatut.size() != 0 ? articleManager.getNbRows() : 0;

						for (Article article : listeArticles) {
							Common.setImg(article, getServletContext());
							if (checkboxList.contains("ventesend") || checkboxList.contains("ventesencours")
									|| checkboxList.contains("ventesnon") || checkboxList.contains("encheresouvertes")) {
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
			}

		} catch (BusinessException e2) {
			e2.printStackTrace();
		}

		if (request.getSession().getAttribute(Common.UTILISATEUR_NAME) != null) {
			request.setAttribute("logged", true);
		} else {
			request.setAttribute("logged", false);
		}

		request.setAttribute("title", "Accueil");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}