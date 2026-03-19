<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:set var="readOnlyTactic" value="${_command == 'show' && draftMode == false}"/>
	<acme:form-textbox code="authenticated.tactic.form.label.name" path="name" readonly="${readOnlyTactic}"/>
	<acme:form-textarea code="authenticated.tactic.form.label.notes" path="notes" readonly="${readOnlyTactic}"/>
	<acme:form-double code="authenticated.tactic.form.label.expected-percentage" path="expectedPercentage" readonly="${readOnlyTactic}"/>
	<acme:form-select code="authenticated.tactic.form.label.kind" path="kind" choices="${kinds}" readonly="${readOnlyTactic}"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="authenticated.tactic.form.button.update" action="/authenticated/tactic/update"/>
			<acme:submit code="authenticated.tactic.form.button.delete" action="/authenticated/tactic/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.tactic.form.button.create" action="/authenticated/tactic/create?strategyId=${strategyId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
