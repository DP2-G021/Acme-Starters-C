<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:set var="readOnlyDonation" value="${_command == 'show' && draftMode == false}"/>
	<acme:form-textbox code="authenticated.donation.form.label.name" path="name" readonly="${readOnlyDonation}"/>
	<acme:form-money code="authenticated.donation.form.label.money" path="money" readonly="${readOnlyDonation}"/>	
	<acme:form-select code="authenticated.donation.form.label.kind" path="kind"  choices="${kinds}" readonly="${readOnlyDonation}"/>
	<acme:form-textarea code="authenticated.donation.form.label.notes" path="notes" readonly="${readOnlyDonation}"/>
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="authenticated.donation.form.button.update" action="/authenticated/donation/update"/>
			<acme:submit code="authenticated.donation.form.button.delete" action="/authenticated/donation/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.donation.form.button.create" action="/authenticated/donation/create?sponsorshipId=${sponsorshipId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>