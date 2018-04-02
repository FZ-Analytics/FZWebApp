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
        <style>
            .lds-ellipsis {
                display: inline-block;
                position: relative;
                width: 64px;
                height: 64px;
            }
            .lds-ellipsis div {
                position: absolute;
                top: 27px;
                width: 11px;
                height: 11px;
                border-radius: 50%;
                background: orange;
                animation-timing-function: cubic-bezier(0, 1, 1, 0);
            }
            .lds-ellipsis div:nth-child(1) {
                left: 6px;
                animation: lds-ellipsis1 0.6s infinite;
            }
            .lds-ellipsis div:nth-child(2) {
                left: 6px;
                animation: lds-ellipsis2 0.6s infinite;
            }
            .lds-ellipsis div:nth-child(3) {
                left: 26px;
                animation: lds-ellipsis2 0.6s infinite;
            }
            .lds-ellipsis div:nth-child(4) {
                left: 45px;
                animation: lds-ellipsis3 0.6s infinite;
            }
            @keyframes lds-ellipsis1 {
                0% {
                    transform: scale(0);
                }
                100% {
                    transform: scale(1);
                }
            }
            @keyframes lds-ellipsis3 {
                0% {
                    transform: scale(1);
                }
                100% {
                    transform: scale(0);
                }
            }
            @keyframes lds-ellipsis2 {
                0% {
                    transform: translate(0, 0);
                }
                100% {
                    transform: translate(19px, 0);
                }
            }

        </style>
        <script>
            $(document).ready(function () {
                $("#loader").hide();
                $(".exportBtn").click(function () {
                    var type = $(this).val();

                    var $apiAddress = '../../api/ExportDb/ExportDb';

                    var jsonForServer = '{\"type\": \"' + type + '\"}';

                    $("#body").fadeOut();
                    $("#loader").fadeIn();
                    //Submit to Result_Shipment
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if (data == 'OK') {
                            $("#body").fadeIn();
                            $("#loader").fadeOut();
                            location.reload();
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
        
        <div id="loader" style="text-align: center;">
            <div class="lds-ellipsis"><div></div><div></div><div></div><div></div></div>
            <p>Processing</p>
        </div>

        <div id="body" class='col-md-12' style='text-align: center;'>
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
