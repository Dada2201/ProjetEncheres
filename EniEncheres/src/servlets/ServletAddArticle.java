package servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bll.ArticleManager;
import bll.CategoriesManager;
import bll.RetraitManager;
import bo.Article;
import bo.Categorie;
import bo.Common;
import bo.Retrait;
import bo.Utilisateur;
import dal.BusinessException;

@WebServlet("/ajoutArticle")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5,
maxRequestSize = 1024 * 1024 * 5 * 5)
public class ServletAddArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final int TAILLE_TAMPON = 10240;
    public static final String CHEMIN_FICHIERS = "/Users/abaron/"; // A changer

	private CategoriesManager categoriesManager;
	
    public ServletAddArticle() {
        super();
    	this.categoriesManager = new CategoriesManager();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Categorie> categories = categoriesManager.selectionTout();
			request.setAttribute( "categories", categories);
		}
		catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		request.setAttribute("title", "Ajout d'un article");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/nouvelleVente.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String nom, description, categorieSelected, dateDebutEnchere, dateFinEnchere, rue, codePostal, ville;
		int prix;
		try
		{			

			CategoriesManager categorieManager = new CategoriesManager();
			ArticleManager articleManager = new ArticleManager();
			RetraitManager retraitManager = new RetraitManager();
			Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute(Common.UTILISATEUR_NAME);
			
			nom = request.getParameter("nom");
			description = request.getParameter("description");
			dateDebutEnchere = request.getParameter("dateDebutEnchere");
			dateFinEnchere = request.getParameter("dateFinEnchere");
			rue = request.getParameter("rue");
			codePostal = request.getParameter("codePostal");
			ville = request.getParameter("ville");
			prix = Integer.parseInt(request.getParameter("prix"));
			categorieSelected = request.getParameter("categorie");

			Categorie categorie = categorieManager.selectionById(Integer.parseInt(categorieSelected));
			Article article = new Article(0, nom, description, new SimpleDateFormat("yyyy-MM-dd").parse(dateDebutEnchere), new SimpleDateFormat("yyyy-MM-dd").parse(dateFinEnchere)
					, prix, 0, utilisateur, categorie);
			Retrait retrait = new Retrait(article, rue, codePostal, ville);

			article = articleManager.ajouter(article, utilisateur, categorie);

	        Part part = request.getPart("photoArticle");
	        String nomFichier = getNomFichier(part);

	        if (nomFichier != null && !nomFichier.isEmpty()) {
	            String nomChamp = part.getName();
	             nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
	                    .substring(nomFichier.lastIndexOf('\\') + 1);

	            ecrireFichier(part, article.getNoArticle()+".png", getServletContext().getRealPath("/")+"resources\\img\\articles\\");

	            request.setAttribute(nomChamp, nomFichier);
	        }
			
			retraitManager.ajouter(retrait);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		response.sendRedirect(request.getContextPath());
	}
	
    private void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } finally {
            try {
                sortie.close();
            } catch (IOException ignore) {
            	System.out.println(ignore.getMessage());
            }
            try {
                entree.close();
            } catch (IOException ignore) {
            	System.out.println(ignore.getMessage());
            }
        }
    }
	
    private static String getNomFichier( Part part ) {
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }   
}
