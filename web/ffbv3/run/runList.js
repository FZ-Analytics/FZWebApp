        var hvsDate = '';

        function getHvsEstmRowArray() {
            
            // find table rows
            var $rows = $('#tbData').find('tr');
            var r = []; // harvest estimate array

            // for each rows
            var i = 0;
            $rows.each(function () {

                // skip header
                if (i === 0) {i++;return;}

                // find all cells
                var $td = $(this).find('td');

                // set hvsDate once
                if (i === 1) {
                    hvsDate = $td.eq(1).text();
                }

                // if toRun checked
                if ($td.find('.toRun').is(":checked")){

                    // Add div to array  
                    r[r.length] = $td.eq(3).text();
                }
            });
            
            return r;
        }

        function getVehicleRowArray() {
            
            // find table rows
            var $rows = $('#tbVehicle').find('tr');
            var r = []; // vehicle array

            // for each rows
            var i = 0;
            $rows.each(function () {

                // skip header
                if (i === 0) {i++;return;}

                // find all cells
                var $td = $(this).find('td');

                // if toRun checked
                if ($td.find('.toRun').is(":checked")){

                    // Add div to array  
                    r[r.length] = $td.eq(1).text();
                }
            });
            
            return r;
        }

        function getParamStr() {
            var $paramStr = '';
            
            // get algo params as json elements
            // if param container exist
            if ($('#dvParam').length > 0){
                
                // get all input elm
                var $input = $('#dvParam').find('.fzInput');
                var $paramStr = '';
                
                // for each input
                $input.each(function () {
                
                    // create the param str
                    $paramStr += 
                            ',\n \"' + $(this).attr('id') + '\"'
                            + ' : \"' + $(this).val() + '\"'; 
                });
                
            }
            return $paramStr;
        }
        
        function run() {
            
            var hs = getHvsEstmRowArray();
            if (hs.length === 0) {
                
                alert("No division selected");
                return;
            }

            var vs = getVehicleRowArray();
            if (vs.length === 0) {
                
                alert("No vehicle selected");
                return;
            }
            
            var $paramStr = getParamStr();

            // Output the result
            var hvsEstmJson = JSON.stringify(hs);
            var vehicleJson = JSON.stringify(vs);
            var jsonForServer = 
                    '{'
                    + '\n\"hvsDate\" : \"'  + hvsDate + '\"'
                    + '\n, \"divList\" : '  + hvsEstmJson
                    + '\n, \"vehicleList\" : '  + vehicleJson
                    + $paramStr
                    + '\n}'  
                    ;

            // uncomment to debug
            //$('#debug').text(jsonForServer);

            $('#json').val(jsonForServer);

            // call server
            $("#form1").submit();
        };

        // set run button event handler
        $("#runBtn").click(function(){run();});
        
        // set view params button event handler
        $("#paramsBtn").click(function(){
            $('#dvParam').toggle();
        });
    
