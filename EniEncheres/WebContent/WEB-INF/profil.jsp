<%@page import="bo.Utilisateur"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
                    <div class="form-group row justify-content-center">
                        <label>Pseudo : </label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Prénom :</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Téléphone :</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Code Postal :</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Mot de passe :</label>
                    </div>                
                    <div class="form-group row justify-content-center">
                        <label>Nom : ${data.id}</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Email :</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Rue :</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Ville :</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Confirmation :</label>
                    </div>
            <div class="mt-5 row justify-content-around">
                 <button type="submit" class="btn btn-primary">Modifier</button> 
            </div>
    </div>
    
</body>
</html>