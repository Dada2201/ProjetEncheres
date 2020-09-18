<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="partial//header.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center">
		<h1>Nouvelle vente</h1>
	</div>
	<div class="row">
		<div class="col-md-3">
			<img id="imgOut"
				src="<c:url value="/resources/img/articles/article.png" />"
				height="250px" width="250px" />
		</div>
		<form class="col-md-9" action="ajoutArticle" method="post"
			enctype="multipart/form-data">
			<div class="form-group row">
				<label class="col-md-3 form-label">Article</label> <input type="text"
					pattern="^[a-zA-Z0-9]+(?:[\s-][a-zA-Z0-9]+)*$" required name="nom"
					class="form-control col-md-9" autofocus />
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Description</label>
				<textarea rows="5" class="form-control col-md-9" required name="description"></textarea>
			</div>
			<div class="form-group row">
				<label class="col-3 form-label">Catégories </label>
				<div class="col-md-9">
					<select class="form-control" required name="categorie">
						<c:forEach var="c" items="${categories}">
							<option value='<c:out value="${c.noCategorie}"/>'><c:out
									value="${c.libelle}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Photo de l'article</label> <input
					id="imgIn" type="file" accept="image/png, image/jpeg, image/jpg"
					name="photoArticle" class="col-md-9"/>
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Mise à prix</label> <input
					type="number" class="form-control col-md-9" min="1" name="prix" required />
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Début de l'enchère</label> <input
					type="date" class="form-control col-md-9" required name="dateDebutEnchere" />
			</div>
			<div class="form-group row">
				<label class="col-md-3 form-label">Fin de l'enchère</label> <input
					type="date" class="form-control col-md-9" required name="dateFinEnchere" />
			</div>
			<div>
				<fieldset class="border p-4">
					<legend class="w-auto">Retrait</legend>

					<div class="form-group row">
						<label class="col-md-3 form-label">Rue</label> <input type="text"
							class="form-control col-md-9"
							pattern="^[a-zA-Z0-9]+(?:[\s-][a-zA-Z0-9]+)*$" required
							name="rue" />
					</div>
					<div class="form-group row">
						<label class="col-md-3 form-label">Code postal</label> <input
							type="text" class="form-control col-md-9" pattern="^[0-9_]*$"
							maxlength="5" required name="codePostal" />
					</div>
					<div class="form-group row">
						<label class="col-md-3 form-label">Ville</label> <input type="text"
							class="form-control col-md-9" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$"
							required name="ville" />
					</div>
				</fieldset>
			</div>
			<div>
				<div class="mt-5 row justify-content-between">
					<input type="submit" class="btn btn-primary" value="Enregistrer" />
					<a href="accueil" class="btn btn-primary">Annuler</a>
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