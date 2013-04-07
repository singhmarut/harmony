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

            $('#skillTree').datagrid({
                 onDblClickCell: function(index,field,value){
                    $(this).datagrid('beginEdit', index);
                    var ed = $(this).datagrid('getEditor', {index:index,field:field});
                    $(ed.target).focus();
                },
                onClickRow: function(index){
                if (editIndex != index){
                        if (endEditing()){
                            $('#skillTree').datagrid('selectRow', index)
                                    .datagrid('beginEdit', index);
                            editIndex = index;
                        } else {
                            $('#skillTree').datagrid('selectRow', editIndex);
                        }
                    }
                }
            });

            $('#tabs').tabs({
                //tools:'#tab-tools'
                onSelect:function(title){
                    $('#tt').tabs({
                        content: 'hello'
                    });
                    alert(title+' is selected');
                }
            });

            var editIndex = undefined;
            function endEditing(){
                if (editIndex == undefined){return true}
                if ($('#skillTree').datagrid('validateRow', editIndex)){
                    var ed = $('#skillTree').datagrid('getEditor', {index:editIndex,field:'id'});
                    $('#skillTree').datagrid('endEdit', editIndex);
                    editIndex = undefined;
                    return true;
                } else {
                    return false;
                }
            }

            $('#addSection').bind('click', function(e){
                var index =0;
                $('#tabs').tabs('add',{
                    title:'New Section',
                    closable:true
                });
            })

            $('#accept').bind('click', function(e){
                if (endEditing()){
                    $('#skillTree').datagrid('acceptChanges');
                    //$('#ddv-0').datagrid('acceptChanges');
                }
            })
            $('#generateKeys').bind('click', function(e){
                var createUrl = "${createLink(controller: 'testKeyGenerator', action: 'generate')}";
            })

            $('#createPaper').bind('click', function(e){
                var createUrl = "${createLink(controller: 'questionPaper', action: 'create')}";

                var questionPaper = new Object();
                questionPaper.title = $('#paperTitle').val();
                var rows = $('#skillTree').treegrid('getRows')
                var counter = 0;
                var section = new Object();

                var sections = new Array();

                var sectionSubjects = new Array();
                section.duration = 1;
                //section.sectionSubjects = sections;
                section.sectionName = $('#sectionName').val();
                section.instruction = "No Instructions";

                sections.push(section);

                var jsonQ = JSON.stringify(sections);

                questionPaper.sectionList = sections;
                jsonQ = JSON.stringify(questionPaper);
                for (var idx =0; idx < rows.length; idx++){
                    var row = rows[idx];
                    var sectionSubject = new Object();
                    sectionSubject.subjectTagId = row.id;
                    sectionSubject.questionCount = row.questionCount;
                    sectionSubject.difficultyLevel = row.difficulty;
                    sectionSubjects.push(sectionSubject);
                }
                section.sectionSubjects = sectionSubjects;

                jsonQ = JSON.stringify(questionPaper);
                $.ajax({
                    url: createUrl,
                    dataType: "json",
                    type: "POST",
                    data: {questionPaper: jsonQ}
                })
            })

            $('#tt').tree({
                onCheck: function(node){
                    //alert(node.text);
                    //var node = $('#tt').treegrid('getSelected');
                    if(node.checked === false){
                        $('#skillTree').datagrid('appendRow',{
                            //parent: node.id,  // the node has a 'id' value that defined through 'idField' property
                            id: node.id,
                            subject: node.text,
                            questionCount: 1,
                            difficulty: 1

                        });
                    }else{
                        var rows = $('#skillTree').treegrid('getRows')
                        var counter = 0;
                        for (; counter < rows.length; counter++){
                            var row = rows[counter];
                            if (row.id === node.id){
                                var rowIndex =  $('#skillTree').treegrid('getRowIndex', row);
                                $('#skillTree').treegrid('deleteRow', rowIndex);
                                break;
                            }
                        }
                    }
                }
            });
        });

    </script>
</head>
<body style="margin: 20px">

<div class="easyui-panel" title="Create Question Paper" style="width:1200px;height:500px;padding:10px;">
    <div class="easyui-layout" data-options="fit:true">

            <div data-options="region:'north'" style="height:50px;padding:10px">
                <div>
                    <label style="padding-left: 10px;padding-right: 10px">Question Paper Title</label>
                    <input type="text" id="paperTitle"/>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" id="createPaper">Create Question Paper</a>
                </div>
            </div>
        <div data-options="region:'west',split:true" style="width:200px;padding:10px">
            <div class="easyui-accordion" data-options="fit:true">
                <div title="Paper Options">
                      <p>Help</p>
                </div>
                <div title="Select Subjects">
                    <ul id="tt" class="easyui-tree" data-options="url:'${createLink(controller: 'subjectTag', action: 'list')}',fit:true,cascadeCheck:false,animate:false,checkbox:true">
                    </ul>
                </div>
            </div>
        </div>
        <div data-options="region:'center'" style="padding:10px">
            <label>Section Name</label> <input id="sectionName" style="width:200px"/>
            <label style="padding-left: 10px">Duration</label>
            <input type="text" class="easyui-numberbox" value="100" data-options="min:0,max:60" id="sectionDuration"/>
            <label style="padding-left: 2px">Minutes</label>
            <table id="skillTree" class="easyui-datagrid" style="width:600px;height:350px;"
                   data-options="fitColumns:true,singleSelect:true,toolbar: '#tb',">
                <thead>
                <tr>
                    <th data-options="field:'id',width:50">id</th>
                    <th data-options="field:'subject',width:180">Subject</th>
                    <th data-options="field:'questionCount',width:100,editor:{type:'text'},align:'right'">Question Count</th>
                    <th data-options="field:'difficulty',width:80,editor:{type:'text'}">Difficulty</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="tb" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addSection">Add New Section</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">Remove</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" id="accept">Accept</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">Reject</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">Manage Time Restrictons</a>
</div>
</body>
</html>