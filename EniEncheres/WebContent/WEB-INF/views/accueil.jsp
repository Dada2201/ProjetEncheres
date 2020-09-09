<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <title>Titre de la page</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="script.js"></script>
</head>

<body>
    <header>
        <div class="container">
            <div class="row justify-content-between">
                <p>ENI-Encheres</p>
                <a href="login">S'inscrire - Se connecter</a>
            </div>
        </div>
    </header>
    <div class="container">
        <div class="row justify-content-center">
            <h1>Liste des enchères</h1>
        </div>
        <p>Filtres :</p>
        <div class="row justify-content-around">
            <div class="col-6">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">&#x1F50D;</span>
                    </div>
                    <input type="text" class="form-control" placeholder="Le nom de l'aticle contient">
                </div>
                <div class="row form-group">
                    <p>Catégories :</p>

                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-3 col-form-label">Catégories :</label>
                    <div class="col-9"> <select class="form-control">
                            <option>Toutes</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-6">
                <button class="h-50 w-50">Rechercher</button>
            </div>
            
        </div>
        <c:if test="${logged}">
        	<div class="row justify-content-around">
        		<div class="col-4">
      		  		<div class="radio">
  						<label><input type="radio" id="achats" checked="true" name="optradio">Achats</label>
					</div>
	            	 <div class="checkbox">
						  <label><input type="checkbox" id="encheresouvertes" value="">Enchères ouvertes</label>
					 </div>
					 <div class="checkbox">
						  <label><input type="checkbox" id="encheresencours" value="">Mes enchères en cours</label>
					 </div>
					 <div class="checkbox">
						  <label><input type="checkbox" id="enchereswin" value="">Mes enchères remportées</label>
					</div>
				</div>
				<div class="col-4">
      		  		<div class="radio">
  						<label><input type="radio" id="ventes" name="optradio">Mes ventes</label>
					</div>				
	            	 <div class="checkbox">
						  <label><input type="checkbox" id="ventesencours" value="" disabled>Mes ventes en cours</label>
					 </div>
					 <div class="checkbox">
						  <label><input type="checkbox" id="ventesnon" value="" disabled>Ventes non débutées</label>
					 </div>
					 <div class="checkbox">
						  <label><input type="checkbox" id="ventesend" value="" disabled=>Ventes terminées</label>
					</div>
				</div>
			</div> 	
	</c:if>
    </div>
    
    <div class="container">
        <div class="row justify-content-around">
        	<c:forEach items="${listeEncheres}" var="enchere">
    			<div class="row border col-6 align-items-center justify-content-around">
                <div class="">
                    <img src="https://pbs.twimg.com/profile_images/790942822853640194/bvZIVYNp.jpg" height="100px" weight="100px" />
                </div>
                <div>
                    <p><u>${enchere.article.nomArticle}</u></p>
                    <p>Prix : ${enchere.article.prixInitial}</p>
                    <p>Fin de l'enchère: ${enchere.article.dateFin}</p>
                    <p>Vendeur: ${enchere.article.utilisateur.pseudo}</p>
                </div>
            </div>
			</c:forEach>
            <div class="row border col-6 align-items-center justify-content-around">
                <div class="">
                    <img src="https://pbs.twimg.com/profile_images/790942822853640194/bvZIVYNp.jpg" height="100px" weight="100px" />
                </div>
                <div>
                    <p><u>PC Gamer pour travailler</u></p>
                    <p>Prix: 210 points</p>
                    <p>Fin de l'enchère: 10/08/2018</p>
                    <p>Vendeur: jojo44</p>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$('#achats').click(function() {
	   if($('#achats').is(':checked')) { 
		    $( "#encheresouvertes").prop("disabled", false);
		    $( "#encheresouvertes").prop("checked", true);
		    $( "#enchereswin").prop("disabled", false);
		    $( "#encheresencours").prop("disabled", false);
		    $( "#ventesnon").prop("disabled", true);
		    $( "#ventesencours").prop("disabled", true);
		    $( "#ventesend").attr("disabled", true);
		    $( "#ventesend").prop('checked', false);
		    $( "#ventesnon").prop('checked', false);
		    $( "#ventesencours").prop('checked', false);


		}
	   });
	$('#ventes').click(function(){
		   if($('#ventes').is(':checked')){
			    $( "#encheresouvertes").prop("disabled", true);
			    $( "#encheresouvertes").prop("checked", false);
			    $( "#enchereswin").prop("disabled", true);
			    $( "#encheresencours").prop("disabled", true);
			    $( "#enchereswin").prop("checked", false);
			    $( "#encheresencours").prop("checked", false);
			    $( "#ventesend").prop('checked', false);
			    $( "#ventesnon").prop('checked', false);
			    $( "#ventesencours").prop('checked', true);
			    $( "#ventesend").prop("disabled", false);
			    $( "#ventesnon").prop("disabled", false);
			    $( "#ventesencours").prop("disabled", false);
	}
	});


})
</script>
</html>
