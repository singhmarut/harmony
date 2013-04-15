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

    <g:javascript src="/bootstrap/js/bootstrap.min.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/bootstrap/css', file: 'bootstrap.min.css')}">

    <r:layoutResources />

    <script type="text/javascript">
        $(document).ready(function() {
            var url = "${createLink(controller: 'subjectTag', action: 'list')}";

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
            });

            $('#questionPaperGrid').datagrid({
                url: "${createLink(controller: 'questionPaper', action: 'list') } ",
                columns:[[
                    {field:'questionPaperId',title:'id',width:100},
                    {field:'title',title:'Name',width:100},
                    {field:'action',title:'Score',width:100,align:'right',
                        formatter:function(value,row,index){
                            var e = '<a href="#" onclick="startScore(\'' + index + '\')">Start Scoring</a> ';
                            return e;
                        }
                    }
                ]]
            });

            $('#showKeys').bind('click', function(e){
                var authKeyUrl = "${createLink(controller: 'questionPaper', action: 'showQuestionPaperKeys')}";
                var row = $('#questionPaperGrid').datagrid('getSelected');
                var paperId = row.questionPaperId
                $.ajax({
                    url: authKeyUrl,
                    type: "GET",
                    data: {questionPaperId: paperId}
                }).done(function(data){
                    if (data.redirect) {
                        // data.redirect contains the string URL to redirect to
                        window.location.href = data.redirect;
                    }
                })
            });
        });

      function startScore(row){

          var rowId = parseInt(row);
          var rows = $('#questionPaperGrid').datagrid('getRows');
          var thisRow = rows[rowId];
          var scoreUrl = "${createLink(controller: 'questionPaper', action: 'startScoring')}";
          $.ajax({
              url: scoreUrl,
              type: "GET",
              data: {questionPaperId: thisRow.questionPaperId}
          }).done(function(data){
              alert('scoring done for ' + thisRow.questionPaperId)
          })
      }

    </script>
</head>
<body style="margin: 20px">

<div class="easyui-panel" title="Create Authorization Keys" style="width:1200px;height:500px;padding:10px;">
    <div class="easyui-layout" data-options="fit:true">

        <div data-options="region:'north'" style="height:100px;padding:10px">
            <div>
                <div class="control-group">
                    <div class="controls">
                        <label>No. Of Keys<input type="text" id="keyCount"/></label>
                        <button type="submit" class="btn btn-small btn-primary" id="generateKeys">Generate Keys</button>
                        <button type="submit" id="showKeys" target="_blank" class="btn btn-small btn-info">Show Valid Keys</button>
                        %{--<button type="submit" class="btn btn-small btn-info" id="authKeys">Show Valid Keys</button>--}%
                    </div>
                </div>
                %{--<a href="${createLink(controller: 'questionPaper', action: '')}" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" id="authKeys">Show Keys</a>--}%
            </div>
        </div>

        <div data-options="region:'center'" style="padding:10px">
            <table id="questionPaperGrid">

            </table>
            %{--<table id="questionPaperGrid" class="easyui-datagrid" style="width:600px;height:300px;"--}%
                  %{--url = "${createLink(controller: 'questionPaper', action: 'list' ) } "--}%
                   %{--data-options="fitColumns:true,singleSelect:true">--}%
                %{--<thead>--}%
                %{--<tr>--}%
                    %{--<th data-options="field:'questionPaperId',width:50">Id</th>--}%
                    %{--<th data-options="field:'title',width:180">Title</th>--}%
                    %{--<th data-options="field:'action',width:180">Action</th>--}%
                %{--</tr>--}%
                %{--</thead>--}%
            %{--</table>--}%
        </div>
    </div>
</div>
</body>
</html>