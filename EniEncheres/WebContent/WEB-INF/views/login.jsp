<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="partial/header/default.jspf" %>
    <div class="container">
        <form class="col-12" action="login" method="post">
            <div class="form-group row mx-auto col-6">
                <label class="col-4">Identifiant :</label>
                <input type="text" class="form-control col-8" placeholder="Pseudo" name="pseudo" required>
            </div>
            <div class="form-group row mx-auto col-6">
                <label class="col-4">Mot de passe :</label>
                <input type="password" class="form-control col-8" placeholder="Mot de passe" name="motDePasse" required>
            </div>
            <div class="row mx-auto col-6">
                <button type="submit" class="btn btn-primary mr-auto ">Connexion</button>
                <div class="mr-auto">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" name="souvenir">
                        <label class="form-check-label">Se souvenir de moi</label>
                    </div>
                    <a href="">Mot de passe oublié</a>
                </div>
            </div>
        </form>
    </div>
    <div class="mt-5 row justify-content-center">
        <button type="submit" class="btn btn-primary">Créer un compte</button>
    </div>
<%@ include file="partial/footer.jspf" %>
