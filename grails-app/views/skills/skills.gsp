<!DOCTYPE html>
<html>
<head>
    <title>Grails Runtime Exception</title>
    <meta name="layout" content="menu"/>
    <g:javascript src="/jquery-ui/js/jquery-1.9.1.js"/>
    <g:javascript src="/jquery-ui/js/jquery-ui-1.10.2.custom.js"/>
    <g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>

    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.base.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.energyblue.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.ui-redmond.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.darkblue.css')}" type="text/css" />

    <link rel="stylesheet" href="${resource(dir: 'js/jquery.jqGrid-4.4.4/css', file: 'ui.jqgrid.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui/css/ui-lightness', file: 'jquery-ui-1.10.2.custom.css')}"
          type="text/css">
    <r:layoutResources />
    <script type="text/javascript">
        $(document).ready(function() {

            var url = "${createLink(controller: 'skills', action: 'list')}";
            //prepare the data
            var source =
            {
                datatype: "json",
                datafields: [
                    { name: 'id', type: 'number' },
                    { name: 'skillName' }
                    ],
                id: 'id',
                url: url
            };

            var renderer = function (id) {
                var finalId = "btn" + id;
                //return 'hello';
                String
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
                        editable: true,
                        width: 800,
                        columns: [
                            { text: 'Sr No', datafield: 'id', width: 100 },
                            { text: 'Tag Name', datafield: 'skillName', width: 100 }
                        ]
                    });
        });

    </script>

</head>
<body>
<div style="margin-bottom: 10px;">
    <input id="addButton" type="button" value="Add New Skill" />
    <input id="removeButton" type="button" value="Delete Skill" />
</div>
<div id="example">
</div>
<g:form>

</g:form>
<g:renderException exception="${exception}" />
</body>
</html>