<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partial//header.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center mb-5">
		<h1>Se connecter</h1>
	</div>

	<c:if test="${error}">
		<div class="alert alert-danger" role="alert">Une erreur
			d'authentification est survenue !</div>
	</c:if>
	<form class="col-12" action="login" method="post">
		<div class="form-group row mx-auto col-md-8">
			<label class="col-4">Identifiant</label> <input type="text"
				class="form-control col-8" placeholder="Pseudo" name="pseudo"
				required autofocus>
		</div>
		<div class="form-group row mx-auto col-md-8">
			<label class="col-4">Mot de passe</label> <input type="password"
				class="form-control col-8" placeholder="Mot de passe"
				name="motDePasse" required>
		</div>
		<div class="mx-auto col-md-8 justify-content-center">
			<div class="row justify-content-center">
				<button type="submit" class="btn btn-primary mx-auto ">Connexion</button>
			</div>
			<div class="row mt-3 justify-content-center">
				<div class="form-check">
					<input type="checkbox" class="form-check-input" name="souvenir">
					<label class="form-check-label">Se souvenir de moi</label>
				</div>
			</div>
			<div class="row justify-content-center">
				<a href="${pageContext.request.contextPath}/editPassword">Mot de
					passe oublié</a>
			</div>
		</div>
	</form>
	<div class="mt-5 row justify-content-center">
		<a href="${pageContext.request.contextPath}/ajoutUtilisateur"
			class="btn btn-primary">Créer un compte</a>
	</div>
</div>

<%@ include file="partial/footer.jspf"%>
