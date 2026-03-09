<%@page language="java"%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.part.list.label.name" path="name" width="25%"/>
	<acme:list-column code="authenticated.part.list.label.description" path="description" width="40%"/>
	<acme:list-column code="authenticated.part.list.label.cost" path="cost" width="20%"/>
	<acme:list-column code="authenticated.part.list.label.kind" path="kind" width="15%"/>
</acme:list>