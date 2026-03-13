<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<acme:form>
	<acme:form-textbox code="inventor.part.form.label.name" path="name"/>
	<acme:form-textarea code="inventor.part.form.label.description" path="description"/>
	<acme:form-money code="inventor.part.form.label.cost" path="cost"/>
	<acme:form-textbox code="inventor.part.form.label.kind" path="kind"/>

	<acme:button code="inventor.part.form.button.showInvention" action="/inventor/invention/show?id=${inventionId}"/>
</acme:form>