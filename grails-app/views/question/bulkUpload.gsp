<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 27/03/13
  Time: 11:59 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Bulk Upload Questions</title>
    <meta name="layout" content="menu"/>
</head>
<body>
  <div style="margin-top: 20px">
     <g:form method="post"  action="bulkUpload" enctype="multipart/form-data">
         <p>Bulk Upload Questions</p>
         <fieldset>
             <input type="file" name="questionsFile"/>
             <input type="submit" name="Upload Questions"/>
         </fieldset>
     </g:form>
  </div>
</body>
</html>