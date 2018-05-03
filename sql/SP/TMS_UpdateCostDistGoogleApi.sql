USE [BOSNET1]
GO
/****** Object:  StoredProcedure [dbo].[TMS_UpdateCostDistGoogleApi]    Script Date: 03/05/2018 12:36:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[TMS_UpdateCostDistGoogleApi] --q
@BrId varchar(10), @top int
AS
  -- SET NOCOUNT ON added to prevent extra result sets from
  -- interfering with SELECT statements.
  SET NOCOUNT ON;

DECLARE @tcust AS TABLE
	(
		cust VARCHAR(100) NOT NULL,
		long VARCHAR(100) NOT NULL,
		lat VARCHAR(100) NOT NULL
	);

DECLARE @zcust AS TABLE
	(
		cust1 VARCHAR(100) NOT NULL,
		long1 VARCHAR(100) NOT NULL,
		lat1 VARCHAR(100) NOT NULL,
		cust2 VARCHAR(100) NOT NULL,
		long2 VARCHAR(100) NOT NULL,
		lat2 VARCHAR(100) NOT NULL
	);

DECLARE @ycust AS TABLE
	(
		cust1 VARCHAR(100) NOT NULL,
		long1 VARCHAR(100) NOT NULL,
		lat1 VARCHAR(100) NOT NULL,
		cust2 VARCHAR(100) NOT NULL,
		long2 VARCHAR(100) NOT NULL,
		lat2 VARCHAR(100) NOT NULL
	);

--ambil TMS_ShipmentPlan + TMS_CustLongLat
 INSERT
	INTO
		@tCust SELECT
			DISTINCT sp.Customer_ID,
			cl.Long,
			cl.Lat
		FROM
			BOSNET1.dbo.TMS_ShipmentPlan sp
		INNER JOIN BOSNET1.dbo.TMS_CustLongLat cl ON
			sp.Customer_ID = cl.CustId
		WHERE
			sp.Plant = @BrId
			AND cl.Long IS NOT NULL
			AND cl.Lat IS NOT NULL
			AND cl.Long NOT IN(
				'n/a',
				'0'
			)
			AND cl.Lat NOT IN(
				'n/a',
				',',
				'0'
			)
			AND cl.Long NOT LIKE('%,%')
			AND cl.Lat NOT LIKE('%,%');

--cek hasil combinasi (TMS_ShipmentPlan + TMS_CustLongLat) X (TMS_ShipmentPlan + TMS_CustLongLat) dengan TMS_CostDist
 --select * from BOSNET1.dbo.TMS_CustCombination aq left outer join BOSNET1.dbo.TMS_CostDist aw on aq.cust1 = aw.from1 and aq.cust2 = aw.to1
 --where aw.from1 is null and aw.to1 is null
 --select * from BOSNET1.dbo.TMS_CostDist where from1 = '5810000365' and to1 = '5810002824'
 DELETE
FROM
	BOSNET1.dbo.TMS_CustCombination;
	--select combinaasi yang belum ada pada TMS_CostDist
INSERT
	INTO
		@zcust --TMS_CustCombination 
 SELECT
			top 1000 cx.*
		FROM
			(
				SELECT
					DISTINCT c1.cust AS cust1,
					c1.long AS long1,
					c1.lat AS lat1,
					c2.cust AS cust2,
					c2.long AS long2,
					c2.lat AS lat2
				FROM
					@tCust c1,
					@tCust c2
				WHERE
					c1.cust <> c2.cust
			) cx
		LEFT OUTER JOIN(
				SELECT
					DISTINCT lon1,
					lat1,
					SUBSTRING( from1, 1, 10 ) AS from1,
					lon2,
					lat2,
					SUBSTRING( to1, 1, 10 ) AS to1
				FROM
					BOSNET1.dbo.TMS_CostDist
				WHERE
					branch = @BrId
			) cy ON
			cx.cust1 = cy.from1
			AND cx.cust2 = cy.to1
		WHERE
			cy.from1 IS NULL
			AND cy.to1 IS NULL;
			--ambil yang tidak kembar
SELECT
			top 100 cust1,
			long1,
			lat1,
			cust2,
			long2,
			lat2
		FROM
			(
				SELECT
					cust1,
					long1,
					lat1,
					cust2,
					long2,
					lat2,
					CONCAT(
						cust1,
						long1,
						lat1,
						cust2,
						long2,
						lat2
					) AS cs
				FROM
					@zcust
			) cx
		INNER JOIN(
				SELECT
					cs
				FROM
					(
						SELECT
							CONCAT(
								cust1,
								long1,
								lat1,
								cust2,
								long2,
								lat2
							) AS cs
						FROM
							@zcust
					) aw
				GROUP BY
					cs
				HAVING
					COUNT( cs )= 1
			) cy ON
			cx.cs = cy.cs