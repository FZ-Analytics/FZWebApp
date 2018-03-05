BEGIN
	DECLARE @RunId Varchar(50) = '20180222_142519695';
	DECLARE @Insert AS TABLE 
		(sc Varchar(2000) NOT NULL);
	DECLARE @CustT AS TABLE 
		(CID Varchar(2000) NOT NULL);

	insert into @CustT
	select Customer_ID as CID from BOSNET1.dbo.TMS_PreRouteJob
		WHERE runid =  @RunId;
		
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_PreRouteJob;';
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_PreRouteVehicle;';
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_CustLongLat;';
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_Progress;';
	
	insert into @Insert
	SELECT
		concat(
			'insert into BOSNET1.dbo.TMS_PreRouteJob values(''',
			RunId,'''',',''',Customer_ID,'''',',''',
			DO_Number,'''',',''',Long,'''',',''',Lat,
			'''',',''',Customer_priority,'''',',''',
			Service_time,'''',',''',deliv_start,
			'''',',''',deliv_end,'''',',''',
			vehicle_type_list,'''',',''',total_kg,
			'''',',''',total_cubication,'''',
			',''',DeliveryDeadline,'''',',''',
			DayWinStart,'''',',''',DayWinEnd,
			'''',',''',UpdatevDate,'''',',''',
			CreateDate,'''',',''',isActive,
			'''',',''',Is_Exclude,'''',',''',
			Is_Edit,'''',',''',Product_Description,
			'''',',''',Gross_Amount,'''',',''',
			DOQty,'''',',''',DOQtyUOM,
			'''',',''',Name1,'''',',''',Street,
			'''',',''',Distribution_Channel,
			'''',',''',Customer_Order_Block_all,
			'''',',''',Customer_Order_Block,
			'''',',''',Request_Delivery_Date,
			'''',',''',MarketId,'''',',''',
			Desa_Kelurahan,'''',',''',
			Kecamatan,'''',',''',Kodya_Kabupaten,
			'''',',''',Batch,'''',',''',Ket_DO,''');'
		) as sc 
	FROM
		BOSNET1.dbo.TMS_PreRouteJob
	WHERE
		runid = @RunId;

	insert into @Insert
	SELECT concat('insert into BOSNET1.dbo.TMS_PreRouteVehicle values(''',RunId,''''
      ,',''',vehicle_code,'''',',''',weight,''''
      ,',''',volume,'''',',''',vehicle_type,''''
      ,',''',branch,'''',',''',startLon,''''
      ,',''',startLat,'''',',''',endLon,''''
      ,',''',endLat,'''',',''',startTime,''''
      ,',''',endTime,'''',',''',source1,''''
      ,',''',UpdatevDate,'''',',''',CreateDate,''''
      ,',''',isActive,'''',',',fixedCost
      ,',',costPerM,',',costPerServiceMin
      ,',',costPerTravelMin,',''',IdDriver,''''
      ,',''',NamaDriver,''');') as sc 
	FROM BOSNET1.dbo.TMS_PreRouteVehicle where runid = @RunId;

	insert into @Insert
	SELECT
		concat('insert into BOSNET1.dbo.TMS_CustLongLat values(''',
			BranchId,'''',',''',BranchName,
			'''',',''',CustId,'''',',''',
			CustName,'''',',''',Address,
			'''',',''',MarketId,'''',
			',''',SubDistrict,'''',
			',''',District,'''',
			',''',City,'''',',''',
			Province,'''',',''',
			PostalCode,'''',',''',
			Long,'''',',''',Lat,
			'''',',''',Source,''');'
		)	as sc 
	FROM
		BOSNET1.dbo.TMS_CustLongLat
	where CustId in (select * from @CustT);
	
	insert into @Insert
	SELECT concat('insert into BOSNET1.dbo.TMS_Progress values(''',runID,''''
      ,',''',status,'''',',',(select case when msg is null then 'NULL' else '''' + msg + ''''end)
      ,',',(select case when pct is null then 0 else pct end)
	  ,',',(select case when mustFinish is null then 0 else mustFinish end),','''
	  ,branch,'''',',''',shift,'''',','''
	  ,tripcalc,'''',',''',(select FORMAT(lastUpd,'yyyy-MM-dd hh:mm:ss')),''''
      ,',''',(select FORMAT(created,'yyyy-MM-dd hh:mm:ss')),'''',',',
	  (select case when maxIter is null then 0 else maxIter end)
      ,',''',DelivDate,'''',',''',Re_RunId,''''
	  ,',''',OriRunId,'''',',''',Channel,''');') as sc 
	FROM BOSNET1.dbo.TMS_Progress where runId = @RunId;

	select * from @Insert;
--select * from bosnet1.dbo.TMS_PreRouteJob where RunId = @RunId;
end