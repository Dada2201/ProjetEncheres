package dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {
	
	/**
	 * Echec général quand tentative d'ajouter un objet null
	 */
	public static final int INSERT_OBJET_NULL=10000;
	
	/**
	 * Echec général quand erreur non gérée à l'insertion 
	 */
	public static final int INSERT_OBJET_ECHEC=10001;
	
	/**
	 * Echec général quand erreur non gérée à la selection 
	 */
	public static final int SELECT_UTILISATEUR_ECHEC = 10002;
	
	/**
	 * Echec général quand erreur non gérée à la selection 
	 */
	public static final int SELECT_UTILISATEUR_INEXISTANTE = 10003;

	/**
	 * Echec général quand erreur non gérée à la selection 
	 */

	public static final int SUPPRESSION_UTILISATEUR_ERREUR = 10005;

	public static final int SELECT_ARTICLE_ECHEC = 10006;

	public static final int SUPPRESSION_ARTICLE_ERREUR = 10007;

	public static final int AJOUTER_ARTICLE_ECHEC = 10008;
	
	public static final int SELECT_ENCHERE_ECHEC = 10009;
	
	public static final int SUPPRESSION_ENCHERE_ERREUR = 10010;
}
