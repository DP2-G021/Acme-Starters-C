<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.strategy.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="authenticated.strategy.form.label.name" path="name" readonly="true"/>
	<acme:form-textarea code="authenticated.strategy.form.label.description" path="description" readonly="true"/>
	<acme:form-moment code="authenticated.strategy.form.label.start-moment" path="startMoment" readonly="true"/>
	<acme:form-moment code="authenticated.strategy.form.label.end-moment" path="endMoment" readonly="true"/>
	<acme:form-url code="authenticated.strategy.form.label.more-info" path="moreInfo" readonly="true"/>
	<acme:form-checkbox code="authenticated.strategy.form.label.draft-mode" path="draftMode" readonly="true"/>
	<acme:form-double code="authenticated.strategy.form.label.months-active" path="monthsActive" readonly="true"/>
	<acme:form-double code="authenticated.strategy.form.label.expected-percentage" path="expectedPercentage" readonly="true"/>
	<acme:button code="authenticated.strategy.form.button.tactics" action="/authenticated/tactic/list?strategyId=${id}"/>
</acme:form>
