<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox code="authenticated.campaign.form.label.ticker" path="ticker" readonly="true"/>
    <acme:form-textbox code="authenticated.campaign.form.label.name" path="name"/>
    <acme:form-checkbox code="authenticated.campaign.form.label.draftMode" path="draftMode" readonly="true"/>
    <acme:form-textarea code="authenticated.campaign.form.label.description" path="description"/>
    <acme:form-moment code="authenticated.campaign.form.label.startMoment" path="startMoment"/>
    <acme:form-moment code="authenticated.campaign.form.label.endMoment" path="endMoment"/>
    <acme:form-url code="authenticated.campaign.form.label.moreInfo" path="moreInfo"/>
    <acme:form-textbox code="authenticated.campaign.form.label.effort" path="effort" readonly="true"/>
    <acme:form-textbox code="authenticated.campaign.form.label.monthsActive" path="monthsActive" readonly="true"/>
    <acme:button code="authenticated.campaign.form.button.milestones" action="/authenticated/milestone/list?campaignId=${id}"/>
</acme:form>