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
    <meta name="layout" content="empty"/>
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
            var questionUrl = "${createLink(controller: 'questionPaper', action: 'getQuestionsForPaper')}";
            var submitUrl = "${createLink(controller: 'questionPaper', action: 'submitTest')}";
            var questionResponseArray = new Array();
            var response = new Object();
            //Array of question for current section
            var questionArray;
            var sectionArray = new Array();
            var sectionPaginationArray = new Array();
            //current id of section
            var curSectionId = 0;
            //current id of question
            var curId = 1;
            $('#question').datagrid({
                //url:questionUrl
            });

            $('#sectionBtn').click(function (e) {
                var id = $(event.target).attr('id');
                var sectionId = parseInt(id.replace("sectionBtn",''))
                e.preventDefault();
                $(this).tab('show');
                showSection(sectionId);
                showQuestion(sectionId, 1);
            })

            $("body").on('click','.option', function(event) {
                var optionId = $(event.target).attr('id');
                var optionPair = optionId.split('.');
                var questionId = optionPair[1] - 1;
                var optionId = optionPair[2];
                var questionResponse
               // if(questionResponse.)
                questionResponse = questionResponseArray[questionId];
                if(typeof(questionResponse) != 'undefined' && questionResponse != null)
                {

                }else{
                    questionResponse = new Object();
                    questionResponse.answers = new Array();
                    questionResponseArray[questionId] = questionResponse;
                    questionResponse.questionId = questionId;
                }

                var checked = $(event.target).val();

                var thisCheck = $(this);
                if (this.checked == true)
                {
                     questionResponse.answers[optionId] = parseInt(optionId);
                }
                else
                {
                    questionResponse.answers[optionId] = -1;
                    //questionResponse.answers[optionId] = parseInt(optionId);
                }
            });

            function preserveQuestionResponse(selectedQId){
                var actualIndex = parseInt(selectedQId) - 1;
                var questionResponse = questionResponseArray[selectedQId-1];
                if(typeof(questionResponse) != 'undefined' && questionResponse != null)
                {
                    for (var iOption = 0; iOption < 4; iOption++){
                        var opResponse = questionResponse.answers[iOption];
                        if ((opResponse != 'undefined') && (opResponse != null) && (opResponse >= 0)){
                            var question = questionArray[selectedQId-1];
                            var optionId = "option" + "." + selectedQId + "." + iOption;
                            $('#option.1.0').prop("checked",true);
                        }
                    }
                }
            }

            function showSection(sectionId){
                var sectionPagination = sectionPaginationArray[sectionId];
                $('#qid').empty();
                $('#qid').append(sectionPagination);
                $('#qid').append('<li><a href="#" id="next">Next</a></li>');
                var sectionBtnId = "sectionBtn" + sectionId;
                $("#sectionBtn0").addClass('active');
            }

            function showQuestion(sectionId, curId){

                questionArray = sectionArray[sectionId].sectionQuestions;
                var question = questionArray[curId - 1];
                var questionResponse = questionResponseArray[curId-1];

                var content = '<p>' + question.text +  '</p>'
                $('#questionArea').empty();
                $('#questionArea').append(content);
                var optionsArray = new Array();
                optionsArray[0] = question.option1;
                optionsArray[1] = question.option2;
                optionsArray[2] = question.option3;
                optionsArray[3] = question.option4;
                for (var i =0; i < 4; i++){
                    var checkBoxId = "option" + "." + curId + "." + i;
                    var checkBoxValue = false;
                    var optionsContent;
                    if(typeof(questionResponse) != 'undefined' && questionResponse != null)
                    {
                        var opResponse = questionResponse.answers[i];
                        if ((opResponse != 'undefined') && (opResponse != null) && (opResponse >= 0)){
                            checkBoxValue = true;
                            optionsContent = '<label class="checkbox"> <input type="checkbox" class="option" id=' + checkBoxId + ' checked=' + checkBoxValue + '>' +
                                    optionsArray[i] +
                                    '</label>';
                            //var question = questionArray[curId-1];
                            //var optionId = "option" + "." + selectedQId + "." + iOption;
                            //$('#option.1.0').prop("checked",true);
                        }
                    }
                    if (checkBoxValue == false){
                        optionsContent = '<label class="checkbox"> <input type="checkbox" class="option" id=' + checkBoxId + '>' +
                            optionsArray[i] +
                            '</label>';
                    }
                    $('#questionArea').append(optionsContent);
                }
            }

            $('body').on('click','.qId', function(event){
                var questionId = $(event.target).attr('id');
                var sectionId = questionId.split(".")[0];
                var qid = questionId.split(".")[1];
                var canAct = true;
                if (questionId === "next"){
                    curId = parseInt(curId) + 1;
                    if (curId > questionArray.length){
                        //preserveQuestionResponse(curId);
                       canAct = false;
                       curId =  questionArray.length
                    }
                }else if (questionId === "prev"){

                    curId = parseInt(curId) - 1;
                    if (curId < 1){
                        //preserveQuestionResponse(curId);
                        canAct = false;
                        curId =  1;
                    }
                }else{
                    if (questionId != curId){
                        canAct = true;
                        curId = qid;
                    }
                }
                if (canAct === true){
                    showQuestion(sectionId,curId);
                    //preserveQuestionResponse(questionId);
                }
            });

            $.ajax({
                url: questionUrl

            }).done(function(data){

                sectionArray = JSON.parse(data).sectionList;

                for (var s=0; s < sectionArray.length; s++){
                    var section = sectionArray[s];
                    $('#sectionBtn').append('<button type="button" class="btn btn-primary" id=sectionBtn' + s + '>' + section.sectionName + '</button>');
                    //$('#sections').append('<li><a href="#" id=section' + s + '>' + section.sectionName + '</a></li>');
                    //$('#sectionBtn').append('<li><a href="#" id=section1' + s + '>' + "dummy" + '</a></li>');
                    questionArray = section.sectionQuestions;
                    var sectionLevelPagination='';
                    for (var i=0; i < questionArray.length; i++){
                        var question = questionArray[i];
                        id = question.id;
                        var questionId = i;
                        questionId = questionId + 1;
                        var content = '<p>' + question.text +  '</p>'
                        var questionSectionId = "qid" + s;
                        var sectionPagination = '<ul id=' + questionSectionId + '>';
                        //Every question has different qid sectionId.qid
                        sectionPagination = sectionPagination + '<li><a href="#" class="qId" id=' + s + '.' + questionId + '>' + questionId + '</a></li>';
                        sectionPagination = sectionPagination + '</ul>';
                        if (typeof(sectionLevelPagination) != 'undefined'){
                            sectionLevelPagination = sectionLevelPagination + sectionPagination;
                        }else{
                            sectionLevelPagination = sectionPagination;
                        }
                        //$('#qid').append(sectionPagination);
                        //$('#qid').append('<li><a href="#" class="qId" id=' + questionId + '>' + questionId + '</a></li>');
                    }
                    sectionPaginationArray.push(sectionLevelPagination);
                }
                showSection(curSectionId);
                showQuestion(curSectionId,curId);

            });

            $('#finishTest').bind('click', function(event) {
                response.candidateResponse = questionResponseArray;
                $.ajax({
                    url: submitUrl,
                    type: "POST",
                    async:false,
                    data: {response: JSON.stringify(questionResponseArray)}
                })
            });
        })
    </script>
</head>
<body>

<div class="btn-group" id="sectionBtn" data-toggle="buttons-radio" style="margin-top: 20px">
    %{--<button type="button" class="btn btn-primary">Dummy Section</button>--}%
</div>

<div class="pagination">
    <ul id="qid">
        <li><a href="#" id="prev">Prev</a></li>
    </ul>
</div>
<div id="questionArea">

</div>
<div id="finishTest" style="margin-right: 100px; float: right;">
    <button class="btn btn-large btn-success" type="button">Finish</button>
</div>
</body>
</html>