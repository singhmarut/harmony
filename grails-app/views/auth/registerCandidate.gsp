<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 06/04/13
  Time: 5:34 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
    <meta name="layout" content="menu"/>
    <meta charset="UTF-8">

    <g:javascript src="/jquery-easyui-1.3.2/jquery-1.8.0.min.js"/>
    %{--<g:javascript src="/jquery-easyui-1.3.2/jquery.easyui.min.js"/>--}%
    %{--<script type="text/javascript" src= "http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>--}%
    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>
    <g:javascript src="/bootstrap/js/bootstrap.min.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/bootstrap/css', file: 'bootstrap.min.css')}">

    <r:layoutResources />
    <script type="text/javascript">
    $(document).ready(function() {

        $('#loadTest').bind('click', function(){
            var user = new Object()
            var email = $('#inputEmail').val();
            var password = $('#inputPassword').val();
            user.email = email;
            user.password = password;
            var registerUrl = "${createLink(controller: 'auth', action: 'registerCandidate')}"
            $.ajax({
                url: registerUrl,
                dataType: "json",
                type: "POST",
                data: {user: JSON.stringify(user)}
            }).done( function(response) {
                if (response.redirect) {
                    window.location.href = response.redirect;
                }
            })
        })
    })
    </script>

</head>
<body>

    <div style="margin:10px 0;"></div>
    <form class="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="inputEmail">Email</label>
            <div class="controls">
                <input type="text" id="inputEmail" placeholder="Email">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="inputPassword">Password</label>
            <div class="controls">
                <input type="password" id="inputPassword" placeholder="Password">
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <label class="checkbox">
                    <input type="checkbox"> Remember me
                </label>
                <button type="submit" class="btn-info" id="loadTest">Sign in and Start Test</button>
            </div>
        </div>
    </form>
</body>
</html>