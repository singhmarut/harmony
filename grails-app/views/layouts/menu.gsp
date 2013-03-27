<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 26/03/13
  Time: 9:05 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <g:javascript src="/jquery-ui/js/jquery-1.9.1.js"/>
    <g:javascript src="/jquery-ui/js/jquery-ui-1.10.2.custom.js"/>
    <g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>

    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>


    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.base.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.energyblue.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.ui-redmond.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.darkblue.css')}" type="text/css" />

    %{--<g:javascript src="/DataTables-1.9.4/media/js/jquery.dataTables.js"/>--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery.jqGrid-4.4.4/css', file: 'ui.jqgrid.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui/css/ui-lightness', file: 'jquery-ui-1.10.2.custom.css')}"
          type="text/css">
    <g:layoutHead/>
    <r:layoutResources />

    <shiro:isNotLoggedIn>
        <a href="${createLink(controller: 'auth')}">Login</a>
    </shiro:isNotLoggedIn>

    <shiro:isLoggedIn>
        Welcome <shiro:principal/>
        <a href="${createLink(controller: 'auth', action: 'signOut')}">Logout</a>
    </shiro:isLoggedIn>
</head>
<body>
<div id='content'>
    <script type="text/javascript">
        $(document).ready(function () {
            // Create a jqxMenu
            $("#jqxMenu").jqxMenu({ width: 600, height: 30, theme: 'summer'});
        });
    </script>
    <div id='jqxWidget'>
        <div id='jqxMenu'>
            <ul>
                <li><a href="${createLink(controller: 'home', action:'index')}">Home</a></li>
                <li>About Us
                    <ul>
                        <li><a href="${createLink(controller: 'home', action:'index')}">History</a></li>
                        <li><a href="#">Our Vision</a></li>
                    </ul>
                </li>
                <li>Question Management
                    <ul>
                        <li><a href="${createLink(controller: 'skills', action:'index')}">Manage My Tags</a></li>
                        <li><a href="${createLink(controller: 'question', action:'showQuestionList')}">Manage Questions</a></li>
                    </ul>
                </li>
                <li><a href="${createLink(controller: 'questionPaper', action:'createPaper')}"></a>Question Paper</li>
                <li>Administrator
                    <ul>
                        <li><a href="${createLink(controller: 'admin', action:'index')}"></a>Manage Account</li>
                    </ul>
                </li>
                <li><a href="#">Contact Us</a></li>
            </ul>
        </div>
    </div>
</div>
<g:layoutBody/>
</body>
</html>