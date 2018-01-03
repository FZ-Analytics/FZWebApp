/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function shw(jobID
    ,runID
    ,hvsDt
    ,rmk
    ,vhRmk
    ,crtSrc
    ){

    var s = '\nRunID = ' + runID
            + '\nSource = ' + crtSrc
            + '\nHarvest Date = ' + hvsDt
            + '\nJob Remark = ' + rmk
            + '\nVehicle Remark = ' + vhRmk
    ;
    alert(s);
}


