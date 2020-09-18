<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="partial//header.jspf"%>
<div class="container py-5">
	<div class="row justify-content-center">
		<h1>${h1}</h1>
	</div>
	<div class="row">
		<div class="col-md-3">
			<img src="${article.img}" height="250px" width="250px" />
		</div>
		<div class="col-md-9">
			<%@ include file="partial/descriptionArticle.jspf"%>
			<%@ include file="partial/descriptionRetraitVendeur.jspf"%>
		</div>
	</div>
	<c:if
		test="${utilisateursEncheres!=null && utilisateursEncheres.size()!=0 }">
		<div>
			<div class="row justify-content-center">
				<h2>La liste des personnes qui ont enchéri</h2>
			</div>
			<ul>
				<c:forEach var="u" items="${utilisateursEncheres}">
					<li>${u.pseudo}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
</div>
<%@ include file="partial/footer.jspf"%>