<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:set var="readOnlyMilestone" value="${_command == 'show' && draftMode == false}"/>
	<acme:form-textbox code="authenticated.milestone.form.label.title" path="title" readonly="${readOnlyMilestone}"/>
	<acme:form-textarea code="authenticated.milestone.form.label.achievements" path="achievements" readonly="${readOnlyMilestone}"/>
	<acme:form-double code="authenticated.milestone.form.label.effort" path="effort" readonly="${readOnlyMilestone}"/>
	<acme:form-select code="authenticated.milestone.form.label.kind" path="kind" choices="${kinds}" readonly="${readOnlyMilestone}"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="authenticated.milestone.form.button.update" action="/authenticated/milestone/update?id=${id}"/>
        	<acme:submit code="authenticated.milestone.form.button.delete" action="/authenticated/milestone/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.milestone.form.button.create" action="/authenticated/milestone/create?campaignId=${campaignId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>