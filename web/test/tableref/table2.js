var $TABLE2 = $('#tbVehicle');
//var $BTN = $('#export-btn');
//var $EXPORT = $('#export');

$('.table2-add').click(function () {
  var $clone = $TABLE2.find('tr.hide').clone(true).removeClass('hide table-line');
  $TABLE2.find('table').append($clone);
});

$('.table2-remove').click(function () {
  $(this).parents('tr').detach();
});

$('.table2-up').click(function () {
  var $row = $(this).parents('tr');
  if ($row.index() === 1) return; // Don't go above the header
  $row.prev().before($row.get(0));
});

$('.table2-down').click(function () {
  var $row = $(this).parents('tr');
  $row.next().after($row.get(0));
});

//// A few jQuery helpers for exporting only
//jQuery.fn.pop = [].pop;
//jQuery.fn.shift = [].shift;
//
//$BTN.click(function () {
//  var $rows = $TABLE2.find('tr:not(:hidden)');
//  var headers = [];
//  var data = [];
//  
//  // Get the headers (add special header logic here)
//  $($rows.shift()).find('th:not(:empty)').each(function () {
//    headers.push($(this).text().toLowerCase());
//  });
//  
//  // Turn all existing rows into a loopable array
//  $rows.each(function () {
//    var $td = $(this).find('td');
//    var h = {};
//    
//    // Use the headers from earlier to name our hash keys
//    headers.forEach(function (header, i) {
//      h[header] = $td.eq(i).text();   
//    });
//    
//    data.push(h);
//  });
//  
//  // Output the result
//  $EXPORT.text(JSON.stringify(data));
//});