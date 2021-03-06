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

    <g:javascript src="/bootstrap/js/bootstrap.min.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/bootstrap/css', file: 'bootstrap.min.css')}">

    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.base.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.energyblue.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.ui-redmond.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.darkblue.css')}" type="text/css" />

    <link rel="stylesheet" href="${resource(dir: 'js/jquery.jqGrid-4.4.4/css', file: 'ui.jqgrid.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui/css/ui-lightness', file: 'jquery-ui-1.10.2.custom.css')}"
          type="text/css">
    <g:layoutHead/>
    <shiro:isNotLoggedIn>
        <div class="btn-group">
            <a class="btn" href="${createLink(controller: 'auth')}">
                Login
            </a>
        </div>
    </shiro:isNotLoggedIn>
    <shiro:isLoggedIn>
        <shiro:hasAnyRole in="['ROLE_ADMIN','ROLE_CANDIDATE']">
        Welcome <shiro:principal/>
        <a href="${createLink(controller: 'auth', action: 'signOut')}">Logout</a>
        </shiro:hasAnyRole>
    </shiro:isLoggedIn>
    <r:layoutResources />

</head>
<body>
<div id='content'>
    <script type="text/javascript">
        $(document).ready(function () {
            // Create a jqxMenu
//            $("#jqxMenu").jqxMenu({ width: 600, height: 30, theme: 'energyblue'});
        });
    </script>
    <div id='jqxWidget'>

            <shiro:hasPermission permission="ADD_QUESTION_PERMISSION">
            <div class="btn-group">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    Question Management
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                        <li><a href="${createLink(controller: 'subjectTag', action:'index')}">Manage Subjects</a></li>
                        <li><a href="${createLink(controller: 'question', action:'showQuestionList')}">Manage Tags</a></li>
                        <li><a href="${createLink(controller: 'question', action:'showBulkUpload')}">Upload Questions</a></li>
                </ul>
            </div>
            </shiro:hasPermission>

            <shiro:isLoggedIn>
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        Reports
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${createLink(controller: 'reports', action:'showCandidateReport')}">My Reports</a></li>
                    </ul>
                </div>
            </shiro:isLoggedIn>
            <shiro:hasRole name="ROLE_ADMIN">
                <div class="btn-group">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    Question Paper
                    <span class="caret"></span>
                </a>
                    <ul class="dropdown-menu">
                        <li><a href="${createLink(controller: 'admin', action:'index')}">Manage Templates</a></li>
                        <li><a href="${createLink(controller: 'questionPaper', action:'createPaper')}">Create Question Paper</a></li>
                        <li><a href="${createLink(controller: 'questionPaper', action:'showQuestionPapers')}">Show Question Papers</a></li>
                    </ul>
                </div>
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        Admin
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${createLink(controller: 'admin', action:'index')}">Manage Accounts</a></li>
                    </ul>
                </div>
            </shiro:hasRole>
            <div class="btn-group">
                %{--<li><a href="#">Contact Us</a></li>--}%
            </div>


        %{--<div id='jqxMenu' class="btn-group">--}%
            %{--<ul class="dropdown-menu">--}%
                %{--<a class="btn dropdown-toggle" data-toggle="dropdown" href="${createLink(controller: 'home', action:'index')}">Home</a>--}%
                %{--<li>About Us--}%
                    %{--<ul class="dropdown-menu">--}%
                        %{--<li><a href="${createLink(controller: 'home', action:'index')}">History</a></li>--}%
                        %{--<li><a href="#">Our Vision</a></li>--}%
                    %{--</ul>--}%
                %{--</li>--}%
                %{--<shiro:hasPermission permission="ADD_QUESTION_PERMISSION">--}%
                %{--<li>Question Management--}%
                    %{--<ul>--}%
                        %{--<li><a href="${createLink(controller: 'subjectTag', action:'index')}">Manage Subjects</a></li>--}%
                        %{--<li><a href="${createLink(controller: 'question', action:'showQuestionList')}">Manage Tags</a></li>--}%
                        %{--<li><a href="${createLink(controller: 'question', action:'showBulkUpload')}">Upload Questions</a></li>--}%
                    %{--</ul>--}%
                %{--</li>--}%
                %{--</shiro:hasPermission>--}%

                %{--<shiro:isLoggedIn>--}%
                    %{--<li>Reports--}%
                        %{--<ul>--}%
                            %{--<li><a href="${createLink(controller: 'reports', action:'showCandidateReport')}">My Reports</a></li>--}%
                        %{--</ul>--}%
                    %{--</li>--}%
                %{--</shiro:isLoggedIn>--}%

                %{--<shiro:hasRole name="ROLE_ADMIN">--}%
                %{--<li>Question Paper--}%
                    %{--<ul>--}%
                        %{--<li><a href="${createLink(controller: 'admin', action:'index')}">Manage Templates</a></li>--}%
                        %{--<li><a href="${createLink(controller: 'questionPaper', action:'createPaper')}">Create Question Paper</a></li>--}%
                        %{--<li><a href="${createLink(controller: 'questionPaper', action:'showQuestionPapers')}">Show Question Papers</a></li>--}%
                    %{--</ul>--}%
                %{--</li>--}%
                %{--<li>Admin--}%
                    %{--<ul>--}%
                        %{--<li><a href="${createLink(controller: 'admin', action:'index')}">Manage Accounts</a></li>--}%
                    %{--</ul>--}%
                %{--</li>--}%
                %{--</shiro:hasRole>--}%

                %{--<li><a href="#">Contact Us</a></li>--}%
            %{--</ul>--}%
        %{--</div>--}%
    </div>
</div>
<g:layoutBody/>
<r:layoutResources />
</body>
</html>