<!DOCTYPE html>
<html>
<head>
    <title>Grails Runtime Exception</title>
    %{--<meta name="layout" content="main">--}%
    %{--<g:javascript library="jquery"/>--}%

    <g:javascript src="/jquery-ui/js/jquery-1.9.1.js"/>
    <g:javascript src="/jquery-ui/js/jquery-ui-1.10.2.custom.js"/>
    <g:javascript src="/jtable/jquery.jtable.js"/>
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui/css', file: 'ui-lightness/jquery-ui-1.10.2.custom.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'js/jtable/themes/lightcolor/orange', file: 'jtable.css')}" type="text/css">
    <r:layoutResources />
    <script type="text/javascript">
    $(document).ready(function() {
        //setup the jtable that will display the results
        $('#ExpenseTableContainer').jtable({
            title: 'List of Questions',
            selecting: true, //Enable selecting
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: true, //Enable sorting
            actions: {
                //listAction: "${createLink(controller: 'question', action: 'listQuestions')}"
                listAction: "${getRequest().contextPath}" + "/rest/url/question/list?",
                createAction: "${getRequest().contextPath}" + "/rest/url/question/create?",
                updateAction: "${getRequest().contextPath}" + "/rest/url/question/update?",
                deleteAction: "${getRequest().contextPath}" + "/rest/url/question/delete?"
            },
            fields: {
                text: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Question Text',
                    width: '30%'
                },option1: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Option1',
                    width: '30%'
                },option2: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Option2',
                    width: '30%'
                },option3: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Option3',
                    width: '30%'
                },option4: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Option4',
                    width: '30%'
                },option5: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Option5',
                    width: '30%'
                },answer1: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Answer1',
                    width: '30%'
                },answer2: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Answer2',
                    width: '30%'
                },answer3: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Answer3',
                    width: '30%'
                },answer4: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Answer4',
                    width: '30%'
                },answer5: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'Answer5',
                    width: '30%'
                },questionType: {
                    key: true,
                    list: true,
                    create: true,
                    edit: true,
                    title: 'questionType',
                    width: '30%'
                }

            },
            rowInserted: function (event, data) {
                //if (data.record.Name.indexOf('Andrew') >= 0) {
                $('#ExpenseTableContainer').jtable('selectRows', data.row);
                console.log("records inserted");
                //$('#PeopleTableContainer').jtable('load');
                //}
            },
            //Register to selectionChanged event to hanlde events
            recordAdded: function(event, data){
                //after record insertion, reload the records
                $('#ExpenseTableContainer').jtable('load');
            },
            recordUpdated: function(event, data){
                //after record updation, reload the records
                $('#ExpenseTableContainer').jtable('load');
            }
        });
        $('#ExpenseTableContainer').jtable('load');

    });

    </script>

</head>
<body>
<div id="ExpenseTableContainer">

</div>
<g:form>

</g:form>
<g:renderException exception="${exception}" />
</body>
</html>
