<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partial/header/header.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center mb-5">
		<h1>Mon compte</h1>
	</div>
	<div class="justify-content-center mx-auto col-md-6">
		<div class="form-group row">
			<h4 class="mr-5">Pseudo</h4>
			<p>${utilisateur.pseudo}</p>
		</div>
		<div class="form-group row">
			<h4 class="mr-5">Prénom</h4>
			<p>${utilisateur.prenom }</p>
		</div>
		<div class="form-group row">
			<h4 class="mr-5">Nom</h4>
			<p>${utilisateur.nom }</p>
		</div>
		<div class="form-group row">
			<h4 class="mr-5">Email</h4>
			<p>
				<a href="mailto:${utilisateur.email}">${utilisateur.email}</a>
			</p>
		</div>
		<div class="form-group row">
			<h4 class="mr-5">Téléphone</h4>
			<p>
				<a href="tel:${utilisateur.telephone }">${utilisateur.telephone }</a>
			</p>
		</div>
		<div class="form-group row">
			<h4 class="mr-5">Code Postal</h4>
			<p>${utilisateur.codePostal }</p>
		</div>
		<div class="form-group row">
			<h4 class="mr-5">Rue</h4>
			<p>${utilisateur.rue}</p>
		</div>
		<div class="form-group row">
			<h4 class="mr-5">Ville</h4>
			<p>${utilisateur.ville}</p>
		</div>
	</div>

	<div class="mt-5 row justify-content-around">
		<a href="modificationProfil" class="btn btn-primary">Modifier</a>
	</div>
</div>

<%@ include file="partial/footer.jspf"%>