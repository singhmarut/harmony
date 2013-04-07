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
    %{--<g:javascript src="/jquery-easyui-1.3.2/jquery-1.8.0.min.js"/>--}%
    %{--<g:javascript src="/jquery-easyui-1.3.2/jquery.easyui.min.js"/>--}%

    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes/default', file: 'easyui.css')}">
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/demo', file: 'demo.css')}">
    <link rel="stylesheet" href="${resource(dir: '/js/jquery-easyui-1.3.2/themes', file: 'icon.css')}">

    <r:layoutResources />

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