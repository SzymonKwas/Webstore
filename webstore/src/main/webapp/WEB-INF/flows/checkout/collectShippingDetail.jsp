<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Klient</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Zamowienie</h1>
				<p>Szczegoly zamowienia</p>
			</div>
		</div>
	</section>
	<section class="container">
		<form:form modelAttribute="order.shippingDetail" class="form-horizontal">
			<fieldset>
				<legend>Szczegoly zamowienia</legend>
				
				<div class="form-group">
					<label class="control-label col-lg-2 col-lg-2" for="name" />Imie</label>
					<div class="col-lg-10">
						<form:input id="name" path="name" type="text" class="form:input-large" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2 col-lg-2" for="shippingDate" />Data zamowienia (dd/mm/yyyy)</label>
					<div class="col-lg-10">
						<form:input id="shippingDate" path="shippingDate" type="text" class="form:input-large" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2" for="doorNo">Numer mieszkania</label>
					<div class="col-lg-10">
						<form:input id="doorNo" path="shippingAddress.doorNo" type="text"
							class="form:input-large" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2" for="streetName">Ulica</label>
					<div class="col-lg-10">
						<form:input id="streetName" path="shippingAddress.streetName." type="text"
							class="form:input-large" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2" for="areaName">Miasto</label>
					<div class="col-lg-10">
						<form:input id="areaName" path="shippingAddress.areaName" type="text"
							class="form:input-large" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2" for="state">Wojewodztwo</label>
					<div class="col-lg-10">
						<form:input id="state" path="shippingAddress.state" type="text"
							class="form:input-large" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2" for="country">Kraj</label>
					<div class="col-lg-10">
						<form:input id="country" path="shippingAddress.country" type="text"
							class="form:input-large" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2" for="zipCode">Kod pocztowy</label>
					<div class="col-lg-10">
						<form:input id="zipCode" path="shippingAddress.zipCode" type="text"
							class="form:input-large" />
					</div>
				</div>

				<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}"/>

				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<button id="back" class="btn btn-default" name="_eventId_backToCollectCustomerInfo">Wstecz</button>
						
						<input type="submit" id="btnAdd" class="btn btn-primary"
							value="Add"  name="_eventId_shippingDetailCollected"/>
						<button id="btnCancel" class="btn btn-default" name="_eventId_cancel">Anuluj</button>
					</div>
				</div>

			</fieldset>
		</form:form>
	</section>
</body>
</html>
