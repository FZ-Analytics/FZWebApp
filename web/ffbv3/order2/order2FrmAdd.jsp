<%@page import="java.util.Date"%>
<%@page import="com.fz.ffbv3.service.order2.Order2Record"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="com.fz.ffbv3.service.usermgt.Auth"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%!
    // TODO: make MVP. This is only for quick dev
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {

        // get input
        Date now = new java.util.Date();
        String hvsDate = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(
                        new java.util.Date()));
        
        String divID = FZUtil.getHttpParam(request, "divID").trim().toUpperCase();
        
        String block1 = FZUtil.getHttpParam(request, "block1").trim().toUpperCase();
        
        String block2 = FZUtil.getHttpParam(request, "block2").trim().toUpperCase();
        
        String readyTime = FZUtil.getHttpParam(request, "readyTime").trim();
        validateClock(readyTime);
        
        String userID = (String) request.getSession().getAttribute("userID");
        if (userID == null) throw new Exception("UserID is null in session");
        if (userID.trim().length() == 0) throw new Exception("UserID is empty in session");
        
        String estmKg = FZUtil.getHttpParam(request, "estmKg");

        String estmFfb = FZUtil.getHttpParam(request, "estmFfb");

        String dirLoc = FZUtil.getHttpParam(request, "dirLoc");

        String isLastOrder = FZUtil.getHttpParam(request, "isLastOrder")
                .trim().toLowerCase();

        String millID = FZUtil.getHttpParam(request, "millID");
        String restanKg = FZUtil.getHttpParam(request,"restanKg").trim();

//        if (!(isLastOrder.equals("yes") ||  isLastOrder.equals("no"))) 
//            throw new Exception("Is last order should be 'yes' or 'no'");

        String isLast2Order = FZUtil.getHttpParam(request, "isLast2Order");

        String lastOrders = (FZUtil.getHttpParam(request,"lastOrders").trim());
        if (lastOrders.equals("2")) isLast2Order = "yes";
        if (lastOrders.equals("1")) isLastOrder = "yes";
        int nLstOrd = Integer.parseInt(lastOrders);
        if (now.getHours()>=15) 
            if (nLstOrd > 10 || nLstOrd < 0) throw new Exception("Please input Bin Remaining");
        if (nLstOrd == 0 && Integer.parseInt(restanKg) ==0 )
            throw new Exception("Please input Restan Size");

        String remark = FZUtil.getHttpParam(request, "remark");

        String lastOdrCnt = "" ;
        if (remark.trim().length() == 0) 
            throw new Exception("Bin Position / Remark cannot empty");
        
        Auth.check(pc, "Div_" + divID + ";Order_Edit", true);
        
        String block2Criteria = "";
        String curTimeStamp = "";
//        if (block2.length() > 0){
//            block2Criteria = " and betweenBlock2 = '" + block2 + "'";
//        }
        
        try (Connection con = (new Db()).getConnection("jdbc/fz")){

        // begin trans
        con.setAutoCommit(false);
            // check similar job
            String sql = 
                    "select jobID, current_time() curTimeStamp from fbJob "
                    + " where "
                    + " hvsDt = '" + hvsDate + "'"
                    + " and divID = '" + divID + "'"
                    + " and betweenBlock1 = '" + block1 + "'"
                    + block2Criteria
                    + " and dirLoc = '" + dirLoc + "'"
                    + " and doneStatus <> 'PLAN'"
                    + " and reorderToJobID is null"
                    ;
            try (PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()){
                
                if (rs.next()){
                    
                    String jobID = FZUtil.getRsString(rs, 1, "");
                    curTimeStamp = FZUtil.getRsString(rs, 2, "");
//                    request.setAttribute("duplicateJobID", jobID);
//                    request.getRequestDispatcher("../order2/order2Frm.jsp")
//                        .forward(request, response);
//                    return;
                    throw new Exception("Duplicate order, with jobID " + jobID);
                }
            }
            
                // get sequence number of job for a division, and runID
                StringBuffer retVal = new StringBuffer();
                getSeqAndRunID(hvsDate, divID, con, retVal);
                String[] seqAndRunID = retVal.toString().split(";");
                //int seq = Integer.parseInt(seqAndRunID[0]);
                String runID = seqAndRunID[0];
                int prevLastOdrCnt = 0;

            if (isLast2Order.equals("yes")){
                    // select lastOdr from schdRun whererunid
		sql = "select lastOdrCnt from fbSchedRun where runID='" + runID + "'";
                try (
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {
//prevLastOdCnt= rs...                
				if (rs.next()) prevLastOdrCnt = rs.getInt("lastOdrCnt");
		}
            }


            // insert
            sql = "insert into fbjob("
                    + "doneStatus"
                    + ", divID"
                    + ", betweenBlock1"
                    + ", betweenBlock2"
                    + ", readyTime"
                    + ", EstmKg"
                    + ", createDt"
                    + ", hvsDt"
                    + ", requesterID"
                    + ", createSource"
                    + ", runID"
                    + ", dirLoc"
                    + ", remark"
                    + ", isLastOrder"
                    + ", isLast2Order"
                    + ", EstmFfb"
                    + ", PrevLastOdrCnt"
                    + ", LastOrders"
                    + ", millID"
                    + ", restanKg"
                    + ")"
                    + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                    ;
            try (PreparedStatement ps = con.prepareStatement(sql)){

                // validate blocks
                validateDivBlock(divID, block1, con);
//                if (block2.trim().length() > 0)
//                    validateDivBlock(divID, block2, con);

                String todayStr = FZUtil.toDateString(
                        new java.util.Date(), "yyyy-MM-dd");

                int i=1;
                ps.setString(i++, "NEW");
                ps.setString(i++, divID);
                ps.setString(i++, block1);
                ps.setString(i++, block2);
                ps.setString(i++, readyTime);
                ps.setString(i++, estmKg);
                ps.setTimestamp(i++, FZUtil.getCurSQLTimeStamp());
                ps.setTimestamp(i++, FZUtil.toSQLTimeStamp(todayStr,"yyyy-MM-dd"));
                ps.setString(i++, userID);
                ps.setString(i++, "ORDR");
    //            ps.setInt(i++, seq);
                ps.setString(i++, runID);
                ps.setString(i++, dirLoc);
                ps.setString(i++, remark);
                ps.setString(i++, isLastOrder);
                ps.setString(i++, isLast2Order);
                ps.setString(i++, estmFfb);
                ps.setInt(i++, prevLastOdrCnt);
                ps.setString(i++, lastOrders);
                ps.setString(i++, millID);
                ps.setString(i++, restanKg);

                ps.executeUpdate();

//                if (isLast2Order.equals("yes"))  incrementLastOdrCnt(con, runID);
                }
                //diganti if > jam 3 & remaining order-nya diisi
                if ((curTimeStamp.compareTo("15:00")>0) && !runID.equals(null) && !runID.isEmpty()) {
                    sql = "select * from fbremainBin" 
                        + " where runID='" + runID 
                        + "' and divID='" + divID + "'";
                    
                    try {
                        PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery() ;
                        if (lastOrders.equals("0")) lastOrders = "9999";
                        if (rs.next()) {
                            String id = rs.getString("id");
                            sql = "update fbremainBin set remainingBin=" + lastOrders
                                + " where id=" +id;
                           //       + " where runID='" + runID + "' and divID='" + divID + "'";
                        } else {
                             sql = "insert into fbremainBin (runID, divID, remainingBin) "
                                 + "values ('" + runID + "', '" + divID + "'," + lastOrders +")";
                        }

                        ps.executeUpdate(sql);
                    } catch (Exception ex) { 

                    }
                }
        con.setAutoCommit(true);
        }
        request.getRequestDispatcher("../appGlobal/success.jsp")
                .forward(request, response);
            
    }

    private void incrementLastOdrCnt(Connection con, String runID) 
            throws Exception {


        String sql = "update fbSchedRun set lastOdrCnt = lastOdrCnt + 2"
            + " where runID = '" + runID + "'";
        try (PreparedStatement ps = con.prepareStatement(sql)){
            ps.executeUpdate();
        }
    }

    private void validateDivBlock(String divID, String block1
            , Connection con) throws Exception {
        
        String sql = "select blockID "
                + " from fbBlock b"
                + " where "
                + "     blockID = '" + block1 + "'"
                + "     and divID = '" + divID + "'"
                ;
        
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            
            if (!rs.next()){
                throw new Exception("Invalid block " 
                        + block1 + " for div " + divID);
            }
            
        }
    }

    private void getSeqAndRunID(String hvsDate, String divID, Connection con
            , StringBuffer retVal) 
            throws Exception {
        String sql = "select runID "
                + " from fbJob"
                + " where hvsDt = '" + hvsDate + "'"
                + "     and divID = '" + divID + "'"
                + "     and reorderToJobID is null"
                + " order by JobID desc"
                ;
//        int seq = 0;
        String runID = "";
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            if (rs.next()) {
//            seq = rs.getInt(1);
//            // if no order yet
//            if (seq == 0){
//                seq = 2;
//            }
//            else {
//                seq += 1;
//            }
            runID = rs.getString(1);
            }
        }
//        retVal.append(seq).append(";").append(runID);
        retVal.append(runID);
    }

    private int validateClock(String readyTime) throws Exception {
        int readyTimeInt = FZUtil.clockToMin(readyTime);
        if ((readyTimeInt < 0) || (readyTimeInt > 24 * 60)){
            throw new Exception("Invalid ready time");
        }
        return readyTimeInt;
    }
%>
<%
    try {
        run(request, response, pageContext);
    } 
    catch (Exception e){
        // forward to error page to display error message
        String msg = FZUtil.toStackTraceText(e);
        System.out.println(msg);
        request.setAttribute("errMsg", msg);
        request
                .getRequestDispatcher("../appGlobal/error.jsp")
                .forward(request, response);
    }
    
%>
