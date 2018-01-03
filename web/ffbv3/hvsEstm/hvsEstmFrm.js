    var $TABLE = $('#tbData');
    var $apiAddress = '../../api/hvsEstmSaveAPI';

    $('.table-add').click(function () {
      var $clone = $TABLE.find('tr.hide').clone(true).removeClass('hide table-line');
      $TABLE.find('table').append($clone);
    });

    $('.table-remove').click(function () {
      $(this).parents('tr').detach();
    });

    $('.table-up').click(function () {
      var $row = $(this).parents('tr');
      if ($row.index() === 1) return; // Don't go above the header
      $row.prev().before($row.get(0));
    });

    $('.table-down').click(function () {
      var $row = $(this).parents('tr');
      $row.next().after($row.get(0));
    });

    // A few jQuery helpers for exporting only
    jQuery.fn.pop = [].pop;
    jQuery.fn.shift = [].shift;

    function callServer(jsonForServer, saveType){
        
        $.get( $apiAddress, {json : jsonForServer})
            .done(function( data ) {
                if (data.success){
                    alert('Success');
                    $('#errMsg').html('');
                    $('#status').text(saveType);
                }
                else{
                    alert('Error');
                    $('#errMsg')
                        .html('Error.<br><br>Detail for tech support:<br>' 
                        + data.msg);
                }
            });
        
    }

    function save(saveType) {
        var $rows = $TABLE.find('tr:not(:hidden)');
        var data = [];

        // Turn all existing rows into a loopable array
        var i = 0;
        $rows.each(function () {
            
          // skip header
          if (i === 0) {i++;return;}
          
          var r = {};
          var $td = $(this).find('td');
          
          r['taskType'] = $td.eq(0).find(':selected').text();
          r['block'] = $td.eq(1).text();
          r['size1'] = $td.eq(2).text();
          
          // add to rows data
          data.push(r);
        });

        // Output the result
        var dataJson = JSON.stringify(data);
        var jsonForServer = 
                '{'
                + '\"hvsDate\" : ' + '\"' + $('#hvsDt').val() + '\"' 
                + '\n, \"divID\" : ' + '\"' + $('#divID').val() + '\"' 
                + '\n, \"status\" : ' + '\"' + saveType + '\"' 
                + '\n, \"remark\" : ' + '\"' + $('#remark').val() + '\"' 
                + '\n, \"kg\" : "0\"' 
                + '\n, \"hvsEstmID\" : \"' + $('#hvsEstmID').val() + "\""
                + '\n, \"dtl\" : '  + dataJson
                + '\n}'  
                ;
                
        // uncomment to debug
        //$('#debug').text(jsonForServer);
        
        $('#json').val(jsonForServer);
  
        // call server
        //callServer(jsonForServer, saveType);
        $("#form1").submit();
    };

    $("#saveDrftBtn").click(function(){save('DRFT');});
    $("#saveFnalBtn").click(function(){save('FNAL');});
