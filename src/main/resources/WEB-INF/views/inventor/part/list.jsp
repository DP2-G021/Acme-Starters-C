<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.part.list.label.name" path="name" width="35%"/>
	<acme:list-column code="inventor.part.list.label.kind" path="kind" width="20%"/>
	<acme:list-column code="inventor.part.list.label.cost" path="cost" width="20%"/>

	<acme:list-hidden path="description"/>
</acme:list>

<jstl:if test="${inventionDraftMode}">
	<acme:button code="inventor.part.list.button.create" action="/inventor/part/create?inventionId=${inventionId}"/>
</jstl:if>

