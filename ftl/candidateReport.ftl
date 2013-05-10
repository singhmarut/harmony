<html>
<head>
    <title>
        Report for ${user.username}
    </title>
    <#--<link rel="stylesheet" type="text/css"-->
          <#--href="<@spring.url '/js/css/style.css'/>"/>-->
</head>
<body>
    <h1>Welcome ${user.username}!</h1>
    <#list answerSheet.candidateAnswers as answer>
        <table>
            <thead>
                <th style="text-align: left; color: #0044cc;">
                    Question Id
                </th>
                <th style="text-align: left; color: #0044cc;">
                    Marks Scored
                </th>
            </thead>
            <tr>
                <td>
                    ${answer.questionId}
                </td>
                <td>
                    ${answer.marksScored}
                </td>
            </tr>
        </table>
    </#list>
</body>
</html>

