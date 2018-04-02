    var $runId = $("#runId").val();
    var $apiGetData = '../../api/progressTrackAPI/getData';
    var $apiUpdData = '../../api/progressTrackAPI/updateData';
    var jsonForServer = '{\"runId\": \"'+$runId+'\"}';
    var data = [];

    var $UpdBTN = $('#submit-btn');
    var $RefBTN = $('#refresh-btn');
    var data = [];
    
    $UpdBTN.click(function () {
        // call server
        alert('Error');
        $.post( $apiUpdData, {json : jsonForServer})
          .done(function() {
                if (data.success){
                    alert('Success');
                    $('#errMsg').html('');
                }
                else{
                    alert('Error');
                    $('#errMsg')
                        .html('Error.<br><br>Detail for tech support:<br>' 
                        + data.msg);
                }
          });
    });
