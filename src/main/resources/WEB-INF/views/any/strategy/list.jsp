<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="any.strategy.list.label.ticker" path="ticker" width="15%"/>
    <acme:list-column code="any.strategy.list.label.name" path="name" width="25%"/>
    <acme:list-column code="any.strategy.list.label.start-moment" path="startMoment" width="30%"/>
    <acme:list-column code="any.strategy.list.label.end-moment" path="endMoment" width="30%"/>
</acme:list>
