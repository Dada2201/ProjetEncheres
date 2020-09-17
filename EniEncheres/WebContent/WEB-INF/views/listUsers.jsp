<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="partial/header/connected.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center mb-5">
		<h1>Gestion des utilisateur de l'application</h1>
	</div>
	<c:if test="${utilisateurs==null || utilisateurs.size() == 0}">
		<div class="row justify-content-center">
			<p class="text-danger">Il n'y a pas d'utilisateur sur
				l'application !</p>
		</div>
	</c:if>
	<form method="post" action="gestionUtilisateurs"
		onSubmit="if(!confirm('Etes vous sûr de supprimer cet utilisateur ?')){return false;}">
		<ul class="list-group">
			<c:forEach var="u" items="${utilisateurs}">
				<li class="list-group-item"><button title="Supprimer"
						type="submit" class="btn btn-link" name="idUtilisateur"
						value="${u.id}">
						<i class="fas fa-trash-alt text-danger"></i>
					</button> <c:out value="${u.pseudo}" /> - <c:out value="${u.email}" /></li>
			</c:forEach>
		</ul>
	</form>
</div>
<%@ include file="partial/footer.jspf"%>