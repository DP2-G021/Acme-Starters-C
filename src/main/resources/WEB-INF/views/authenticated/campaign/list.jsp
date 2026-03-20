<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.campaign.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="authenticated.campaign.list.label.name" path="name" width="25%"/>
	<acme:list-column code="authenticated.campaign.list.label.startMoment" path="startMoment" width="25%"/>
	<acme:list-column code="authenticated.campaign.list.label.endMoment" path="endMoment" width="25%"/>
	<acme:list-column code="authenticated.campaign.list.label.draftMode" path="draftModeDisplay" width="10%"/>
</acme:list>

<acme:button code="authenticated.campaign.list.button.create" action="/authenticated/campaign/create"/>