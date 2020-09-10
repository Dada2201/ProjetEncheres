<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <title>Titre de la page</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="script.js"></script>
</head>

<body>
    <header>
        <div class="container">
            <div class="row justify-content-between">
                <p>ENI-Encheres</p>
            </div>
        </div>
    </header>
    <div class="container">
        <div class="row justify-content-center">
            <h1>Nouvelle vente</h1>
        </div>
        <div class="row">
            <div class="col-4">
                <img src="https://pbs.twimg.com/profile_images/790942822853640194/bvZIVYNp.jpg" height="250px" weight="250px" />
            </div>
            <form class="col-8" action="ajoutArticle" method="post">
                <div class="form-group row">
                    <label class="col-3 col-form-label">Article :</label>
                    <input type="text" required name="nom"/>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Description :</label>
                    <textarea rows="5" required name="description"></textarea>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Catégories </label>
                    <div class="col-9"> 
                    	<select class="form-control" required name="categorie">
							<c:forEach var="c" items="${categories}">
								<option value='<c:out value="${c.noCategorie}"/>'><c:out value="${c.libelle}"/></option>
							</c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Photo de l'article</label>
                    <input type="file" accept="image/png, image/jpeg" name="photoArticle"/>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Mise à prix :</label>
                    <input type="number" name="prix"/>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Début de l'enchère :</label>
                    <input type="date" required name="dateDebutEnchere"/>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Fin de l'enchère :</label>
                    <input type="date" required name="dateFinEnchere"/>
                </div>
                <div>
                    <fieldset class="border p-2">
                        <legend class="w-auto">Retrait</legend>

                        <div class="form-group row">
                            <label class="col-3 col-form-label">Rue :</label>
                            <input type="text" required name="rue"/>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Code postal :</label>
                            <input type="text" required name="codePostal"/>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Ville :</label>
                            <input type="text" required name="ville"/>
                        </div>
                    </fieldset>
                </div>
                <div>
                    <div class="mt-5 row justify-content-between">
                        <input type="submit" class="btn btn-primary" value="Enregistrer"/>
                        <a href="accueil" class="btn btn-primary">Annuler</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>

</html>
