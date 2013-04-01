<!DOCTYPE html>
<html>
<head>
    <title>Grails Runtime Exception</title>
    <meta name="layout" content="menu"/>
    %{--<meta name="layout" content="main">--}%
    %{--<g:javascript library="jquery"/>--}%

    <g:javascript src="/jquery-ui/js/jquery-1.9.1.js"/>
    <g:javascript src="/jquery-ui/js/jquery-ui-1.10.2.custom.min.js"/>
    %{--<g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>--}%

    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>
    %{--<g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>--}%
    %{--<g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>--}%

    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.base.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.energyblue.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.ui-redmond.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.darkblue.css')}" type="text/css" />

    %{--<g:javascript src="/DataTables-1.9.4/media/js/jquery.dataTables.js"/>--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery.jqGrid-4.4.4/css', file: 'ui.jqgrid.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui/css/ui-lightness', file: 'jquery-ui-1.10.2.custom.css')}"
          type="text/css">
    <r:layoutResources />
    <script type="text/javascript">
    $(document).ready(function() {

        var url =  "${createLink(controller: 'question', action: 'listQuestions')}" + "?tagName=" + "${getRequest().getParameter('tagName')}" //"${getRequest().contextPath}" + "/rest/url/question/list?" + ;
        //prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
                { name: 'id', type: 'number' },
                { name: 'text' }  ,
                { name: 'option1' },
                { name: 'option2' },
                { name: 'option3' },
                { name: 'option4' },
                { name: 'option5' },
                { name: 'isDirty' },
                { name: 'Delete' }
            ],
            id: 'id',
            url: url
        };
        function getDemoTheme(){
            return "energyblue";
        }

        var renderer = function (id) {
            var finalId = "btn" + id;
            return '<input type="button" onClick="buttonclick(event)" class="gridButton" id= "dummy" value="Delete Question" />';
        }
        var buttonclick = function (event)
        {
            var id = event.target.id;
            $("#jqxgrid").jqxGrid('deleterow', id);
        }
        $('#addButton').on('click',

        function(){
            var newData = {"totalRecords":1,"curPage":1,"data":[{"text":"hello"}]};
            var newRow = $("#example").jqxGrid('addrow', null, newData);
            $('#example').jqxGrid('selectrow', newData.uid);
            //var cell = $('#jqxGrid').jqxGrid('getcell', 0, 'id');
        } );

        var dataAdapter = new $.jqx.dataAdapter(source, {
            loadComplete: function (data) { },
            loadError: function (xhr, status, error) { }
        });
        $("#addButton").jqxButton({ width: '150', height: '25'});
        $("#removeButton").jqxButton({ width: '150', height: '25'});
        $("#example").jqxGrid(
            {
                source: dataAdapter,
                columnsresize: true,
                theme: 'darkblue',
                pageable: true,
                autoheight: true,
                editable: false,
                width: 800,
                columns: [
                    { text: '', datafield: 'id', width: 50 },
                    { text: 'Question Text', datafield: 'text', width: 100 },
                    { text: 'Option 1', datafield: 'option1', width: 70 },
                    { text: 'Option 2', datafield: 'option2', width: 70 },
                    { text: 'Option 3', datafield: 'option3', width: 70, cellsalign: 'right' },
                    { text: 'Option 4', datafield: 'option4', width: 70, cellsalign: 'right', cellsformat: 'c2' },
                    { text: 'Option 5', datafield: 'option5', width: 70, cellsalign: 'right', cellsformat: 'c2' },
                    { text: 'Answer', datafield: 'answer1', width: 100, cellsalign: 'right', cellsformat: 'c2' },
                    { text: 'Save', datafield: 'isDirty', width: 100,columntype: 'checkbox', cellsalign: 'right', cellsformat: 'c2' },
                    { text: 'Delete', datafield: 'Delete', width: 100,cellsrenderer: renderer, cellsalign: 'right', cellsformat: 'c2' }
                ]
            });
    });

    </script>

</head>
<body>
<div style="margin-bottom: 10px; margin-top: 20px">
    <input id="addButton" type="button" value="Add New Question" />
    <input id="removeButton" type="button" value="Delete Question" />
</div>
<div id="example">
   %{--<table id="ExpenseTableContainer">--}%

   %{--</table>--}%
</div>
<g:form>

</g:form>
<g:renderException exception="${exception}" />
</body>
</html>
