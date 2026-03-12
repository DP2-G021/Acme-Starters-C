<%@page language="java"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.sponsorship.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="authenticated.sponsorship.form.label.name" path="name" readonly="true"/>
	<acme:form-textarea code="authenticated.sponsorship.form.label.description" path="description" readonly="true"/>
	<acme:form-moment code="authenticated.sponsorship.form.label.start-moment" path="startMoment" readonly="true"/>
	<acme:form-moment code="authenticated.sponsorship.form.label.end-moment" path="endMoment" readonly="true"/>
	<acme:form-url code="authenticated.sponsorship.form.label.more-info" path="moreInfo" readonly="true"/>
	<acme:form-double code="authenticated.sponsorship.form.label.months-active" path="monthsActive" readonly="true"/>
	<acme:form-money code="authenticated.sponsorship.form.label.total-money" path="totalMoney" readonly="true"/>
	<acme:form-checkbox code="authenticated.sponsorship.form.label.draft-mode" path="draftMode" readonly="true"/>

	<acme:button code="authenticated.sponsorship.form.button.donations" action="/authenticated/donation/list?sponsorshipId=${id}"/>
	
</acme:form>