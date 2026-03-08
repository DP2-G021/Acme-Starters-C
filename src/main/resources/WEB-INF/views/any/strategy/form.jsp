<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox code="any.strategy.form.label.ticker" path="ticker" readonly="true"/>
    <acme:form-textbox code="any.strategy.form.label.name" path="name" readonly="true"/>
    <acme:form-textarea code="any.strategy.form.label.description" path="description" readonly="true"/>
    <acme:form-moment code="any.strategy.form.label.start-moment" path="startMoment" readonly="true"/>
    <acme:form-moment code="any.strategy.form.label.end-moment" path="endMoment" readonly="true"/>
    <acme:form-url code="any.strategy.form.label.more-info" path="moreInfo" readonly="true"/>
    <acme:form-double code="any.strategy.form.label.months-active" path="monthsActive" readonly="true"/>
    <acme:form-double code="any.strategy.form.label.expected-percentage" path="expectedPercentage" readonly="true"/>
    
    <acme:button code="any.strategy.form.button.tactics" action="/any/tactic/list?strategyId=${id}"/>
    <acme:button code="any.strategy.form.button.fundraiser" action="/any/fundraiser/show?strategyId=${id}"/>
    <acme:form-return code="any.strategy.form.button.return"/>
</acme:form>
