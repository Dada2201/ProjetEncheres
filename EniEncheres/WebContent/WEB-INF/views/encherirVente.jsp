<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="partial//header.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center">
		<h1>Enchérir sur la vente</h1>
	</div>
	<div class="row">
		<div class="col-md-3">
			<img src="${article.img}" height="250px" width="250px" />
		</div>
		<div class="col-md-9">
			<c:if test="${enchere == null}">
				<p class="text-info">Personne n'a encore enchéri ! Soyez le
					premier !</p>
			</c:if>
			<c:if test="${(enchere != null && enchere.montantEnchere >= utilisateur.credit) || errorEnchere}">
				<p class="text-danger">Vous ne pouvez pas enchérir, vous avez seulement ${utilisateur.credit}pts alors que l'enchère est à ${enchere.montantEnchere}pts !</p>
			</c:if>

			<%@ include file="partial/descriptionArticle.jspf" %>
			<div class="form-group row">
				<p class="mr-3">Meilleure offre</p>
				<p>${enchere.montantEnchere}pts par ${enchere.utilisateur.pseudo}</p>
			</div>
			<%@ include file="partial/descriptionRetraitVendeur.jspf"%>
			<c:if test="${enchere == null || enchere.montantEnchere < utilisateur.credit}">
				<form action="encherir" method="post" class="form-group row">
					<p class ="col-md-3">Ma proposition</p>
					<input class="col-md-2 mr-2" type="number" name="prix" class="form-control" min="${enchere.montantEnchere+1}" /> <input type="submit"
						class="btn btn-primary" value="Enchérir" />
				</form>
			</c:if>
		</div>
	</div>
</div>
<%@ include file="partial/footer.jspf"%>