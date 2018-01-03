    var $TABLE = $('#tbDemand');
    var $BTN = $('#export-btn');
    var $EXPORT = $('#export');

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

    $BTN.click(function () {
        var $rows = $TABLE.find('tr:not(:hidden)');
        var headers = [];
        var data = [];

        // Get the headers (add special header logic here)
        $($rows.shift()).find('th:not(:empty)').each(function () {
          headers.push($(this).text().toLowerCase());
        });

        // Turn all existing rows into a loopable array
        $rows.each(function () {
          var i=0;  
          var r = [];
          $(this).find('.celVal').each(function(){

              // if contains select
              var val;
              if ($(this).find('select').length){
                  // get selected key
                  val = $(this).find(':selected').text();
              }
              else {
                  // else get cell value
                  val = $(this).text();
              }
              console.log('value = ' + val);
              // add to row cell array
              r[i++] = val;
          });

          // add to rows data
          data.push(r);
        });

        // Output the result
        var result = JSON.stringify(data);
        $EXPORT.text(result);
  
        // call server
        sendCommand(result);
});

function sendCommand(jsonIn){
     var data_file = "harvestplanprc.jsp?json=" + jsonIn;
     http_request = new XMLHttpRequest();
     console.log('Sending command');
     try{
        // Opera 8.0+, Firefox, Chrome, Safari
        http_request = new XMLHttpRequest();
     }catch (e){
        // Internet Explorer Browsers
        try{
           http_request = new ActiveXObject("Msxml2.XMLHTTP");

        }catch (e) {

           try{
              http_request = new ActiveXObject("Microsoft.XMLHTTP");
           }catch (e){
              // Something went wrong
              alert("Your browser broke!");
              return false;
           }

        }
     }
     // send it
     console.log("Get data");
     http_request.onreadystatechange = commandResponded; // callback
     http_request.open("GET", data_file, true);
     http_request.send();
}

function commandResponded(){

    console.log('Dload done');

    // if http state ready
   if (http_request.readyState === 4  ){

        // if request not empty
        var rsp = http_request.responseText; 
        console.log('Response = ' + rsp);
        if (rsp.trim() !== ''){
            alert(rsp);
        }
        else {
            // empty response string
            console.log('Empty Response');
        }

   }
}

