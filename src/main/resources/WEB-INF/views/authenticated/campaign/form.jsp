<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
<jstl:set var="readOnlyCampaign" value="${_command == 'show' && draftMode == false}"/>
    <acme:form-textbox code="authenticated.campaign.form.label.ticker" path="ticker" readonly="${readOnlyCampaign}"/>
    <acme:form-textbox code="authenticated.campaign.form.label.name" path="name" readonly="${readOnlyCampaign}"/>
    <acme:form-textarea code="authenticated.campaign.form.label.description" path="description" readonly="${readOnlyCampaign}"/>
    <acme:form-moment code="authenticated.campaign.form.label.startMoment" path="startMoment" readonly="${readOnlyCampaign}"/>
    <acme:form-moment code="authenticated.campaign.form.label.endMoment" path="endMoment" readonly="${readOnlyCampaign}"/>
    <acme:form-url code="authenticated.campaign.form.label.moreInfo" path="moreInfo" readonly="${readOnlyCampaign}"/>
	<acme:form-textbox code="authenticated.campaign.form.label.draftMode" path="draftMode" readonly="true"/>
    <acme:form-textbox code="authenticated.campaign.form.label.effort" path="effort" readonly="true"/>
    <acme:form-textbox code="authenticated.campaign.form.label.monthsActive" path="monthsActive" readonly="true"/>
    
    <jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="authenticated.campaign.form.button.milestones" action="/authenticated/milestone/list?campaignId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="authenticated.campaign.form.button.milestones" action="/authenticated/milestone/list?campaignId=${id}"/>
			<acme:submit code="authenticated.campaign.form.button.update" action="/authenticated/campaign/update"/>
			<acme:submit code="authenticated.campaign.form.button.delete" action="/authenticated/campaign/delete"/>
			<acme:submit code="authenticated.campaign.form.button.publish" action="/authenticated/campaign/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.campaign.form.button.create" action="/authenticated/campaign/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>