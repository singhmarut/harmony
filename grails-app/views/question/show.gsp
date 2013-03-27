
<%@ page import="harmony.Question" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-question" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-question" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list question">
			
				<g:if test="${questionInstance?.answer1}">
				<li class="fieldcontain">
					<span id="answer1-label" class="property-label"><g:message code="question.answer1.label" default="Answer1" /></span>
					
						<span class="property-value" aria-labelledby="answer1-label"><g:fieldValue bean="${questionInstance}" field="answer1"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.answer2}">
				<li class="fieldcontain">
					<span id="answer2-label" class="property-label"><g:message code="question.answer2.label" default="Answer2" /></span>
					
						<span class="property-value" aria-labelledby="answer2-label"><g:fieldValue bean="${questionInstance}" field="answer2"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.answer3}">
				<li class="fieldcontain">
					<span id="answer3-label" class="property-label"><g:message code="question.answer3.label" default="Answer3" /></span>
					
						<span class="property-value" aria-labelledby="answer3-label"><g:fieldValue bean="${questionInstance}" field="answer3"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.answer4}">
				<li class="fieldcontain">
					<span id="answer4-label" class="property-label"><g:message code="question.answer4.label" default="Answer4" /></span>
					
						<span class="property-value" aria-labelledby="answer4-label"><g:fieldValue bean="${questionInstance}" field="answer4"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.answer5}">
				<li class="fieldcontain">
					<span id="answer5-label" class="property-label"><g:message code="question.answer5.label" default="Answer5" /></span>
					
						<span class="property-value" aria-labelledby="answer5-label"><g:fieldValue bean="${questionInstance}" field="answer5"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.companyShortName}">
				<li class="fieldcontain">
					<span id="companyShortName-label" class="property-label"><g:message code="question.companyShortName.label" default="Company Short Name" /></span>
					
						<span class="property-value" aria-labelledby="companyShortName-label"><g:fieldValue bean="${questionInstance}" field="companyShortName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.option1}">
				<li class="fieldcontain">
					<span id="option1-label" class="property-label"><g:message code="question.option1.label" default="Option1" /></span>
					
						<span class="property-value" aria-labelledby="option1-label"><g:fieldValue bean="${questionInstance}" field="option1"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.option2}">
				<li class="fieldcontain">
					<span id="option2-label" class="property-label"><g:message code="question.option2.label" default="Option2" /></span>
					
						<span class="property-value" aria-labelledby="option2-label"><g:fieldValue bean="${questionInstance}" field="option2"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.option3}">
				<li class="fieldcontain">
					<span id="option3-label" class="property-label"><g:message code="question.option3.label" default="Option3" /></span>
					
						<span class="property-value" aria-labelledby="option3-label"><g:fieldValue bean="${questionInstance}" field="option3"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.option4}">
				<li class="fieldcontain">
					<span id="option4-label" class="property-label"><g:message code="question.option4.label" default="Option4" /></span>
					
						<span class="property-value" aria-labelledby="option4-label"><g:fieldValue bean="${questionInstance}" field="option4"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.option5}">
				<li class="fieldcontain">
					<span id="option5-label" class="property-label"><g:message code="question.option5.label" default="Option5" /></span>
					
						<span class="property-value" aria-labelledby="option5-label"><g:fieldValue bean="${questionInstance}" field="option5"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.questionType}">
				<li class="fieldcontain">
					<span id="questionType-label" class="property-label"><g:message code="question.questionType.label" default="Question Type" /></span>
					
						<span class="property-value" aria-labelledby="questionType-label"><g:fieldValue bean="${questionInstance}" field="questionType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionInstance?.text}">
				<li class="fieldcontain">
					<span id="text-label" class="property-label"><g:message code="question.text.label" default="Text" /></span>
					
						<span class="property-value" aria-labelledby="text-label"><g:fieldValue bean="${questionInstance}" field="text"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${questionInstance?.id}" />
					<g:link class="edit" action="edit" id="${questionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
