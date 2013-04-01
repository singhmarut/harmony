<!DOCTYPE html>
<html>
<head>
    <title>Manage Subjects</title>
    <meta name="layout" content="menu"/>
    <meta charset="UTF-8">

    <g:javascript src="/jquery-easyui-1.3.2/jquery-1.8.0.min.js"/>
    <g:javascript src="/jquery-easyui-1.3.2/jquery.easyui.min.js"/>

    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>

    %{--<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">--}%
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes/default', file: 'easyui.css')}">
    %{--<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">--}%
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/demo', file: 'demo.css')}">
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes', file: 'icon.css')}">

<r:layoutResources />

<script type="text/javascript">
    $(document).ready(function() {
      var url = "${createLink(controller: 'subjectTag', action: 'list')}";
      $('#addButton').linkbutton({
          iconCls: 'icon-add'
      });

      $('#removeButton').linkbutton({
          iconCls: 'icon-remove'
      });

      $('#editSubject').linkbutton({
          iconCls: 'icon-edit'
      });

        var subjectName = $( "#subjectName" ),
               allFields = $( [] ).add( subjectName ),
                tips = $( ".validateTips" );

        $( "#dialog-form" ).dialog({
            autoOpen: false,
            height: 400,
            width: 360,
            modal: true
        });

        $('#addButton').bind('click', function(e){
            $("#dialog-form").dialog("open");
        });

        $('#cancelSubject').bind('click', function(e){
            $("#dialog-form").dialog("close");
        });

        $('#addSubject').bind('click', function(e){
            var createUrl = "${createLink(controller: 'subjectTag', action: 'create')}";
            var subject = new Object();
            subject.text = subjectName.val();
            var selectedNode = $('#tt').tree('getSelected');
            if (selectedNode){
                subject.parentSkill =  selectedNode.text;
            }
            $.ajax({
                url: createUrl,
                dataType: "json",
                tyoe: "POST",
                data: {so: JSON.stringify(subject)}
            })
        });
        $('#tt').tree({
            onClick: function(node){
                //alert(node.text);  // alert node text property when clicked
            }
        });
    });

</script>
</head>

<body>
<div style="margin-bottom: 10px;margin-top: 20px">
    <a id="addButton" href="#">Add New Subject</a>
    <a id="removeButton" href="#">Remove Subject</a>
    <a id="editSubject" href="#">Edit Subject</a>
</div>

<div id='addNewAccountWindow'>
    %{--<div>Add new Account</div>--}%
    %{--<div id="btnOK"/>--}%
</div>

%{--<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"--}%
     %{--closed="true" buttons="#dlg-buttons">--}%
    %{--<div class="ftitle">New Subject</div>--}%
    %{--<form id="fm" method="post" novalidate>--}%
        %{--<div class="fitem">--}%
            %{--<label>Subject Name:</label>--}%
            %{--<input name="subjectName" class="easyui-validatebox" required="true">--}%
        %{--</div>--}%
    %{--</form>--}%
%{--</div>--}%



<div id="dialog-form" closed="true" title="Create new Subject">
    <p class="validateTips">All form fields are required.</p>
    <form id="fm" method="post">
        <fieldset>
            <label for="subjectName">Subject Name</label>
            <input type="text" name="subjectName" id="subjectName" class="text ui-widget-content ui-corner-all"/>
        </fieldset>

        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="addSubject">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="cancelSubject">Cancel</a>
        </div>
    </form>
</div>
<ul id="tt" class="easyui-tree" data-options="url:'${createLink(controller: 'subjectTag', action: 'list')}',animate:false,checkbox:true"></ul>
%{--<ul id="tt" class="easyui-tree" animate="true" cascadeCheck="true" checkbox="true" lines ="true" url="">--}%
%{--</ul>--}%
</body>
</html>