<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Run</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
            $(document).ready(function () {
                $("#loader").hide();
                $(".submitBtn").click(function () {
                    var type = $(this).val();

                    var $apiAddress = '../../../api/ExportDb/ExportDb';

                    var jsonForServer = '{\"type\": \"' + type + '\"}';

                    $("#body").fadeOut();
                    $("#loader").fadeIn();
                    //Submit to Result_Shipment
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if (data == 'OK') {
                            $("#body").fadeIn();
                            $("#loader").fadeOut();
                            location.reload()
                        } else {
                            $("#body").fadeIn();
                            $("#loader").fadeOut();
                            alert(data);
                        }
                    });
                });
            });
        </script>

        <h3>Export Database to Fiza</h3>
        <br>
        <br>

        <div class='col-md-12' style='text-align: center;'>
            <div class='col-md-4'>
                <button id="customer" class="btn btn-primary btn-sm exportBtn" type="submit" value="customer">Export Database Customer</button>
            </div>
            <div class='col-md-4'>
                <button id="truck" class="btn btn-primary btn-sm exportBtn" type="submit" value="truck">Export Database Truck</button>
            </div>
            <div class='col-md-4'>
                <button id="salesman" class="btn btn-primary btn-sm exportBtn" type="submit" value="salesman">Export Database Salesman</button>
            </div>
        </div>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
