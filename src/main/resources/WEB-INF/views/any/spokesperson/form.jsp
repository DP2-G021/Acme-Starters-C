<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox code="any.spokesperson.form.label.name" path="userAccount.identity.name"/>
    <acme:form-textbox code="any.spokesperson.form.label.surname" path="userAccount.identity.surname"/>
    <acme:form-textbox code="any.spokesperson.form.label.email" path="userAccount.identity.email"/>
    
    <acme:form-textarea code="any.spokesperson.form.label.cv" path="cv"/>
    <acme:form-textarea code="any.spokesperson.form.label.achievements" path="achievements"/>
    <acme:form-textbox code="any.spokesperson.form.label.licensed" path="licensed"/>
    
</acme:form>