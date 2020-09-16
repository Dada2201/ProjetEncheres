<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partial/header/default.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center pb-5">
		<h1>Mot de passe oublié</h1>
	</div>
	<form action="editPassword" method="post">
		<div class="form-group row mx-auto col-md-6">
			<label class="col-4">Email :</label> <input type="email"
				class="form-control col-8" placeholder="Email" maxlength="30"
				name="email" required>
		</div>
		<div class="form-group row mx-auto col-md-6">
			<label class="col-4">Mot de passe :</label> <input type="password"
				class="form-control col-8" placeholder="Mot de passe"
				maxlength="255" name="motDePasse" required>
		</div>
		<div class="form-group row mx-auto col-md-6">
			<label class="col-4">Répéter le mot de passe :</label> <input
				type="password" class="form-control col-8"
				placeholder="Répéter le mot de passe" maxlength="255"
				name="confirmationMotDePasse" required>
		</div>
		<div class="mt-5 mx-auto row justify-content-around col-md-6">
			<a href="${pageContext.request.contextPath}/ajoutUtilisateur"
				class="btn btn-primary">Se connecter</a><a href="${pageContext.request.contextPath}/login"
				class="btn btn-primary">Créer un compte</a> <input
				class="btn btn-primary" type="submit" value="Modifier">
		</div>
	</form>
	<div class="mt-5 row justify-content-center"></div>
</div>
<%@ include file="partial/footer.jspf"%>
