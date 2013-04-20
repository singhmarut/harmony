<!DOCTYPE html>
<html>
<head>
    <title>Authentication Keys</title>
    <meta name="layout" content="menu"/>
    <meta charset="UTF-8">

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
        $(document).ready(function() {
            $('#authKeyGrid').datagrid({

            });
        });

    </script>
</head>
<body style="margin: 20px">
    <label><b>Authenticaion Keys for Question Paper ${questionPaperId}</b></label>
    <div style="padding:10px">
        <table id="authKeyGrid" class="easyui-datagrid" style="width:600px;height:300px;"
               url = "${createLink(controller: 'questionPaper', action: 'showQuestionPaperKeys' ) }?questionPaperId=${questionPaperId}"
               data-options="fitColumns:true,singleSelect:true">
            <thead>
            <tr>
                <th data-options="field:'questionPaperId',width:50">Question Paper Id</th>
                <th data-options="field:'authKey',width:180">Auth Key</th>
            </tr>
            </thead>
        </table>
    </div>
</body>
</html>