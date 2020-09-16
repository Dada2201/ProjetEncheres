<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="partial/header/connected.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center">
		<h1>Nouvelle vente</h1>
	</div>
	<div class="row">
		<div class="col-md-3">
			<img id="imgOut" src="${article.img}" height="250px" width="250px" />
		</div>
		<form class="col-md-9" action="editArticle" method="post"
			enctype="multipart/form-data">
			<div class="form-group row">
				<label class="col-md-3 form-label">Article</label> <input
					class="form-control col-md-9" type="text"
					pattern="^[a-zA-Z0-9]+(?:[\s-][a-zA-Z0-9]+)*$"
					value="${article.nomArticle}" required name="nom" autofocus />
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Description</label>
				<textarea rows="5" class="form-control col-md-9" required
					name="description">${article.description}</textarea>
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Catégories </label>
				<div class="col-md-9">
					<select class="form-control" required name="categorie">
						<option value="${article.categorie.noCategorie}" selected disable
							hidden>${article.categorie.libelle}</option>
						<c:forEach var="c" items="${categories}">
							<option value='<c:out value="${c.noCategorie}"/>'><c:out
									value="${c.libelle}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Photo de l'article</label> <input
					class="col-md-9" id="imgIn" type="file"
					accept="image/png, image/jpeg, image/jpg" name="photoArticle" />
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Mise à prix</label> <input
					class="form-control col-md-9" type="number"
					value="${article.prixInitial }" name="prix" required />
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Début de l'enchère</label> <input
					class="form-control col-md-9" type="date"
					value="${article.dateDebut}" required name="dateDebutEnchere" />
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Fin de l'enchère</label> <input
					class="form-control col-md-9" type="date"
					value="${article.dateFin}" required name="dateFinEnchere" />
			</div>
			<div>
				<fieldset class="border p-4">
					<legend class="w-auto">Retrait</legend>

					<div class="form-group row">
						<label class="col-md-3 form-label">Rue</label> <input type="text"
							class="form-control col-md-9" type="text"
							pattern="^[a-zA-Z0-9]+(?:[\s-][a-zA-Z0-9]+)*$" required
							value="${retrait.rue }" name="rue" />
					</div>
					<div class="form-group row">
						<label class="col-md-3 form-label">Code postal</label> <input
							class="form-control col-md-9" type="text" pattern="^[0-9_]*$"
							required value="${retrait.codePostal}" maxlength="5"
							name="codePostal" />
					</div>
					<div class="form-group row">
						<label class="col-md-3 form-label">Ville</label> <input
							class="form-control col-md-9" type="text"
							pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$" required
							value="${retrait.ville}" name="ville" />
					</div>
				</fieldset>
			</div>
			<div>
				<div class="mt-5 row justify-content-between">
					<input type="submit" class="btn btn-primary" value="Enregistrer" />
					<a href="accueil" class="btn btn-primary">Annuler</a> <a
						href="cancelArticle?idArticle=${article.noArticle }"
						class="btn btn-primary">Annuler la vente</a>
				</div>
			</div>
		</form>
	</div>
</div>
<%@ include file="partial/footer.jspf"%>

<script>
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#imgOut').attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}

	$("#imgIn").change(function() {
		readURL(this);
	});
</script>