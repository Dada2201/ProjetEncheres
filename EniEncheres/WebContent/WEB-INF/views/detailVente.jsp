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
            <h1>Détail vente</h1>
        </div>
        <div class="row">
            <div class="col-4">
                <img src="https://pbs.twimg.com/profile_images/790942822853640194/bvZIVYNp.jpg" height="250px" weight="250px" />
            </div>
            <div class="col-8">
                <div class="form-group row">
                    <p>${article.nomArticle}</p>
                </div>
                <div class="form-group row">
                    <p>Description :</p>
                    <p>${article.description}</p>
                </div>
                <div class="form-group row">
                    <p>CatÃ©gorie</p>
                    <p>${categorie.libelle}</p>
                </div>
                <div class="form-group row">
                    <p>Meilleure offre :</p>
                    <p>${enchere.montantEnchere} pts par ${utilisateurEnchere.pseudo}</p>
                </div>
                <div class="form-group row">
                    <p>Mise Ã  prix :</p>
                    <p>185 pts</p>
                </div>
                <div class="form-group row">
                    <p>Fin de l'enchère :</p>
                    <p><fmt:formatDate pattern = "dd/MM/yyyy" value = "${article.dateFin}" /></p>
                </div>
                <div class="form-group row">
                    <p>Retrait :</p>
                    <div>
                        <p>10 allée des Alouettes</p>
                        <p>44800 Saint Herblain</p>
                    </div>
                </div>
                <div class="form-group row">
                    <p>Vendeur :</p>
                    <p>${utilisateurVendeur.pseudo}</p>
                </div>
                <div class="form-group row">
                    <p>Ma proposition :</p>
                    <input type="number" />
                    <button type="submit" class="btn btn-primary">Enchérir</button>
                </div>
            </div>
        </div>
    </div>
</body>

</html>