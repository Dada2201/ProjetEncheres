<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="partial//header.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center">
		<h1>Détail de l'Enchère</h1>
	</div>
	<div class="row">
		<div class="col-md-3">
			<img src="${article.img}" height="250px" width="250px" />
		</div>
		<div class="col-md-9">
			<%@ include file="partial/descriptionArticle.jspf" %>
			<div class="form-group row">
				<p class="mr-3">Meilleure offre</p>
				<p>${enchere.montantEnchere}pts par ${enchere.utilisateur.pseudo}</p>
			</div>
			<%@ include file="partial/descriptionRetraitVendeur.jspf"%>

		</div>
	</div>
</div>
<%@ include file="partial/footer.jspf"%>