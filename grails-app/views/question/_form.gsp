<%@ page import="com.harmony.graph.Question" %>



<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answer1', 'error')} ">
	<label for="answer1">
		<g:message code="question.answer1.label" default="Answer1" />
		
	</label>
	<g:textField name="answer1" value="${questionInstance?.answer1}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answer2', 'error')} ">
	<label for="answer2">
		<g:message code="question.answer2.label" default="Answer2" />
		
	</label>
	<g:textField name="answer2" value="${questionInstance?.answer2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answer3', 'error')} ">
	<label for="answer3">
		<g:message code="question.answer3.label" default="Answer3" />
		
	</label>
	<g:textField name="answer3" value="${questionInstance?.answer3}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answer4', 'error')} ">
	<label for="answer4">
		<g:message code="question.answer4.label" default="Answer4" />
		
	</label>
	<g:textField name="answer4" value="${questionInstance?.answer4}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answer5', 'error')} ">
	<label for="answer5">
		<g:message code="question.answer5.label" default="Answer5" />
		
	</label>
	<g:textField name="answer5" value="${questionInstance?.answer5}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'companyShortName', 'error')} ">
	<label for="companyShortName">
		<g:message code="question.companyShortName.label" default="Company Short Name" />
		
	</label>
	<g:textField name="companyShortName" value="${questionInstance?.companyShortName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'option1', 'error')} ">
	<label for="option1">
		<g:message code="question.option1.label" default="Option1" />
		
	</label>
	<g:textField name="option1" value="${questionInstance?.option1}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'option2', 'error')} ">
	<label for="option2">
		<g:message code="question.option2.label" default="Option2" />
		
	</label>
	<g:textField name="option2" value="${questionInstance?.option2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'option3', 'error')} ">
	<label for="option3">
		<g:message code="question.option3.label" default="Option3" />
		
	</label>
	<g:textField name="option3" value="${questionInstance?.option3}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'option4', 'error')} ">
	<label for="option4">
		<g:message code="question.option4.label" default="Option4" />
		
	</label>
	<g:textField name="option4" value="${questionInstance?.option4}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'option5', 'error')} ">
	<label for="option5">
		<g:message code="question.option5.label" default="Option5" />
		
	</label>
	<g:textField name="option5" value="${questionInstance?.option5}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'questionType', 'error')} required">
	<label for="questionType">
		<g:message code="question.questionType.label" default="Question Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="questionType" from="${harmony.QuestionTypeEnum?.values()}" keys="${harmony.QuestionTypeEnum.values()*.name()}" required="" value="${questionInstance?.questionType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'text', 'error')} ">
	<label for="text">
		<g:message code="question.text.label" default="Text" />
		
	</label>
	<g:textField name="text" value="${questionInstance?.text}"/>
</div>

