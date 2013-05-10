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

    %{--<g:javascript src="/bootstrap/js/bootstrap.min.js"/>--}%

    %{--<link rel="stylesheet" href="${resource(dir: '/js/bootstrap/css', file: 'bootstrap.min.css')}">--}%
    <r:layoutResources />

    <script type="text/javascript">
        $(document).ready(function() {
            var url = "${createLink(controller: 'subjectTag', action: 'list')}";
            var allSubjectUrl = "${createLink(controller: 'subjectTag', action: 'listAll')}";
            //Ids of sections
            var sectionIds = new Array();
            //Map between section id and section name
            var sectionIdMap = new Object();
            var curSecionId;

            var sectionInfoMap = new Object();
            var sectionList = new Array();

            var editIndex = undefined;
            function endEditing(gridId){
                if (editIndex == undefined){return true}
                if ($(gridId).datagrid('validateRow', editIndex)){
                    var ed = $(gridId).datagrid('getEditor', {index:editIndex,field:'id'});
                    $(gridId).datagrid('endEdit', editIndex);
                    editIndex = undefined;

                    $(gridId).datagrid('acceptChanges');
                    return true;
                } else {
                    return false;
                }
            }

            function accept(gridId){
                if (endEditing(gridId)){
                    $(gridId).datagrid('acceptChanges');
                }
            }

            function showQuestions(row){
               var subjectName = row.subject;
               alert(subjectName);
            }

            function addNewSection(sectionId, sectionName){
                sectionIdMap[sectionId] = sectionName;
                sectionIds.push(sectionId);
                var divId = "div" + sectionId;
                $('#questionSections').append('<div id=' + divId + '><table style="width:700px;" class="section" autoRowHeight:true  singleselect:true  striped:true id='+ sectionId + ' title=' + sectionName + '></table></div>');
                var d = document.getElementById(divId);
                var x = document.getElementById(sectionId);
                $(x).datagrid({
                    idField: "id",
                toolbar: [
                        {
                        iconCls: 'icon-add',
                        handler: function(){
                            $(x).datagrid('appendRow',{
                            subject: '',
                            questionCount: 1,
                            difficulty: 1
                        });
                      }
                    },{
                        iconCls: 'icon-remove',
                        handler: function(){
                            $(d).remove();
                            for (var i =0; i < sectionList.length; i++){
                                var section = sectionList[i];
                                if (section == sectionName){
                                    sectionList.splice(i,1);
                                    break;
                                }
                            }
                        }
                    },{
                        iconCls: 'icon-save',
                        handler: function(){
                           accept(x)
                        }
                    },{
                        iconCls: 'icon-ok',
                        handler: function(){
                            curSecionId = sectionId;
                            $('#sectionDlg').dialog('open');
                        }
                    }],

                    columns:[[
                        {field:'id',title:'Id',width:100,hidden:true},
                        {field:'subject',title:'Subject',width:300,editor:{type:'combobox',options:{
                            url:allSubjectUrl,
                            valueField:'text',
                            textField:'text',
                            panelHeight:'auto',
                            editable:true
                        } }},
                        {field:'questionCount',title:'Question Count',width:100,align:'right',editor:{type:'numberbox'}},
                        {field:'difficulty',title:'Difficulty (1-10)',width:120,align:'right',editor:{type:'numberbox'}},
                        {field:'action',title:'Compulsory Questions',width:150,align:'center',
                            formatter:function(value,row,index){
                                var e = '<a href="#" onclick="showQuestions(this)">Select Questions</a> ';
                                //var d = '<a href="#" onclick="deleterow(this)">Delete</a>';
                                return e;
                            }
                        }
                    ]],
                    onDblClickCell: function(index,field,value){
                        $(this).datagrid('beginEdit', index);
                        var ed = $(this).datagrid('getEditor', {index:index,field:field});
                        $(ed.target).focus();
                    },
                    onClickRow: function(index){
                        if (editIndex != index){
                            if (endEditing(x)){
                                $(this).datagrid('selectRow', index)
                                        .datagrid('beginEdit', index);
                                editIndex = index;
                            } else {
                                $(this).datagrid('selectRow', editIndex);
                            }
                        }
                    }
                });
            }


            $('#addNewSection').bind('click', function(e){
                $('#sectionNameDlg').dialog('open');
            })

            $('#closeSectioNameDlg').bind('click', function(e){
                $('#sectionNameDlg').dialog('close');
            })

            $('#addNewSectionName').bind('click', function(e){
                var index =0;
                var sectionName = $('#idSectionName').val();
                sectionList.push(sectionName);
                index = sectionList.length;
                var sectionId = "section" + index;
                addNewSection(sectionId, sectionName);
                $('#sectionNameDlg').dialog('close');
            })

            $('#generateKeys').bind('click', function(e){
                var createUrl = "${createLink(controller: 'testKeyGenerator', action: 'generate')}";
            })

            $('#sectionDlg').dialog({
                onBeforeOpen:function(){
                    var sectionInfo = sectionInfoMap[curSecionId];
                    if (sectionInfo){
                        $('#sectionInstruction').val(sectionInfo.instruction);
                        $('#sectionDuration').val(sectionInfo.duration);
                    }else{
                        if (sectionInfo){
                            $('#sectionInstruction').val(' ');
                            $('#sectionDuration').val(5);
                        }
                    }
                }
            })

            $('#saveSectionDetail').bind('click', function(e){
                var sectionInfo = new Object();
                sectionInfo.instruction = $('#sectionInstruction').val();
                sectionInfo.duration = $('#sectionDuration').val();
                sectionInfoMap[curSecionId] = sectionInfo;
                $('#sectionDlg').dialog('close');
                //alert('section detail saved');
            })

            $('#closeSectionDetail').bind('click', function(e){
                $('#sectionDlg').dialog('close');
            })

            $('#createPaper').bind('click', function(e){

                for (var sId = 0; sId < sectionIds.length; sId++){
                    var x = document.getElementById(sectionIds[sId]);
                    accept(x);
                }

                var createUrl = "${createLink(controller: 'questionPaper', action: 'create')}";

                var questionPaper = new Object();
                questionPaper.title = $('#paperTitle').val();
                var sections = new Array();

                for (var iSection = 0; iSection < sectionIds.length;iSection++){
                    var sectionId = sectionIds[iSection];
                    var x=document.getElementById(sectionId);

                    var rows = $(x).datagrid('getRows')
                    var counter = 0;
                    var section = new Object();

                    var sectionSubjects = new Array();

                    //section.sectionSubjects = sections;
                    section.sectionName = sectionIdMap[sectionId];
                    var sectionInfo = sectionInfoMap[sectionId];
                    if (sectionInfo){
                        section.duration = sectionInfo.duration;
                        section.instruction = sectionInfo.instruction;
                    }

                    for (var idx =0; idx < rows.length; idx++){

                        var row = rows[idx];
                        if (row.subject != null && row.subject != ""){
                            var sectionSubject = new Object();
                            //sectionSubject.subjectTagId = row.id;
                            sectionSubject.subjectName = row.subject;
                            sectionSubject.questionCount = row.questionCount;
                            sectionSubject.difficultyLevel = row.difficulty;
                            sectionSubjects.push(sectionSubject);
                        }
                    }
                    section.sectionSubjects = sectionSubjects;
                    sections.push(section);
                }

                questionPaper.sectionList = sections;

                jsonQ = JSON.stringify(questionPaper);
                $.ajax({
                    url: createUrl,
                    dataType: "json",
                    type: "POST",
                    data: {questionPaper: jsonQ}
                }).success(function (data){
                   var url = "${createLink(controller: 'questionPaper', action: 'showQuestionPapers')}"
                   window.location.href = url;
                })
                .fail(function ( data ) {
                        alert('Unable to create question paper. Please check your settings');
                });
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

        function saveRow(sectionId, index){
            $(sectionId).datagrid('endEdit', index);
            //$(sectionId).datagrid('acceptChanges');
        }
    </script>
</head>
<body style="margin: 20px">

<div class="easyui-panel" title="Create Question Paper" style="width:1200px;height:500px;padding:10px;">
    <div class="easyui-layout" data-options="fit:true">

            <div data-options="region:'north'" style="height:60px;">
                <div>
                    <label style="padding-left: 10px;padding-right: 10px">Title</label>
                    <input type="text" id="paperTitle"/>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" id="createPaper">Create Question Paper</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addNewSection">Add New Section</a>

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
        <div data-options="region:'east',split:true" style="width:200px;padding:10px">
            <div class="easyui-accordion" data-options="fit:true">
                <div title="Paper Options">
                    <p>Help</p>
                </div>
                <div title="Select Subjects">
                    <ul id="st" class="easyui-tree" data-options="url:'${createLink(controller: 'subjectTag', action: 'list')}',fit:true,cascadeCheck:false,animate:false,checkbox:true">
                    </ul>
                </div>
            </div>
        </div>
        <div data-options="region:'center'" style="padding:10px" id="questionSections">
            <label><b>Question Paper Template</b></label>
        </div>
    </div>
</div>

<div id="sectionDlg" class="easyui-dialog" title="Section Options"
     data-options="iconCls:'icon-save',modal:true,resizable:true,closed:true, buttons:[{
                    text:'Save',
                    id: 'saveSectionDetail'
                    },{
                    text:'Close',
                    id: 'closeSectionDetail'
                    }]" style="width:400px;height:400px;padding:10px;">
    <label>Section Time<input class="easyui-numberbox" id="sectionDuration"/> </label>
    </br>
    <label>Section Instructions</label>
    <textarea rows="15" cols="40" id="sectionInstruction" title="Section Instructions">
    </textarea>
</div>


<div id="sectionNameDlg" class="easyui-dialog" title="Add New Section"
     data-options="iconCls:'icon-save',modal:true,resizable:true,closed:true, buttons:[{
                    text:'OK',
                    id: 'addNewSectionName'
                    },{
                    text:'Cancel',
                    id: 'closeSectioNameDlg'
                    }]" style="width:400px;height:400px;padding:10px;">
    <label>Section Name<input type="text" id="idSectionName"/> </label>
</div>

</body>
</html>