<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
<jstl:set var="readOnlySponsorship" value="${_command == 'show' && draftMode == false}"/>
	<acme:form-textbox code="authenticated.sponsorship.form.label.ticker" path="ticker" readonly="${readOnlySponsorship}"/>
	<acme:form-textbox code="authenticated.sponsorship.form.label.name" path="name" readonly="${readOnlySponsorship}"/>
	<acme:form-textarea code="authenticated.sponsorship.form.label.description" path="description" readonly="${readOnlySponsorship}"/>
	<acme:form-moment code="authenticated.sponsorship.form.label.start-moment" path="startMoment" readonly="${readOnlySponsorship}"/>
	<acme:form-moment code="authenticated.sponsorship.form.label.end-moment" path="endMoment" readonly="${readOnlySponsorship}"/>
	<acme:form-url code="authenticated.sponsorship.form.label.more-info" path="moreInfo" readonly="${readOnlySponsorship}"/>
	<acme:form-double code="authenticated.sponsorship.form.label.months-active" path="monthsActive" readonly="true"/>
	<acme:form-money code="authenticated.sponsorship.form.label.total-money" path="totalMoney" readonly="true"/>
	<acme:form-textbox code="authenticated.sponsorship.form.label.draft-mode" path="draftMode" readonly="true"/>
	
	<jstl:choose> 	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="authenticated.sponsorship.form.button.donations" action="/authenticated/donation/list?sponsorshipId=${id}"/> 			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="authenticated.sponsorship.form.button.donations" action="/authenticated/donation/list?sponsorshipId=${id}"/> 			
			<acme:submit code="authenticated.sponsorship.form.button.update" action="/authenticated/sponsorship/update"/>
			<acme:submit code="authenticated.sponsorship.form.button.delete" action="/authenticated/sponsorship/delete"/>
			<acme:submit code="authenticated.sponsorship.form.button.publish" action="/authenticated/sponsorship/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.sponsorship.form.button.create" action="/authenticated/sponsorship/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>