<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="partial/header/notConnected.jspf" %>
    <div class="container py-5">
        <div class="row justify-content-center">
			<h1>${h1}</h1>
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
                    <p>${article.categorie.libelle}</p>
                </div>
                <div class="form-group row">
                    <p>Meilleure offre :</p>
                    <p>${enchere.montantEnchere} pts par ${enchere.utilisateur.pseudo}</p>
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
                    <p>${article.utilisateur.pseudo}</p>
                </div>
            </div>
        </div>
    </div>
<%@ include file="partial/footer.jspf" %>