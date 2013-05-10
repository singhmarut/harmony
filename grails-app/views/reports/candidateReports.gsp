<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 16/04/13
  Time: 3:32 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Candidate Reports</title>
    <meta name="layout" content="menu"/>

    <g:javascript src="/jquery-easyui-1.3.2/jquery-1.8.0.min.js"/>
    <g:javascript src="/jquery-easyui-1.3.2/jquery.easyui.min.js"/>
    <script type="text/javascript" src= "http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes/default', file: 'easyui.css')}">
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/demo', file: 'demo.css')}">
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes', file: 'icon.css')}">

    <g:javascript src="/bootstrap/js/bootstrap.min.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/bootstrap/css', file: 'bootstrap.min.css')}">

    <r:layoutResources />
    <script type="text/javascript">

    $(document).ready(function(){
    $('#reportsGrid').datagrid({
        url: "${createLink(controller: 'reports', action: 'showQuestionPaperReports', params: [questionPaperId: questionPaperId]) }",
        columns:[[
            {field:'authKey',title:'Candidate Email',width:100},
            {field:'report',title:'Report',width:150,align:'right',
                formatter:function(value,row,index){
                    var e = '<a href="#" onclick="showReport(\'' + index + '\')">Show Report</a> ';
                    return e;
                }
            }
        ]]
    });
    })

    function showReport(index){
        var rowId = parseInt(index);
        var rows = $('#reportsGrid').datagrid('getRows');
        var thisRow = rows[rowId];
        var candidateReportUrl = "${createLink(controller: 'reports', action: 'showCandidateReport')}" + "?authKey=" + thisRow.authKey;
        window.location.href=candidateReportUrl;
    }

</script>

</head>
<body style="margin: 20px">
   <div id="reportsGrid" style="padding-top: 20px">

   </div>
</body>
</html>