<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${logged}">
<%@ include file="partial/header/connected.jspf" %>
	</c:if>
<c:if test="${logged}"> <!--  TODO change for test !logged or logged -->
<%@ include file="partial/header/notConnected.jspf" %>

	</c:if>
<body>

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
                    <p>Catï¿½gories :</p>

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
        	<form>
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
			</form>	
	</c:if>
    </div>
    
    <div class="container">
        <div class="row justify-content-around">
        	<c:forEach items="${listeEncheres}" var="enchere">
				<%@ include file="partial/article.jspf" %>
			</c:forEach>
        </div>
    </div>
<%@ include file="partial/footer.jspf" %>
