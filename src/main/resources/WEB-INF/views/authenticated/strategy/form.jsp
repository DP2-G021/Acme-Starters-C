<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:set var="readOnlyStrategy" value="${_command == 'show' && draftMode == false}"/>
	<acme:form-textbox code="authenticated.strategy.form.label.ticker" path="ticker" readonly="${readOnlyStrategy}"/>
	<acme:form-textbox code="authenticated.strategy.form.label.name" path="name" readonly="${readOnlyStrategy}"/>
	<acme:form-textarea code="authenticated.strategy.form.label.description" path="description" readonly="${readOnlyStrategy}"/>
	<acme:form-moment code="authenticated.strategy.form.label.start-moment" path="startMoment" readonly="${readOnlyStrategy}"/>
	<acme:form-moment code="authenticated.strategy.form.label.end-moment" path="endMoment" readonly="${readOnlyStrategy}"/>
	<acme:form-url code="authenticated.strategy.form.label.more-info" path="moreInfo" readonly="${readOnlyStrategy}"/>
	<acme:form-textbox code="authenticated.strategy.form.label.draft-mode" path="draftModeDisplay" readonly="true"/>
	<acme:form-double code="authenticated.strategy.form.label.months-active" path="monthsActive" readonly="true"/>
	<acme:form-double code="authenticated.strategy.form.label.expected-percentage" path="expectedPercentage" readonly="true"/>

	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="authenticated.strategy.form.button.tactics" action="/authenticated/tactic/list?strategyId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="authenticated.strategy.form.button.tactics" action="/authenticated/tactic/list?strategyId=${id}"/>
			<acme:submit code="authenticated.strategy.form.button.update" action="/authenticated/strategy/update"/>
			<acme:submit code="authenticated.strategy.form.button.delete" action="/authenticated/strategy/delete"/>
			<acme:submit code="authenticated.strategy.form.button.publish" action="/authenticated/strategy/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.strategy.form.button.create" action="/authenticated/strategy/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
