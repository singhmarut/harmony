<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 23/03/13
  Time: 11:52 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
    <meta name="layout" content="menu"/>
</head>
<body>

<g:form>
    <g:each in="${questionPaper.sections}" var="section">
       <g:each in="${section.questions}" var="question">
           <div id="questionText" align="left">
                <table>
                    <tr>
                        ${question.text}
                    </tr>
                </table>
           </div>
       </g:each>
    </g:each>
</g:form>
</body>
</html>