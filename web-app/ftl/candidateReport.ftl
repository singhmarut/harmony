<html>
<head>
    <title>
        Report for ${user.emailId} for ${questionPaper.title}
    </title>
</head>
<body>
    <h1>Welcome ${user.emailId}!</h1>
    <#list answerSheet.candidateAnswers as answer>
        <table>
            <thead>
                <th>
                    Question Id
                </th>
                <th>
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

