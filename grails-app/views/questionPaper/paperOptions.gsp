<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 24/03/13
  Time: 10:16 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
</head>
<body>
<table>
    <thead>
    <th>
        Link
    </th>
    </thead>
    <tr>
        <td>
           <a href= "${createLink(action :'createPaper')}">Create Paper</a>
        </td>
    </tr>
    <tr>
        <td>
            <g:createLinkTo action="paper" controller="questionPaper">Show Paper</g:createLinkTo>
        </td>
    </tr>
</table>
</body>
</html>