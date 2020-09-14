<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="partial/header/connected.jspf" %>
        <div class="container py-5">
                    <div class="form-group row justify-content-center">
                        <label>Pseudo : ${utilisateur.pseudo}</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Prénom : ${utilisateur.prenom }</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Téléphone : ${utilisateur.telephone }</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Code Postal : ${utilisateur.codePostal }</label>
                    </div>          
                    <div class="form-group row justify-content-center">
                        <label>Nom : ${utilisateur.nom }</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Email : ${utilisateur.email}</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Rue : ${utilisateur.rue}</label>
                    </div>
                    <div class="form-group row justify-content-center">
                        <label>Ville : ${utilisateur.ville}</label>
                    </div>

            <div class="mt-5 row justify-content-around">
                 <a href="modificationProfil" class="btn btn-primary">Modifier</a> 
            </div>
    </div>
    
<%@ include file="partial/footer.jspf" %>