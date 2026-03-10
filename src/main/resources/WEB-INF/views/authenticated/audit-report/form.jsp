<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.audit-report.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="authenticated.audit-report.form.label.name" path="name" readonly="true"/>
	<acme:form-textarea code="authenticated.audit-report.form.label.description" path="description" readonly="true"/>
	<acme:form-moment code="authenticated.audit-report.form.label.startMoment" path="startMoment" readonly="true"/>
	<acme:form-moment code="authenticated.audit-report.form.label.endMoment" path="endMoment" readonly="true"/>
	<acme:form-url code="authenticated.audit-report.form.label.moreInfo" path="moreInfo" readonly="true"/>
	<acme:form-checkbox code="authenticated.audit-report.form.label.draftMode" path="draftMode" readonly="true"/>
	<acme:form-double code="authenticated.audit-report.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="authenticated.audit-report.form.label.hours" path="hours" readonly="true"/>
</acme:form>