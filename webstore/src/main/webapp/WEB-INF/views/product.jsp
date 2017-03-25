<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<script	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>
	<script src="/webstore/resource/js/controllers.js"></script>
<title>Produkt</title>
</head>
<body>
	<section class="container" ng-app="cartApp">
		<div class="row">
			<div class="col-md-5">
				<h3>${product.name}</h3>
				<img src="<c:url value="/images/${product.productId}.png"/>"alt="image" style="width: 80%" />
				<p>${product.description}</p>
				<p>

				<a href="<c:url value='/products/product/pdf/?id=${product.productId}'/>"> Instrukcja PDF</a>

				</p>
				<p>
					<strong>Kod produktu: </strong><span class="label label-warning">${product.productId}</span>
				</p>
				<p>
					<strong>Producent</strong>: ${product.manufacturer}
				</p>
				<p>
					<strong>Kategoria</strong>: ${product.category}
				</p>
				<p>
					<strong>Liczba sztuk w magazynie</strong>: ${product.unitsInStock}
				</p>
				<h4>${product.unitPrice}PLN</h4>
				<p ng-controller="cartCtrl">
					<a href="#" class="btn btn-warning btn-large" ng-click="addToCart('${product.productId}')"> 
<span class="glyphicon-shopping-cart glyphicon"></span> Zamów teraz </a>
<a href="<spring:url value="/cart" />" class="btn btn-default">
	<span class="glyphicon-hand-right glyphicon"></span> Koszyk
</a>

 <a href="<spring:url value="/products" />" class="btn btn-default">
						<span class="glyphicon-hand-left glyphicon"></span> wstecz
					</a>

				</p>

			</div>
		</div>
	</section>
</body>
</html>