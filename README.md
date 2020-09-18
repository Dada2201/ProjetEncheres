# ProjetEncheres

##Connexion à la base de données

    <Context>
    	<Ressource
    		name="jdbc/pool_cnx"
    		
    		driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver"
    		type="javax.sql.DataSource"
    		
    		url="jdbc:sqlserver://localhost;databasename=XXXXXXX"
    		username="XXXXXXX"
    		password="XXXXXXX"
    		
    		maxTotal="100"
    		maxIdle="30"
    		maxWaitMillis="5000"		
    		/>
    </Context>
    
Remplir les logins et base de données avec ses identifiants

##Ajout d'images de base

Pour ajouter des images directement dans votre projet, elles doivent se trouver dans le serveur tomcat,
ajouter des images au chemin workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\EniEncheres\resources\img\articles
