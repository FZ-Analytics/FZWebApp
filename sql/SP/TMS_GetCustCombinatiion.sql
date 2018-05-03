USE [BOSNET1]
GO
/****** Object:  StoredProcedure [dbo].[TMS_GetCustCombinatiion]    Script Date: 03/05/2018 17:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[TMS_GetCustCombinatiion] --exec [dbo].[TMS_GetCustCombinatiion] 'D312', '20180503_122120895'
@BrId varchar(10), @RunId varchar(50)
AS
  -- SET NOCOUNT ON added to prevent extra result sets from
  -- interfering with SELECT statements.
  SET NOCOUNT ON;
  DECLARE @tcust AS TABLE (
    cust varchar(100) NOT NULL,
    long varchar(100) NOT NULL,
    lat varchar(100) NOT NULL
  );

  INSERT INTO @tcust
    SELECT
    DISTINCT
      concat('DEPO_', a.branch) AS cust,
      a.startLon AS lon,
      a.startLat AS lat
    FROM bosnet1.dbo.TMS_PreRouteVehicle a
    WHERE a.RunId = @RunId
    AND a.branch = @BrId
    AND a.isActive = '1'
    AND a.IdDriver IS NOT NULL
    AND a.NamaDriver IS NOT NULL
  INSERT INTO @tcust
    SELECT
    DISTINCT
      Customer_ID AS cust,
      Long AS lon,
      Lat AS lat
    FROM BOSNET1.dbo.TMS_PreRouteJob
    WHERE RunId = @RunId
	  AND Is_Exclude = 'inc'
	  AND Is_Edit = 'edit';

  DECLARE @ycust AS TABLE (
    cust1 varchar(100) NOT NULL,
    long1 varchar(100) NOT NULL,
    lat1 varchar(100) NOT NULL,
    cust2 varchar(100) NOT NULL,
    long2 varchar(100) NOT NULL,
    lat2 varchar(100) NOT NULL
  );

  DECLARE @zcust AS TABLE (
    cust1 varchar(100) NOT NULL,
    long1 varchar(100) NOT NULL,
    lat1 varchar(100) NOT NULL,
    cust2 varchar(100) NOT NULL,
    long2 varchar(100) NOT NULL,
    lat2 varchar(100) NOT NULL
  );

  --get cust combination from preroutejob
  INSERT INTO @ycust
    SELECT
    DISTINCT
      c1.cust AS cust1,
      c1.long AS long1,
      c1.lat AS lat1,
      c2.cust AS cust2,
      c2.long AS long2,
      c2.lat AS lat2
    FROM @tCust c1,
         @tCust c2
    WHERE c1.cust <> c2.cust;

  --DELETE FROM BOSNET1.dbo.TMS_CostDistCombination;
  --compare cust combination with TMS_CostDist
  --INSERT INTO @zcust
    SELECT
      cx.*
    FROM @ycust cx
    LEFT OUTER JOIN (SELECT
    DISTINCT
      lon1,
      lat1,
      SUBSTRING(from1, 1, 10) AS from1,
      lon2,
      lat2,
      SUBSTRING(to1, 1, 10) AS to1
    FROM BOSNET1.dbo.TMS_CostDist
    WHERE branch = @BrId) cy
      ON cx.cust1 = cy.from1
      AND cx.cust2 = cy.to1
      AND cx.long1 = cy.lon1
      AND cx.lat1 = cy.lat1
      AND cx.long2 = cy.lon2
      AND cx.lat2 = cy.lat2
    WHERE cy.from1 IS NULL
    AND cy.to1 IS NULL;
  

  --SELECT
  --  cc.*
  --FROM @zcust cc
  --LEFT OUTER JOIN BOSNET1.dbo.TMS_CostDist cd
  --  ON cc.cust1 = SUBSTRING(cd.from1, 1, 10)
  --  AND cc.cust2 = SUBSTRING(cd.to1, 1, 10)
  --WHERE cd.from1 IS NULL
  --AND cd.to1 IS NULL
  --ORDER BY cc.cust1 ASC