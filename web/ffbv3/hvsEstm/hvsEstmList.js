        
        function run() {
            
            // find table rows
            var $rows = $('#tbData').find('tr');
            var r = [];
            var hvsDate = '';

            // for each rows
            var i = 0;
            $rows.each(function () {

                // skip header
                if (i === 0) {i++;return;}

                // find all cells
                var $td = $(this).find('td');

                // set hvsDate once
                if (i === 1) {
                    hvsDate = $td.eq(0).text();
                }

                // if toRun checked
                if ($td.find('.toRun').is(":checked")){

                    // Add div to array  
                    r[r.length] = $td.eq(2).text();
                }
            });
            
            if (r.length === 0) {
                
                alert("No division selected");
                return;
            }

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

            // Output the result
            var dataJson = JSON.stringify(r);
            var jsonForServer = 
                    '{'
                    + '\n\"hvsDate\" : \"'  + hvsDate + '\"'
                    + '\n, \"divList\" : '  + dataJson
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
    
