<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${logged}">
	<%@ include file="partial/header/connected.jspf"%>
</c:if>
<c:if test="${!logged}">
	<%@ include file="partial/header/notConnected.jspf"%>

</c:if>
<body>

	<div class="container py-5">
		<div class="row justify-content-center">
			<h1>Liste des enchères</h1>
		</div>
		<div class="row">
			<div class="col-md-10">
				<h2>Filtres</h2>
				<div class="row justify-content-around">
					<div class="col-md-8">
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">&#x1F50D;</span>
							</div>
							<input type="text" class="form-control"
								placeholder="Le nom de l'aticle contient" id="searchContent">
						</div>

						<div class="form-group row">
							<label for="inputPassword" class="col-3 col-form-label">Catégories
								:</label>
							<div class="col-9">
								<select id="categories" class="form-control" required
									name="categorie">
									<option>Toutes</option>

									<c:forEach var="c" items="${categories}">
										<option value='<c:out value="${c.noCategorie}"/>'><c:out
												value="${c.libelle}" /></option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

				</div>
				<c:if test="${logged}">
					<div>
						<div class="row justify-content-around">
							<div class="col-md-4">
								<div class="radio">
									<label><input type="radio" id="achats" checked
										name="optradio">Achats</label>
								</div>
								<div class="checkbox pl-5">
									<label><input type="checkbox" id="encheresouvertes"
										value="">Enchères ouvertes</label>
								</div>
								<div class="checkbox pl-5">
									<label><input type="checkbox" id="encheresencours"
										value="">Mes enchères en cours</label>
								</div>
								<div class="checkbox pl-5">
									<label><input type="checkbox" id="enchereswin" value="">Mes
										enchères remportées</label>
								</div>
							</div>
							<div class="col-md-4">
								<div class="radio">
									<label><input type="radio" id="ventes" name="optradio">Mes
										ventes</label>
								</div>
								<div class="checkbox pl-5">
									<label><input type="checkbox" id="ventesencours"
										value="" disabled>Mes ventes en cours</label>
								</div>
								<div class="checkbox pl-5">
									<label><input type="checkbox" id="ventesnon" value=""
										disabled>Ventes non débutées</label>
								</div>
								<div class="checkbox pl-5">
									<label><input type="checkbox" id="ventesend" value=""
										disabled>Ventes terminées</label>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			<div>
				<div class="col-md-6 justify-content-center align-self-center">
					<button id="searchButton" class="btn btn-primary">Rechercher</button>
				</div>
			</div>
		</div>

		<div id="liste" class="container">
			<c:if test="${listeArticles != null}">
				<div class="row justify-content-center">
					<h2>Toutes les encheres</h2>
				</div>
			</c:if>
			<div class="row justify-content-around">
				<c:forEach items="${listeArticles}" var="article">
					<%@ include file="partial/article.jspf"%>
				</c:forEach>
			</div>
			<c:if test="${(nbItems/6) > 1}">
				<nav class="row justify-content-center pt-5">
					<ul id="pagination" class="pagination">
						<c:forEach var="i" begin="1" end="${(nbItems/6+1)}">
							<c:if test="${i!=(nbItems/6+1) || nbItems%6!=0}">
								<li value="${i}" class="page-item"><p class="page-link">${i}</p></li>
							</c:if>
						</c:forEach>
					</ul>
				</nav>
			</c:if>
		</div>
	</div>

	<script>
		var checkedCheckbox = [];
		var noCategorie = 0;
		var liste = null;

		$('#categories').on('change', function() {
			noCategorie = this.value;
			$.ajax({
				url : 'ServletHome',
				data : {
					categorie : this.value
				},
				success : function(data) {
					liste = "";
					liste = $(data).find('#liste').html();
					$('#liste').filter(function() {
						return $(this).val() == "";
					});
					$('#liste').html(liste);
				}
			});
		});

		$('#searchButton').on('click', function() {
			$.ajax({
				url : 'ServletHome',
				data : {
					search : $("#searchContent").val()
				},
				success : function(data) {
					liste = "";
					liste = $(data).find('#liste').html();
					$('#liste').filter(function() {
						return $(this).val() == "";
					});
					$('#liste').html(liste);
				}
			});
		});

		$('.pagination li').on('click', function() {
			alert('ici');
			$.ajax({
				url : 'ServletHome',
				type : 'GET',
				data : {
					page : this.value
				},
				success : function(data) {
					liste = "";
					liste = $(data).find('#liste').html();
					alert(liste)
					$('#liste').filter(function() {
						return $(this).val() == "";
					});
					$('#liste').html(liste);
				}
			});
		});

		$(document).on("click", "input[type='checkbox']", function(event) {
			if ($("#encheresouvertes").is(':checked')) {
				checkedCheckbox.push($("#encheresouvertes").attr('id'))
			}
			if ($("#enchereswin").is(':checked')) {
				checkedCheckbox.push($("#enchereswin").attr('id'))
			}
			if ($("#encheresencours").is(':checked')) {
				checkedCheckbox.push($("#encheresencours").attr('id'))
			}
			if ($("#ventesnon").is(':checked')) {
				checkedCheckbox.push($("#ventesnon").attr('id'))
			}
			if ($("#ventesencours").is(':checked')) {
				checkedCheckbox.push($("#ventesencours").attr('id'))
			}
			if ($("#ventesend").is(':checked')) {
				checkedCheckbox.push($("#ventesend").attr('id'))
			}

			var json = JSON.stringify(checkedCheckbox);
			checkedCheckbox.length = 0;

			$.ajax({
				url : 'ServletHome?checkbox=' + json,
				type : 'GET',
				success : function(data) {
					liste = "";
					liste = $(data).find('#liste').html();
					$('#liste').filter(function() {
						return $(this).val() == "";
					});
					$('#liste').html(liste);
				}
			});
		});
		json = null;
	</script>
	<%@ include file="partial/footer.jspf"%>