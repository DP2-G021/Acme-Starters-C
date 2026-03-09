<%@page language="java"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="authenticated.invention.form.label.name" path="name"/>
	<acme:form-textarea code="authenticated.invention.form.label.description" path="description"/>
	<acme:form-moment code="authenticated.invention.form.label.start-moment" path="startMoment"/>
	<acme:form-moment code="authenticated.invention.form.label.end-moment" path="endMoment"/>
	<acme:form-url code="authenticated.invention.form.label.more-info" path="moreInfo"/>
	<acme:form-double code="authenticated.invention.form.label.months-active" path="monthsActive"/>
	<acme:form-money code="authenticated.invention.form.label.cost" path="cost"/>
	<acme:form-checkbox code="authenticated.invention.form.label.draft-mode" path="draftMode"/>

	<acme:button code="authenticated.invention.form.button.list-parts" action="/authenticated/part/list?masterId=${id}"/>
</acme:form>