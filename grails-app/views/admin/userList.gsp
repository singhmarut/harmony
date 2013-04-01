<!DOCTYPE html>
<html>
<head>
    <title>Grails Runtime Exception</title>
    <meta name="layout" content="menu"/>
    <g:javascript src="/jquery-ui/js/jquery-1.9.1.js"/>
    <g:javascript src="/jquery-ui/js/jquery-ui-1.10.2.custom.js"/>
    <g:javascript src="/jquery.jqGrid-4.4.4/js/jquery.jqGrid.src.js"/>

    <g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqx-all.js"/>
    %{--<g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqxcore.js"/>--}%
    %{--<g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqxbuttons.js"/>--}%
    %{--<g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqxgrid.js"/>--}%
    %{--<g:javascript src="/jqwidgets-ver2.8/jqwidgets/jqxwindow.js"/>--}%
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.base.css')}" type="text/css" />
    <link rel="stylesheet"
          href="${resource(dir: 'js/jqwidgets-ver2.8/jqwidgets/styles', file: 'jqx.ui-redmond.css')}" type="text/css" />

    %{--<link rel="stylesheet" href="${resource(dir: 'js/jquery.jqGrid-4.4.4/css', file: 'ui.jqgrid.css')}" type="text/css">--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui/css/ui-lightness', file: 'jquery-ui-1.10.2.custom.css')}"
          type="text/css">
    <r:layoutResources />
    <script type="text/javascript">
        $(document).ready(function() {
            var url = "${createLink(controller: 'admin', action: 'list')}";
            //prepare the data
            var source =
            {
                datatype: "json",
                datafields: [
                    { name: 'id', type: 'number' },
                    { name: 'username' },
                    { name: 'firstName' },
                    { name: 'lastName' },
                    { name: 'enabled' }
                ],
                id: 'id',
                url: url
            };

            var renderer = function (id) {
                var finalId = "btn" + id;
                //return 'hello';
                String
                return '<input type="button" onClick="buttonclick(event)" class="gridButton" id= "dummy" value="Disable Account" />';
            }
            var buttonclick = function (event)
            {
                var id = event.target.id;
                $("#jqxgrid").jqxGrid('deleterow', id);
            }
            $("#btnOK").bind('click', function () {
                alert('Submit Button Clicked');
                var newData = {"totalRecords":1,"curPage":1,"data":[{"text":"hello"}]};
                var newRow = $("#example").jqxGrid('addrow', null, newData);
                $('#example').jqxGrid('selectrow', newData.uid);
            });

            var firstName = $( "#firstName" ),
            lastName = $( "#lastName" ),
            email = $( "#email" ),
            password = $( "#password" ),
            allFields = $( [] ).add( firstName ).add( lastName).add( email ).add( password ),
            tips = $( ".validateTips" );

            function updateTips( t ) {
                tips
                        .text( t )
                        .addClass( "ui-state-highlight" );
                setTimeout(function() {
                    tips.removeClass( "ui-state-highlight", 1500 );
                }, 500 );
            }

            function checkLength( o, n, min, max ) {
                if ( o.val().length > max || o.val().length < min ) {
                    o.addClass( "ui-state-error" );
                    updateTips( "Length of " + n + " must be between " +
                            min + " and " + max + "." );
                    return false;
                } else {
                    return true;
                }
            }

            function checkRegexp( o, regexp, n ) {
                if ( !( regexp.test( o.val() ) ) ) {
                    o.addClass( "ui-state-error" );
                    updateTips( n );
                    return false;
                } else {
                    return true;
                }
            }

            $( "#dialog-form" ).dialog({
                autoOpen: false,
                height: 400,
                width: 360,
                modal: true,
                buttons: {
                    "Create an account": function() {
                        var bValid = true;
                        allFields.removeClass( "ui-state-error" );

                        bValid = bValid && checkLength( firstName, "firstName", 3, 16 );
                        bValid = bValid && checkLength( lastName, "lastName", 3, 16 );
                        bValid = bValid && checkLength( email, "email", 6, 80 );
                        bValid = bValid && checkLength( password, "password", 5, 16 );

                        //bValid = bValid && checkRegexp( name, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
                        // From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
                        bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
                        bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

                        if ( bValid ) {

                            var rows = $('#example').jqxGrid('getrows').length + 1 ;
                            var user = new Object();
                            user.username = email.val();
                            user.firstName = firstName.val();
                            user.lastName = lastName.val();
                            $.ajax({
                                url: "${createLink(controller: 'admin', action: 'createUserAccount')}",
                                type: "POST",
                                dataType: 'json',
                                data: {user: JSON.stringify(user)}
                            }).success(function( msg ) {
                                $( this ).dialog( "close" );
                                alert( "Data Saved: " + msg );
                            });

                        }
                    },
                    Cancel: function() {
                        $( this ).dialog( "close" );
                    }
                },
                close: function() {
                    allFields.val( "" ).removeClass( "ui-state-error" );
                }
            });

            //$("#addNewAccountWindow ").jqxWindow({okButton: $('#okButton'),autoOpen: false, height:90, width: 150, theme: 'summer', isModal: true });

            $('#addButton').on('click',

                    function(){
                        $( "#dialog-form").dialog( "open" );
                    } );

            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadComplete: function (data) { },
                loadError: function (xhr, status, error) { }
            });
            $("#addButton").jqxButton({ width: '150', height: '25'});
            $("#removeButton").jqxButton({ width: '150', height: '25'});
            $("#example").jqxGrid(
                    {
                        source: dataAdapter,
                        columnsresize: true,
                        theme: 'ui-redmond',
                        pageable: true,
                        autoheight: true,
                        editable: true,
                        width: 500,
                        columns: [
                            { text: '', datafield: 'id', width: 100 },
                            { text: 'User Name', datafield: 'username', width: 100 },
                            { text: 'First Name', datafield: 'firstName', width: 100 },
                            { text: 'Last Name', datafield: 'lastName', width: 100 },
                            { text: 'Enabled', datafield: 'enabled', width: 100,columntype: 'checkbox', cellsalign: 'right', cellsformat: 'c2' }
                        ]
                    });
        });

    </script>

</head>
<body>
<div style="margin-bottom: 10px; margin-top: 20px">
    <input id="addButton" type="button" value="Add New Account" />
    <input id="removeButton" type="button" value="Disable Account" />
</div>

<div id="dialog-form" title="Create new user">
    <p class="validateTips">All form fields are required.</p>
    <form>
        <fieldset>
            <label for="firstName">First Name</label>
            <input type="text" name="firstName" id="firstName" class="text ui-widget-content ui-corner-all"/>
            <label for="lastName">Last Name</label>
            <input type="text" name="Last Name" id="lastName" class="text ui-widget-content ui-corner-all" />
            <label for="email">Email</label>
            <input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all" />
            <label for="password">Password</label>
            <input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
        </fieldset>
    </form>
</div>

<div id='addNewAccountWindow'>
    %{--<div>Add new Account</div>--}%
    %{--<div id="btnOK"/>--}%
</div>

<div id="example">
    %{--<div>Header</div>--}%
    %{--<div>Content</div>--}%
</div>
</body>
</html>
