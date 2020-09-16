<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partial/header/default.jspf"%>
<div class="container py-5">
	<c:if test="${errorPseudo}">
		<div class="alert alert-danger" role="alert">Le pseudo est déjà
			pris, veuillez en prendre un autre.</div>
	</c:if>
	<c:if test="${errorMail}">
		<div class="alert alert-danger" role="alert">Le mail est déjà
			pris, veuillez en prendre un autre.</div>
	</c:if>
	<div class="row justify-content-center">
		<h1>Mon profil</h1>
	</div>

	<form action="ajoutUtilisateur" method="post">
		<div class="row">
			<div class="col-md">
				<div class="form-group row">
					<label class="col-4">Pseudo :</label> <input type="text"
						class="form-control col-8" pattern="^[a-zA-Z0-9_]*$" name="pseudo"
						maxlength="30" required autofocus>
				</div>
				<div class="form-group row">
					<label class="col-4">Nom :</label> <input type="text"
						class="form-control col-8" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$" name="nom"
						maxlength="30" required>
				</div>
				<div class="form-group row">
					<label class="col-4">Prénom :</label> <input type="text"
						class="form-control col-8" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$" name="prenom"
						maxlength="30" required>
				</div>
				<div class="form-group row">
					<label class="col-4">Email :</label> <input type="email"
						class="form-control col-8" maxlength="20" name="email" required>
				</div>
				<div class="form-group row">
					<label class="col-4">Téléphone :</label> <input type="tel"
						class="form-control col-8" pattern="^[0-9_]*$" maxlength="10"
						name="telephone">
				</div>
			</div>
			<div class="col-md">
				<div class="form-group row">
					<label class="col-4">Rue :</label> <input type="text"
						class="form-control col-8" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$"
						maxlength="30" name="rue" required>
				</div>
				<div class="form-group row">
					<label class="col-4">Code Postal :</label> <input type="text"
						class="form-control col-8" pattern="^[0-9_]*$" maxlength="5"
						name="codePostal" required>
				</div>
				<div class="form-group row">
					<label class="col-4">Ville :</label> <input type="text"
						class="form-control col-8" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$" maxlength="30"
						name="ville" required>
				</div>
				<div class="form-group row">
					<label class="col-4">Mot de passe :</label> <input type="password"
						class="form-control col-8"
						pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,}$"
						onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Respectez le format du mot de passe (1minuscule, 1Majuscule, 1chiffre, 1symbole et min 8chars ' : ''); if(this.checkValidity()) 
                        form.confirmationMotDePasse.pattern = this.value;"
						maxlength="255" name="motDePasse" required>
					<!-- Le pattern du mot de passe : 1Maj ,1Min, 1chiffre, 1symbol 8min 255max -->
				</div>
				<div class="form-group row">
					<label class="col-4">Confirmation :</label> <input type="password"
						class="form-control col-8"
						pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,}$"
						onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
						maxlength="255" name="confirmationMotDePasse" required>
				</div>
			</div>
		</div>
		<div class="mt-5 row justify-content-around">
			<button type="submit" class="btn btn-primary">Créer un
				compte</button>
			<a href="${pageContext.request.contextPath}" class="btn btn-primary">Annuler</a>
		</div>
	</form>
</div>
<%@ include file="partial/footer.jspf"%>
