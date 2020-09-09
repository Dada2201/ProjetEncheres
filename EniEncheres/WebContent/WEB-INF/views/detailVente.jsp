<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="partial/header/notConnected.jsp" %>
    </header>
    <div class="container">
        <div class="row justify-content-center">
            <h1>Détail vente</h1>
        </div>
        <div class="row">
            <div class="col-4">
                <img src="https://pbs.twimg.com/profile_images/790942822853640194/bvZIVYNp.jpg" height="250px" weight="250px" />
            </div>
            <div class="col-8">
                <div class="form-group row">
                    <p>${article.nomArticle}</p>
                </div>
                <div class="form-group row">
                    <p>Description :</p>
                    <p>${article.description}</p>
                </div>
                <div class="form-group row">
                    <p>Catégorie</p>
                    <p>${categorie.libelle}</p>
                </div>
                <div class="form-group row">
                    <p>Meilleure offre :</p>
                    <p>${enchere.montantEnchere} pts par ${utilisateurEnchere.pseudo}</p>
                </div>
                <div class="form-group row">
                    <p>Mise à prix :</p>
                    <p>${article.prixInitial} pts</p>
                </div>
                <div class="form-group row">
                    <p>Fin de l'enchère :</p>
                    <p><fmt:formatDate pattern = "dd/MM/yyyy" value = "${article.dateFin}" /></p>
                </div>
                <div class="form-group row">
                    <p>Retrait :</p>
                    <div>
                        <p>${retrait.rue}</p>
                        <p>${retrait.codePostal} ${retrait.ville}</p>
                    </div>
                </div>
                <div class="form-group row">
                    <p>Vendeur :</p>
                    <p>${utilisateurVendeur.pseudo}</p>
                </div>
                <form action="encherir" method="post" class="form-group row">
                    <p>Ma proposition :</p>
                    <input type="number" name="prix" />
                    <input type="submit" class="btn btn-primary" value="Enchérir" />
                </form>
            </div>
        </div>
    </div>
</body>

</html>
