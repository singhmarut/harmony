<!DOCTYPE html>
<html>
<head>
    <title>Grails Runtime Exception</title>
    %{--<meta name="layout" content="main">--}%
    %{--<g:javascript library="jquery"/>--}%

    %{--<g:javascript src="/jquery-ui/js/jquery-1.9.1.js"/>--}%
    %{--<g:javascript src="/jquery-ui/js/jquery-ui-1.10.2.custom.js"/>--}%

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>

    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/themes/base/jquery-ui.css" />

    %{--<link rel="stylesheet" href="${resource(dir: 'js/grid/themes', file: 'peach/pq-grid.css')}" type="text/css" />--}%
    <g:javascript src="/grid/pqgrid.dev.js" />
    %{--<g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>--}%
    %{--<g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>--}%

    %{--<g:javascript src="/DataTables-1.9.4/media/js/jquery.dataTables.js"/>--}%
    %{--<link rel="stylesheet" href="${resource(dir: 'js/jquery.jqGrid-4.4.4/css', file: 'ui.jqgrid.css')}" type="text/css">--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui/css/ui-lightness', file: 'jquery-ui-1.10.2.custom.css')}"
          type="text/css">
    <r:layoutResources />
    <script type="text/javascript">
    $(function() {
        var url = "${getRequest().contextPath}" + "/rest/url/question/list?";
        var data = [ [1,'Exxon Mobil','339,938.0','36,130.0'],
            [2,'Wal-Mart Stores','315,654.0','11,231.0'],
            [3,'Royal Dutch Shell','306,731.0','25,311.0'],
            [4,'BP','267,600.0','22,341.0'],
            [5,'General Motors','192,604.0','-10,567.0'],
            [6,'Chevron','189,481.0','14,099.0'],
            [7,'DaimlerChrysler','186,106.3','3,536.3'],
            [8,'Toyota Motor','185,805.0','12,119.6'],
            [9,'Ford Motor','177,210.0','2,024.0'],
            [10,'ConocoPhillips','166,683.0','13,529.0'],
            [11,'General Electric','157,153.0','16,353.0'],
            [12,'Total','152,360.7','15,250.0'],
            [13,'ING Group','138,235.3','8,958.9'],
            [14,'Citigroup','131,045.0','24,589.0'],
            [15,'AXA','129,839.2','5,186.5'],
            [16,'Allianz','121,406.0','5,442.4'],
            [17,'Volkswagen','118,376.6','1,391.7'],
            [18,'Fortis','112,351.4','4,896.3'],
            [19,'Crédit Agricole','110,764.6','7,434.3'],
            [20,'American Intl. Group','108,905.0','10,477.0']];

        var obj = {};
        obj.width = 700;
        obj.height = 400;
        obj.colModel = [{title:"Rank", width:100, dataType:"integer"},
            {title:"Company", width:200, dataType:"string"},
            {title:"Revenues ($ millions)", width:150, dataType:"float", align:"right"},
            {title:"Profits ($ millions)", width:150, dataType:"float", align:"right"}];
        obj.dataModel = {data:data};
        $("#example").pqGrid( obj );

    });

</script>

</head>
<body>
<div id="example">
   %{--<table id="ExpenseTableContainer">--}%

   %{--</table>--}%
</div>
<g:form>

</g:form>
<g:renderException exception="${exception}" />
</body>
</html>
