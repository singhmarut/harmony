<!DOCTYPE html>
<html>
<head>
    <title>Grails Runtime Exception</title>
    %{--<meta name="layout" content="main">--}%
    %{--<g:javascript library="jquery"/>--}%

    <g:javascript src="/jquery-easyui-1.3.2/jquery-1.8.0.min.js"/>
    <g:javascript src="/jquery-easyui-1.3.2/jquery.easyui.min.js"/>
    <script type="text/javascript" src= "http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes/default', file: 'easyui.css')}">
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/demo', file: 'demo.css')}">
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes', file: 'icon.css')}">

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
    <table class="easyui-datagrid" style="width:400px;height:250px"
           data-options="url:'datagrid_data.json',fitColumns:true,singleSelect:true">
        <thead>
        <tr>
            <th data-options="field:'text',width:100">Code</th>
            <th data-options="field:'option1',width:100,checkbox:true">Option 1</th>
            <th data-options="field:'option2',width:100,checkbox:true'">Option 2</th>
            <th data-options="field:'option3',width:100,checkbox:true'">Option 3</th>
            <th data-options="field:'option4',width:100,checkbox:true'">Option 4</th>
            <th data-options="field:'option5',width:100,checkbox:true'">Option 5</th>
            <th data-options="field:'choice1',width:100,checkbox:true">Option 1</th>
            <th data-options="field:'choice2',width:100,checkbox:true'">Option 2</th>
            <th data-options="field:'choice3',width:100,checkbox:true'">Option 3</th>
            <th data-options="field:'choice4',width:100,checkbox:true'">Option 4</th>
            <th data-options="field:'choice5',width:100,checkbox:true'">Option 5</th>
            <th data-options="field:'marks',width:100'">Marks</th>
            <th data-options="field:'difficultyLevel',width:100">Difficulty Level</th>
        </tr>
        </thead>
    </table>
</div>
<g:form>

</g:form>
<g:renderException exception="${exception}" />
</body>
</html>
