<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.strategy.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="authenticated.strategy.list.label.name" path="name" width="25%"/>
	<acme:list-column code="authenticated.strategy.list.label.start-moment" path="startMoment" width="25%"/>
	<acme:list-column code="authenticated.strategy.list.label.end-moment" path="endMoment" width="25%"/>
	<acme:list-column code="authenticated.strategy.list.label.draft-mode" path="draftModeDisplay" width="10%"/>
</acme:list>

<acme:button code="authenticated.strategy.list.button.create" action="/authenticated/strategy/create"/>
