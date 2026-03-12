<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<acme:form>
	<acme:form-textbox code="authenticated.part.form.label.name" path="name"/>
	<acme:form-textarea code="authenticated.part.form.label.description" path="description"/>
	<acme:form-money code="authenticated.part.form.label.cost" path="cost"/>
	<acme:form-textbox code="authenticated.part.form.label.kind" path="kind"/>

	<acme:button code="authenticated.part.form.button.showInvention" action="/authenticated/invention/show?id=${inventionId}"/>
</acme:form>