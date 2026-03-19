<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:set var="readOnlyAuditReport" value="${_command == 'show' && draftMode == false}"/>
	<acme:form-textbox code="authenticated.audit-report.form.label.ticker" path="ticker" readonly="${readOnlyAuditReport}"/>
	<acme:form-textbox code="authenticated.audit-report.form.label.name" path="name" readonly="${readOnlyAuditReport}"/>
	<acme:form-textarea code="authenticated.audit-report.form.label.description" path="description" readonly="${readOnlyAuditReport}"/>
	<acme:form-moment code="authenticated.audit-report.form.label.startMoment" path="startMoment" readonly="${readOnlyAuditReport}"/>
	<acme:form-moment code="authenticated.audit-report.form.label.endMoment" path="endMoment" readonly="${readOnlyAuditReport}"/>
	<acme:form-url code="authenticated.audit-report.form.label.moreInfo" path="moreInfo" readonly="${readOnlyAuditReport}"/>
	<acme:form-textbox code="authenticated.audit-report.form.label.draftMode" path="draftMode" readonly="true"/>
	<acme:form-double code="authenticated.audit-report.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="authenticated.audit-report.form.label.hours" path="hours" readonly="true"/>
	
	 <jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="authenticated.audit-report.form.button.audit-sections" action="/authenticated/audit-section/list?auditreportId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="authenticated.audit-report.form.button.audit-sections" action="/authenticated/audit-section/list?auditreportId=${id}"/>
			<acme:submit code="authenticated.audit-report.form.button.update" action="/authenticated/audit-report/update"/>
			<acme:submit code="authenticated.audit-report.form.button.delete" action="/authenticated/audit-report/delete"/>
			<acme:submit code="authenticated.audit-report.form.button.publish" action="/authenticated/audit-report/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.audit-report.form.button.create" action="/authenticated/audit-report/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>