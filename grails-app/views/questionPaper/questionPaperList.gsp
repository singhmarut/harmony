<!DOCTYPE html>
<html>
<head>
    <title>Create Question Paper</title>
    <meta name="layout" content="menu"/>
    <meta charset="UTF-8">

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
            var url = "${createLink(controller: 'subjectTag', action: 'list')}";

            $('#questionPaperGrid').datagrid({

            });

            $('#generateKeys').bind('click', function(e){
                var createUrl = "${createLink(controller: 'testKeyGenerator', action: 'generate')}";
                var row = $('#questionPaperGrid').datagrid('getSelected');
                var autKeyJson = new Object();
                autKeyJson.id = row.questionPaperId;
                autKeyJson.keyCount = $('#keyCount').val();
                $.ajax({
                    url: createUrl,
                    type: "POST",
                    dataType:"json",
                    data: {authReq: JSON.stringify(autKeyJson)}
                })
            })
        });

    </script>
</head>
<body style="margin: 20px">

<div class="easyui-panel" title="Create Authorization Keys" style="width:1200px;height:500px;padding:10px;">
    <div class="easyui-layout" data-options="fit:true">

        <div data-options="region:'north'" style="height:50px;padding:10px">
            <div>
                <label style="padding-left: 10px;padding-right: 10px">Question Paper Title</label>
                <input type="text" id="paperTitle" disabled="true"/>
                <input type="text" class="easyui-numberbox" value="50" data-options="min:0,max:60" id="keyCount"/>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" id="generateKeys">Generate Keys</a>
                <a href="${createLink(controller: 'questionPaper', action: '')}" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" id="authKeys">Generate Keys</a>
            </div>
        </div>

        <div data-options="region:'center'" style="padding:10px">
            <table id="questionPaperGrid" class="easyui-datagrid" style="width:600px;height:350px;"
                  url = "${createLink(controller: 'questionPaper', action: 'list' ) } "
                   data-options="fitColumns:true,singleSelect:true">
                <thead>
                <tr>
                    <th data-options="field:'questionPaperId',width:50">Id</th>
                    <th data-options="field:'title',width:180">Title</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
</body>
</html>