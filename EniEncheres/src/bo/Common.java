package bo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bll.UtilisateurManager;
import dal.BusinessException;

public class Common {

	public final static String UTILISATEUR_NAME = "utilisateur";
    public static final int TAILLE_TAMPON = 10240;
	public static final int NB_ITEMS_PAGE = 6;
	
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
	        for (Cookie cookie : cookies) {
	        	if(cookie.getName().equals(name)) {
	        		return cookie.getValue();
	        	}
	        }
		}
		return null;
	}
	
	public static void isConnected(HttpServletRequest request, HttpServletResponse response, UtilisateurManager utilisateurManager) throws IOException, BusinessException {
		if(request.getSession().getAttribute("utilisateur") != null || utilisateurManager.selectionParId( ((Utilisateur)request.getSession().getAttribute("utilisateur")).getId()) != null) {
			response.sendRedirect("/logout");
		}
	}

	public static String getMd5(String input) 
    { 
        try { 
              MessageDigest md = MessageDigest.getInstance("MD5"); 
              byte[] messageDigest = md.digest(input.getBytes()); 
              BigInteger no = new BigInteger(1, messageDigest); 
              String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
          catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
    public static void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
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
	
    public static String getNomFichier( Part part ) {
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }

	public static void setImg(Article article, ServletContext servletContext) {	        					
		File f = new File(servletContext.getRealPath("/")+"resources\\img\\articles\\"+article.getNoArticle()+".png");

		if(f.exists() && !f.isDirectory()) {
			article.setImg("resources\\img\\articles\\"+article.getNoArticle()+".png");
		}else {
			article.setImg("resources\\img\\articles\\article.png");	
		}
	}   
}
