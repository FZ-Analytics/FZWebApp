<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.fz.ffbv3.service.order2.Order2Record"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.fz.util.FZUtil"%>
<%!
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        List<Order2Record> ors = new ArrayList<Order2Record>();
        request.setAttribute("OrderList", ors);
        
        // if no button pressed, will display empty page
        String submit = FZUtil.getHttpParam(request, "submit");
        if (!submit.equals("search")){
            return;
        }

        String hvsDate = FZUtil.getHttpParam(request, "hvsDt");
        String divID = FZUtil.getHttpParam(request, "divID");
        String runID = FZUtil.getHttpParam(request, "runID");
        
        String sortBy = FZUtil.getHttpParam(request, "sortBy");
        request.setAttribute("SortBy", sortBy);

        String where = "";
        if (runID.length() > 0 ){
            where = " j.runID = '" + runID + "'"
                    + " and j.reOrderToJobID is null"
                    ;
        }
        else {
            where = 
                "\n j.hvsDt = '" + hvsDate + "'"
                + "\n   and j.divID like '%" + divID + "%'"
                    + " and j.reOrderToJobID is null"
                ;
        }

        String orderBy = ""
                + "\n       j.PlanTruckID"
                + "\n       , j.jobSeq"
                ;
        if (sortBy.equals("div")){
            orderBy = ""
                + "\n       j.divID"
                + "\n       , j.readyTime"
                    ;
        }
        
        String sql = "select distinct "
                + "\n date_format(j.hvsDt,'%Y-%m-%d')"
                + "\n, v.vehicleName"
                + "\n, j.jobSeq"
                + "\n, j.divID"
                + "\n, j.betweenBlock1"
                + "\n, j.betweenBlock2"
                + "\n, j.DoneStatus"
                + "\n, j.readyTime"
                + "\n, j.estmKg"
                + "\n, date_format(t.planStart,'%H:%i')"
                + "\n, date_format(t2.planEnd,'%H:%i')"
                + "\n, date_format(t.actualStart,'%H:%i')"
                + "\n, date_format(t2.actualEnd,'%H:%i')"
                + "\n, j.jobID"
                + "\n, j.runID"
                + "\n, j.remark"
                + "\n, v.vehicleID"
                + "\n, v.remark vehicleRemark"
                + "\n, t.reasonState"
                + "\n, t2.reasonState"
                + "\n, j.createSource"
                + "\n from fbJob j"
                + "\n   left outer join fbTask2 t"
                + "\n       on j.jobId = t.jobID"
                + "\n       and t.taskSeq = 1"
                + "\n   left outer join fbTask2 t2"
                + "\n       on j.jobId = t2.jobID"
                + "\n       and t2.taskSeq = 2"
                + "\n   left outer join fbVehicle v"
                + "\n       on j.ActualTruckID = v.vehicleID"
                + "\n where " + where
                + "\n   order by "
                + "\n " + orderBy
                + "\n       , case "
                + "\n           when j.doneStatus='PLAN' then '' "
                + "\n           else j.doneStatus "
                + "\n         end"
                ;
        
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
            //ps.setTimestamp(1, FZUtil.toSQLTimeStamp(hvsDate, "yyyy-MM-dd"));
            
            try (ResultSet rs = ps.executeQuery()){
                
                while (rs.next()){
                    
                    Order2Record or = new Order2Record();
                    
                    int i = 1;
                    or.hvsDate = FZUtil.getRsString(rs, i++, "");
                    or.vehicleName = FZUtil.getRsString(rs, i++, "");
                    or.jobSeq = FZUtil.getRsString(rs, i++, "");
                    or.divID = FZUtil.getRsString(rs, i++, "");
                    or.block1 = FZUtil.getRsString(rs, i++, "");
                    or.block2 = FZUtil.getRsString(rs, i++, "");
                    or.jobStatus = FZUtil.getRsString(rs, i++, "");
                    or.readyTime = FZUtil.getRsString(rs, i++, "");
                    or.size = (int) FZUtil.getRsDouble(rs, i++, 0);
                    or.planStart = FZUtil.getRsString(rs, i++, "");
                    or.planEnd = FZUtil.getRsString(rs, i++, "");
                    or.actualStart = FZUtil.getRsString(rs, i++, "");
                    or.actualEnd = FZUtil.getRsString(rs, i++, "");
                    or.jobID = FZUtil.getRsString(rs, i++, "");
                    or.runID = FZUtil.getRsString(rs, i++, "");
                    or.remark = FZUtil.getRsString(rs, i++, "");
                    or.vehicleID = FZUtil.getRsString(rs, i++, "");
                    or.vehicleRemark = FZUtil.getRsString(rs, i++, "");
                    or.Task1ReasonState = FZUtil.getRsString(rs, i++, "");
                    or.Task2ReasonState = FZUtil.getRsString(rs, i++, "");
                    or.createSource = FZUtil.getRsString(rs, i++, "");
                    
//                    or.from1 = FZUtil.getRsString(rs, i++, "");
//                    or.to1 = FZUtil.getRsString(rs, i++, "");
//                    or.taskDoneStatus = FZUtil.getRsString(rs, i++, "");
//                    or.vehicleRemark = FZUtil.getRsString(rs, i++, "");
//                    or.taskSeq = FZUtil.getRsString(rs, i++, "");
                    
                    ors.add(or);
                }
            }
        }
    }
%>
<%run(request, response, pageContext);%>