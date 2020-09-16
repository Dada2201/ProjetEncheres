<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partial/header/connected.jspf"%>
<div class="container">
	<div class="row justify-content-center">
		<h1>Mon profil</h1>
	</div>

	<c:if test="${errorMdp}">
		<div class="alert alert-danger" role="alert">Le mot de passe actuel ne correspond pas.</div>
	</c:if>
	
	<form action="<%=request.getContextPath()%>/modificationProfil"
		method="post">
		<div class="row">
			<div class="col-md">
				<div class="form-group row">
					<label class="col-4">Pseudo : </label> <input type="text"
						value="${utilisateur.pseudo}" pattern="^[a-zA-Z0-9_]*$" required class="form-control col-8"
						name="pseudo">
				</div>
				<div class="form-group row">
					<label class="col-4">Nom :</label> <input type="text"
						value="${utilisateur.nom}" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$" required class="form-control col-8" name="nom">
				</div>
				<div class="form-group row">
					<label class="col-4">Prénom :</label> <input type="text"
						value="${utilisateur.prenom}" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$" required class="form-control col-8"
						name="prenom">
				</div>
				<div class="form-group row">
					<label class="col-4">Email :</label> <input type="email"
						value="${utilisateur.email}" required class="form-control col-8"
						name="email">
				</div>
				<div class="form-group row">
					<label class="col-4">Téléphone :</label> <input type="tel"
						value="${utilisateur.telephone}" class="form-control col-8"
						name="telephone">
				</div>
			</div>
			<div class="col-md">
				<div class="form-group row">
					<label class="col-4">Rue :</label> <input type="text"
						value="${utilisateur.rue}" pattern="^[a-zA-Z0-9]+(?:[\s-][a-zA-Z0-9]+)*$" required class="form-control col-8" name="rue">
				</div>
				<div class="form-group row">
					<label class="col-4">Code Postal :</label> <input type="text"
						value="${utilisateur.codePostal}" required maxlength="5" pattern="^[0-9_]*$" class="form-control col-8"
						name="codepostal">
				</div>
				<div class="form-group row">
					<label class="col-4">Ville :</label> <input type="text"
						value="${utilisateur.ville}" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$" required class="form-control col-8"
						name="ville">
				</div>
				<div class="form-group row">
					<label class="col-4">Mot de passe actuel :</label> <input
						type="password" class="form-control col-8" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,}$" required name="mdpactuel">
				</div>
				<div class="form-group row">
					<label class="col-4">Nouveau mot de passe :</label> <input
						type="password" class="form-control col-8" 
						pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,}$"
						onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Respectez le format du mot de passe (1minuscule, 1Majuscule, 1chiffre, 1symbole et min 8chars ' : ''); if(this.checkValidity()) 
                        form.mdpconfirm.pattern = this.value;"
						maxlength="255" name="mdpnouveau" required>
				</div>
				<div class="form-group row">
					<label class="col-4">Confirmation :</label> <input type="password"
						class="form-control col-8" 
						pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,}$"
						onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
						maxlength="255" name="mdpconfirm" required>
				</div>
			</div>
		</div>
		<div class="mt-5 row justify-content-around">
			<button type="submit" name="update_button" class="btn btn-primary">Enregistrer</button>
			<button type="submit" name="delete_button" class="btn btn-primary">Supprimer
				mon compte</button>
		</div>
	</form>
</div>
<%@ include file="partial/footer.jspf"%>
