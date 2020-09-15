<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${logged}">
<%@ include file="partial/header/connected.jspf" %>
	</c:if>
<c:if test="${!logged}"> <!--  TODO change for test !logged or logged -->
<%@ include file="partial/header/notConnected.jspf" %>

	</c:if>
<body>

    <div class="container py-5">
        <div class="row justify-content-center">
            <h1>Liste des enchères</h1>
        </div>
        <div class="row">
        	<div class="col-md-10">
		        	<p>Filtres :</p>
		        <div class="row justify-content-around">
		            <div class="col-md-6">
		                <div class="input-group mb-3">
		                    <div class="input-group-prepend">
		                        <span class="input-group-text">&#x1F50D;</span>
		                    </div>
		                    <input type="text" class="form-control" placeholder="Le nom de l'aticle contient">
		                </div>
		                
		                <div class="form-group row">
		                    <label for="inputPassword" class="col-3 col-form-label">Catégories :</label>
		                    <div class="col-9"> 
		                                        	<select class="form-control" required name="categorie">
		                                        	                            <option>Toutes</option>
		                                        	
									<c:forEach var="c" items="${categories}">
										<option value='<c:out value="${c.noCategorie}"/>'><c:out value="${c.libelle}"/></option>
									</c:forEach>
		                        </select>
		                    </div>
		                </div>
		            </div>
		            
		        </div>
		        <c:if test="${logged}">
		        	<form>
		        	<div class="row justify-content-around">
		        		<div class="col-md-4">
		      		  		<div class="radio">
		  						<label><input type="radio" id="achats" checked="true" name="optradio">Achats</label>
							</div>
			            	 <div class="checkbox pl-5">
								  <label><input type="checkbox" id="encheresouvertes" value="">Enchères ouvertes</label>
							 </div>
							 <div class="checkbox pl-5">
								  <label><input type="checkbox" id="encheresencours" value="">Mes enchères en cours</label>
							 </div>
							 <div class="checkbox pl-5">
								  <label><input type="checkbox" id="enchereswin" value="">Mes enchères remportées</label>
							</div>
						</div>
						<div class="col-md-4">
		      		  		<div class="radio">
		  						<label><input type="radio" id="ventes" name="optradio">Mes ventes</label>
							</div>				
			            	 <div class="checkbox pl-5">
								  <label><input type="checkbox" id="ventesencours" value="" disabled>Mes ventes en cours</label>
							 </div>
							 <div class="checkbox pl-5">
								  <label><input type="checkbox" id="ventesnon" value="" disabled>Ventes non débutées</label>
							 </div>
							 <div class="checkbox pl-5">
								  <label><input type="checkbox" id="ventesend" value="" disabled=>Ventes terminées</label>
							</div>
						</div>
					</div> 
					</form>	
			</c:if>
        	</div>
        	<div>
		           <div class="col-md-6 justify-content-center align-self-center">
		                <button class="btn btn-primary">Rechercher</button>
		            </div>
        	</div>
        </div>
        
    </div>




    <div id="liste" class="container">
        <div class="row justify-content-around">
        	<c:forEach items="${listeArticles}" var="article">
				<%@ include file="partial/article.jspf" %>
			</c:forEach>
        </div>
    </div>
    
    	<script>
	
	var checkedCheckbox = [];
	
	$(document).on( "click", "input[type='checkbox']", function(event) {	
	if($("#encheresouvertes").is(':checked')) {
		checkedCheckbox.push($("#encheresouvertes").attr('id'))
    }
	if($("#enchereswin").is(':checked')) {
		checkedCheckbox.push($("#enchereswin").attr('id'))
    }
	if($("#encheresencours").is(':checked')) {
		checkedCheckbox.push($("#encheresencours").attr('id'))
    }
	if($("#ventesnon").is(':checked')) {
		checkedCheckbox.push($("#ventesnon").attr('id'))
    }
	if($("#ventesencours").is(':checked')) {
		checkedCheckbox.push($("#ventesencours").attr('id'))
    }
	if($("#ventesend").is(':checked')) {
		checkedCheckbox.push($("#ventesend").attr('id'))
	}
	
	var json = JSON.stringify(checkedCheckbox);
	var liste = null;
	checkedCheckbox.length = 0;
	
                $.ajax({
                    url: 'ServletHome?test='+json,
                    type: 'GET',
                    success: function (data) {
						liste = "";
                    	liste = $(data).filter('#liste').html();
						$('#liste').filter(function() { return $(this).val() == ""; });
		            	$('#liste').html(liste);
                    }
                });
	});
	json = null;
        </script>
<%@ include file="partial/footer.jspf" %>
