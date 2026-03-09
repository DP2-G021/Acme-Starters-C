<%@page language="java"%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox code="any.part.form.label.name" path="name"/>
	<acme:form-textarea code="any.part.form.label.description" path="description"/>
	<acme:form-money code="any.part.form.label.cost" path="cost"/>
	<acme:form-textbox code="any.part.form.label.kind" path="kind"/>

	<acme:submit code="authenticated.part.form.button.show-invention" action="/authenticated/invention/show?id=${inventionId}"/>
</acme:form>