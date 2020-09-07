<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

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
            <h1>Mon profil</h1>
        </div>
        <form action="/ajoutUtilisateur" method="post">
            <div class="row">
                <div class="col">
                    <div class="form-group row">
                        <label class="col-4">Pseudo :</label>
                        <input type="text" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Prénom :</label>
                        <input type="text" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Téléphone :</label>
                        <input type="tel" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Code Postal :</label>
                        <input type="text" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Mot de passe :</label>
                        <input type="password" class="form-control col-8">
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label class="col-4">Nom :</label>
                        <input type="text" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Email :</label>
                        <input type="email" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Rue :</label>
                        <input type="text" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Ville :</label>
                        <input type="text" class="form-control col-8">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Confirmation :</label>
                        <input type="password" class="form-control col-8">
                    </div>
                </div>
            </div>
            <div class="mt-5 row justify-content-around">
                <button type="submit" class="btn btn-primary">Créer un compte</button>
                <button type="submit" class="btn btn-primary">Annuler</button>
            </div>
        </form>
    </div>
</body></html>
