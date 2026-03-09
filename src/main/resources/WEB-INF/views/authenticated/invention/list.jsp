<%@page language="java"%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.invention.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="authenticated.invention.list.label.name" path="name" width="30%"/>
	<acme:list-column code="authenticated.invention.list.label.months-active" path="monthsActive" width="15%"/>
	<acme:list-column code="authenticated.invention.list.label.cost" path="cost" width="15%"/>
	<acme:list-column code="authenticated.invention.list.label.more-info" path="moreInfo" width="20%"/>
</acme:list>