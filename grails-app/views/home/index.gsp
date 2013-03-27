<!DOCTYPE html>
<html>
<head>
    <title>Grails Runtime Exception</title>
    <meta name="layout" content="menu"/>
</head>
<body>
<g:form>
    <g:set var="questionUrl" value="${g.createLink(controller: 'question', action: 'list')}"/>
</g:form>
<g:renderException exception="${exception}" />
</body>
</html>
