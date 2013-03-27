<%--
  Created by IntelliJ IDEA.
  User: marut
  Date: 24/03/13
  Time: 12:02 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Create Question Paper - Step 1</title>
    <meta name="layout" content="menu"/>
    <g:javascript src='jquery.jqGrid-4.4.4/js/jquery-1.9.0.min.js' />
    <script src="http://yui.yahooapis.com/3.9.0/build/yui/yui-min.js"></script>

    <r:layoutResources />

    <script type="text/javascript">
        // Create a YUI sandbox on your page.
        YUI().use('node', 'event', function (Y) {
            // The Node and Event modules are loaded and ready to use.
            // Your code goes here!
        });
        YUI().use('datatable', function (Y) {
            // DataTable is available and ready for use. Add implementation
            // code here.
            var data = [
                { id: "ga-3475", name: "gadget",   price: "$6.99", cost: "$5.99" },
                { id: "sp-9980", name: "sprocket", price: "$3.75", cost: "$3.25" },
                { id: "wi-0650", name: "widget",   price: "$4.25", cost: "$3.75" }
            ];

            var table = new Y.DataTable({
                columns: [{
                    key: "section",
                    label: "Section Name",
                    formatter:      '<input type="text"/>'
                }, {
                    key: "duration",
                    label: "Duration",
                    formatter:      '<input type="text"/>'
                },{
                    key: "message",
                    label: "Message",
                    allowHTML:  true,
                    formatter:      '<input type="text"/>'
                }],
                data: data,
                // Optionally configure your table with a caption
                caption: "Create Section for Question Paper",
                scrollable: "y",
                height: "200px",
                width:  "400px",
                // and/or a summary (table attribute)
                summary: "Test Sections"
            });

            table.render("#example");
        });
    </script>
</head>
<body class="yui3-skin-sam">
<g:form>
    <table>

    </table>
     <table>
        <tr>
            <td>
                <g:textField name="sectionName" />
            </td>
            <td>
               <g:textField name="duration"/>
            </td>
            <td>
                <g:textField name="instruction"/>
            </td>
            <td>
                <a href="${createLink(action: '')}">Select Questions</a>
            </td>
        </tr>
     </table>

</g:form>
</body>
</html>