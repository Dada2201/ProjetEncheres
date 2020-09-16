<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${logged}">
	<%@ include file="partial/header/connected.jspf"%>
</c:if>
<c:if test="${!logged}">
	<!--  TODO change for test !logged or logged -->
	<%@ include file="partial/header/notConnected.jspf"%>
</c:if>
<div class="container py-5">
	<div class="row justify-content-center">
		<h1>${h1}</h1>
	</div>
	<div class="row">
		<div class="col-md-3">
			<img src="${article.img}" height="250px" width="250px" />
		</div>
		<div class="col-md-9">
			<div class="form-group row">
				<p>${article.nomArticle}</p>
			</div>
			<div class="form-group row">
				<p>Description</p>
				<p>${article.description}</p>
			</div>
			<div class="form-group row">
				<p>Catégorie</p>
				<p>${article.categorie.libelle}</p>
			</div>
			<div class="form-group row">
				<p>Meilleure offre</p>
				<p>${enchere.montantEnchere}pts par
					${enchere.utilisateur.pseudo}</p>
			</div>
			<div class="form-group row">
				<p>Mise à prix</p>
				<p>${article.prixInitial}pts</p>
			</div>
			<div class="form-group row">
				<p>Fin de l'enchère</p>
				<p>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${article.dateFin}" />
				</p>
			</div>
			<div class="form-group row">
				<p>Retrait</p>
				<div>
					<p>${retrait.rue}</p>
					<p>${retrait.codePostal}${retrait.ville}</p>
				</div>
			</div>
			<div class="form-group row">
				<p>Vendeur</p>
				<p>${article.utilisateur.pseudo}</p>
			</div>
		</div>
	</div>
</div>
<%@ include file="partial/footer.jspf"%>