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
	
	<jstl:set var="readOnlyAuditSection" value="${_command == 'show' && draftMode == false}"/>
	<acme:form-textbox code="authenticated.audit-section.form.label.name" path="name" readonly="${readOnlyAuditSection}"/>
	<acme:form-textbox code="authenticated.audit-section.form.label.notes" path="notes" readonly="${readOnlyAuditSection}"/>
	<acme:form-double code="authenticated.audit-section.form.label.hours" path="hours" readonly="${readOnlyAuditSection}"/>
	<acme:form-textbox code="authenticated.audit-section.form.label.kind" path="kind" readonly="${readOnlyAuditSection}"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="authenticated.audit-section.form.button.update" action="/authenticated/audit-section/update"/>
			<acme:submit code="authenticated.audit-section.form.button.delete" action="/authenticated/audit-section/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.audit-section.form.button.create" action="/authenticated/audit-section/create?auditreportId=${auditreportId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
