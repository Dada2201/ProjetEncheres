package bo;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.UtilisateurManager;
import dal.BusinessException;

public class Common {

	public final static String UTILISATEUR_NAME = "utilisateur";
	
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
	
}
