<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="authenticated.invention.form.label.name" path="name"/>
	<acme:form-textarea code="authenticated.invention.form.label.description" path="description"/>
	<acme:form-moment code="authenticated.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="authenticated.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="authenticated.invention.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double code="authenticated.invention.form.label.monthsActive" path="monthsActive"/>
	<acme:form-money code="authenticated.invention.form.label.cost" path="cost"/>
	<acme:form-checkbox code="authenticated.invention.form.label.draftMode" path="draftMode"/>

	<acme:button code="authenticated.invention.form.button.listParts" action="/authenticated/part/list?inventionId=${id}"/>
</acme:form>