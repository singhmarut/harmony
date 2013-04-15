<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  %{--<meta name="layout" content="main" />--}%
  <title>Login</title>
    <meta name="layout" content="menu" />

    <g:javascript src="/bootstrap/js/bootstrap.min.js"/>

    <link rel="stylesheet" href="${resource(dir: '/js/bootstrap/css', file: 'bootstrap.min.css')}">
    <r:layoutResources />
</head>
<body>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
    <div class="form-actions">
        <g:form action="signIn">
            %{--<div data-options="region:'west'" style="height:50px;padding:10px">--}%
            <input type="hidden" name="targetUri" value="${targetUri}" />
            <table>
                <tbody>
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="username" value="${username}" /></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password" value="" /></td>
                </tr>
                <tr>
                    <td>Remember me?:</td>
                    <td><g:checkBox name="rememberMe" value="${rememberMe}" /></td>
                </tr>
                <tr>
                    <td />
                    <td><input type="submit" value="Sign in" /></td>
                </tr>
                </tbody>
            </table>
            %{--</div>--}%
        </g:form>
    </div>
    <div class="form-actions">
        <form class="form-horizontal" action="${createLink(controller: 'auth', action: 'authorizeKey')}">
            <div class="control-group">
                <label class="control-label" for="inputEmail">Email</label>
                <div class="controls">
                    <input type="text" id="inputEmail" name="email" placeholder="Email">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="inputEmail">Authorization Key</label>
                <div class="controls">
                    <input type="text" id="authKey" name="authKey" placeholder="Test Key">
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn-info" id="loadTest">Sign in and Start Test</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
