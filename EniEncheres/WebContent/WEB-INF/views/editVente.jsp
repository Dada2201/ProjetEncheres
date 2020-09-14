<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="partial/header/default.jspf" %>
    <div class="container py-5">
        <div class="row justify-content-center">
            <h1>Nouvelle vente</h1>
        </div>
        <div class="row">
            <div class="col-4">
                <img src="https://pbs.twimg.com/profile_images/790942822853640194/bvZIVYNp.jpg" height="250px" weight="250px" />
            </div>
            <form class="col-8" action="editArticle" method="post">
                <div class="form-group row">
                    <label class="col-3 col-form-label">Article :</label>
                    <input type="text" value="${article.nomArticle}" required name="nom"/>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Description :</label>
                    <textarea rows="5" required name="description">${article.description}</textarea>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Catégories </label>
                    <div class="col-9"> 
                    	<select class="form-control" name="categorie">
                    		<option value="${article.categorie.noCategorie}" selected>${article.categorie.libelle}</option>
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
                    <input type="number" value="${article.prixInitial }" name="prix"/>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Début de l'enchère :</label>
                    <input type="date" value="${article.dateDebut}" required name="dateDebutEnchere"/>
                </div>
                <div class="form-group row">
                    <label class="col-3 col-form-label">Fin de l'enchère :</label>
                    <input type="date" value="${article.dateFin}" required name="dateFinEnchere"/>
                </div>
                <div>
                    <fieldset class="border p-2">
                        <legend class="w-auto">Retrait</legend>

                        <div class="form-group row">
                            <label class="col-3 col-form-label">Rue :</label>
                            <input type="text" value="${retrait.rue }" name="rue"/>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Code postal :</label>
                            <input type="text" value="${retrait.codePostal}" name="codePostal"/>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label">Ville :</label>
                            <input type="text" value="${retrait.ville}" name="ville"/>
                        </div>
                    </fieldset>
                </div>
                <div>
                    <div class="mt-5 row justify-content-between">
                        <input type="submit" class="btn btn-primary" value="Enregistrer"/>
                        <a href="accueil" class="btn btn-primary">Annuler</a>
                        <a href="cancelArticle?idArticle=${article.noArticle }" class="btn btn-primary">Annuler la vente</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
<%@ include file="partial/footer.jspf" %>
