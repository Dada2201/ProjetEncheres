<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partial/head.jspf" %>
<body>

    <div class="container">
        <div class="row justify-content-center">
            <h1>Liste des ench�res</h1>
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
                    <p>Cat�gories :</p>

                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-3 col-form-label">Cat�gories :</label>
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
						  <label><input type="checkbox" id="encheresouvertes" value="">Ench�res ouvertes</label>
					 </div>
					 <div class="checkbox">
						  <label><input type="checkbox" id="encheresencours" value="">Mes ench�res en cours</label>
					 </div>
					 <div class="checkbox">
						  <label><input type="checkbox" id="enchereswin" value="">Mes ench�res remport�es</label>
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
						  <label><input type="checkbox" id="ventesnon" value="" disabled>Ventes non d�but�es</label>
					 </div>
					 <div class="checkbox">
						  <label><input type="checkbox" id="ventesend" value="" disabled=>Ventes termin�es</label>
					</div>
				</div>
			</div> 	
	</c:if>
    </div>
    
    <div class="container">
        <div class="row justify-content-around">
        	<c:forEach items="${listeEncheres}" var="enchere">
				<%@ include file="partial/article.jspf" %>
			</c:forEach>
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
