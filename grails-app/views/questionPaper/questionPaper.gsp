<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 06/04/13
  Time: 11:38 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Question Paper</title>
    <meta name="layout" content="menu"/>
    <meta charset="UTF-8">

    <g:javascript src="/jquery-easyui-1.3.2/jquery-1.8.0.min.js"/>
    <g:javascript src="/jquery-easyui-1.3.2/jquery.easyui.min.js"/>
    <script type="text/javascript" src= "http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>

    <g:javascript src="/bootstrap/js/bootstrap.min.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/bootstrap/css', file: 'bootstrap.min.css')}">
    <r:layoutResources />

    <script type="text/javascript">
        $(document).ready(function() {
            var questionUrl = "${createLink(controller: 'questionPaper', action: 'getQuestionsForPaper')}"
            $('#question').datagrid({
                //url:questionUrl
            });

            function showMe(){
                alert('User clicked on "foo."');
            }

            $('li','ul').live('click', function(event){
                var checkboxID = $(event.target).attr('id');
                alert(checkboxID);
                //alert('Click event');
            });

            $.ajax({
                url: questionUrl

            }).done(function(data){
                var questionArray = JSON.parse(data)
                for (var i=0; i < questionArray.length; i++){
                    var question = questionArray[i];
                    id = question.id;
                    var questionId = i;
                    questionId = questionId + 1;
                    var content = '<p>' + question.text +  '</p>'
                    $('body').append(content);
                    $('#qid').append('<li><a href="#" id=' + questionId + '>' + questionId + '</a></li>');
                }
                $('#qid').append('<li><a href="#" id="next">Next</a></li>');
            });
        })
    </script>
</head>
<body>
<div class="pagination">
    <ul id="qid">
        <li><a href="#" id="prev">Prev</a></li>
    </ul>
</div>
<div id="questionArea">

</div>
%{--<div class="easyui-panel" title="" style="width:1200px;height:500px">--}%

    %{--<div class="easyui-layout" data-options="fit:true">--}%
        %{--<div data-options="region:'center'">--}%
            %{--<label>Section Name</label> <input id="sectionName" style="width:200px"/>--}%
            %{--<label style="padding-left: 10px">Duration</label>--}%
            %{--<input type="text" class="easyui-numberbox" value="100" id="sectionDuration" disabled="true"/>--}%
            %{--<label style="padding-left: 2px">Minutes</label>--}%
            %{--<p>hello</p>--}%
        %{--</div>--}%
        %{--<div data-options="region:'south'" style="padding:10px">--}%
            %{--<div class="easyui-pagination" style="border:1px solid #ddd;" id="q" data-options="--}%
                %{--total: 50,--}%
                %{--pageSize: 1,--}%
                %{--showPageList: false,--}%
                %{--showRefresh: false,--}%
                %{--displayMsg: ''">--}%
            %{--</div>--}%
        %{--</div>--}%
    %{--</div>--}%
%{--</div>--}%
</body>
</html>