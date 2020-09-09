<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="partial/header/default.jsp" %>
    <div class="container">
        <div class="row justify-content-center">
            <h1>Mon profil</h1>
        </div>
        
        <form action="ajoutUtilisateur" method="post">
            <div class="row">
                <div class="col">
                    <div class="form-group row">
                        <label class="col-4">Pseudo :</label>
                        <input type="text" class="form-control col-8" name="pseudo" required>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Prénom :</label>
                        <input type="text" class="form-control col-8" name ="prenom" required>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Téléphone :</label>
                        <input type="tel" class="form-control col-8" name="telephone">
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Code Postal :</label>
                        <input type="text" class="form-control col-8" name="codePostal" required>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Mot de passe :</label>
                        <input type="password" class="form-control col-8" name="motDePasse" required>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label class="col-4">Nom :</label>
                        <input type="text" class="form-control col-8" name="nom" required>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Email :</label>
                        <input type="email" class="form-control col-8" name="email" required>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Rue :</label>
                        <input type="text" class="form-control col-8" name="rue" required>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Ville :</label>
                        <input type="text" class="form-control col-8" name="ville" required>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Confirmation :</label>
                        <input type="password" class="form-control col-8" name="confirmationMotDePasse" required>
                    </div>
                </div>
            </div>
            <div class="mt-5 row justify-content-around">
                <button type="submit" class="btn btn-primary">Créer un compte</button>
                <button type="submit" class="btn btn-primary">Annuler</button>
            </div>
        </form>
    </div>
<%@ include file="partial/footer.jsp" %>
