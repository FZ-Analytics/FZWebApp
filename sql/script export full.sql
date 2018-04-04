BEGIN
	DECLARE @Insert AS TABLE 
		(sc Varchar(2000) NOT NULL);

	DECLARE @Temp AS TABLE 
		(Customer_ID Varchar(50) NOT NULL,
		DO_Number Varchar(50) NOT NULL);

	DECLARE @BrId Varchar(5) = 'D312';

	insert into @Temp
	SELECT
	sp.Customer_ID,
	sp.DO_Number
	FROM
		bosnet1.dbo.TMS_ShipmentPlan sp
	LEFT OUTER JOIN(
			SELECT
				a.*
			FROM
				(
					SELECT
						ROW_NUMBER() OVER(
							PARTITION BY Customer_ID
						ORDER BY
							Customer_ID
						) AS noId,
						*
					FROM
						bosnet1.dbo.customer
					WHERE
						(
							Customer_Order_Block IS NULL
							OR Customer_Order_Block = ''
						)
						AND(
							Customer_Order_Block_all IS NULL
							OR Customer_Order_Block_all = ''
						)
				) a
			WHERE
				a.noid = 1
		) cs ON
		sp.customer_id = cs.customer_id
	LEFT JOIN(
			SELECT
				*
			FROM
				(
					SELECT
						ROW_NUMBER() OVER(
							PARTITION BY custid
						ORDER BY
							custid
						) AS noId,
						*
					FROM
						bosnet1.dbo.TMS_CustLongLat
				) a
			WHERE
				a.noid = 1
		) cl ON
		sp.customer_id = cl.custID
	LEFT OUTER JOIN bosnet1.dbo.TMS_CustAtr ca ON
		sp.customer_id = ca.customer_id
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dd ON
		dd.param = 'DeliveryDeadLine'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params ds ON
		ds.param = 'DayWinStart'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params de ON
		de.param = 'DayWinEnd'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params df ON
		df.param = 'DefaultCustPriority'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dr ON
		dr.param = 'DefaultCustServiceTime'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dg ON
		dg.param = 'DefaultCustStartTime'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dt ON
		dt.param = 'DefaultCustEndTime'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dh ON
		dh.param = 'DefaultCustVehicleTypes'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dy ON
		dy.param = 'MTDefault'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dn ON
		dn.param = 'BufferEndDefault'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params dj ON
		dj.param = 'SatDelivDefault'
	LEFT OUTER JOIN bosnet1.dbo.TMS_Params du ON
		du.param = 'ChannelNullDefault'
	WHERE
		sp.plant = @BrId
		AND sp.already_shipment = 'N'
		AND sp.notused_flag IS NULL
		AND sp.incoterm = 'FCO'
		AND(
			sp.Order_Type = 'ZDCO'
			OR sp.Order_Type = 'ZDTO'
		)
		AND sp.create_date >= DATEADD(
			DAY,
			- 7,
			GETDATE()
		)
	ORDER BY
		sp.Customer_ID ASC;

	insert into @Insert
	select 'delete from BOSNET1.dbo.customer;';
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_CustAtr;';
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_CustLongLat;';
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_ShipmentPlan;';
	insert into @Insert
	select 'delete from BOSNET1.dbo.TMS_VehicleAtr;';

	insert into @Insert
	select concat(
			'insert into BOSNET1.dbo.customer values(''',
			Customer_ID,'''',',''',Account_Group,'''',',''',
			Distribution_Channel,'''',',''',Division,'''',',''',
			Name1,'''',',''',Name2,'''',',''',
			Street,'''',',''',Distric,'''',',''',
			Postal_Code,'''',',''',City,'''',',''',
			Transportation_Zone,'''',',''',Longitude,'''',',''',
			Latitude,'''',',''',Industry_Code,'''',',''',
			Contact_Person,'''',',''',Contact_Person_Number,'''',',''',
			Terms_of_Payment,'''',',''',Sales_Distric,'''',',''',
			Sales_Office,'''',',''',Customer_Group,'''',',''',
			Delivering_Plant,'''',',''',Incoterms,'''',',''',
			Customer_Group1,'''',',''',Customer_Group2,'''',',''',
			Customer_Group3,'''',',''',Customer_Group4,'''',',''',
			Sold_To,'''',',''',Ship_To,'''',',''',
			Payer,'''',',''',Bill_To,'''',',''',
			Salesman,'''',',''',Tax_Indicator,'''',',',
			Credit_Limit,',',
			(select case when Customer_Order_Block_all is null or Customer_Order_Block_all = '' then 'null' else Customer_Order_Block_all end),',',
			(select case when Customer_Order_Block is null or Customer_Order_Block = '' then 'null' else Customer_Order_Block end),',',
			(select case when Deletion_Flag_all is null or Deletion_Flag_all = '' then 'null' else Deletion_Flag_all end),',',
			(select case when Deletion_Flag is null or Deletion_Flag = '' then 'null' else Deletion_Flag end),',''',
			(select FORMAT(Create_Date,'yyyy-MM-dd')),'''',',''',
			(select case when Create_Time is null then 'null' else FORMAT(CONVERT(datetime, Create_Time,108),'HH:mm:ss') end),'''',',',Flag,',',
			(select case when NOO_Create_Date is null then 'null' else '''' + FORMAT(NOO_Create_Date,'yyyy-MM-dd') + '''' end),',',
			(select case when NOO_Approved_Date is null then 'null' else '''' + FORMAT(NOO_Approved_Date,'yyyy-MM-dd') + '''' end),',',
			(select case when NOO_Update_Date is null then 'null' else '''' + FORMAT(NOO_Update_Date,'yyyy-MM-dd') + '''' end),',''',
			Rt_Rw,'''',',''',
			Desa_Kelurahan,'''',',''',Kecamatan,'''',',''',
			Kodya_Kabupaten,'''',',''',Province_Code,'''',',',
			Customer_Priority,',''',Tax_Class,''');') as sc 
	from (SELECT
			a.*
		FROM
			(
				SELECT
					ROW_NUMBER() OVER(
						PARTITION BY Customer_ID
					ORDER BY
						Customer_ID
					) AS noId,
					*
				FROM
					bosnet1.dbo.customer
				WHERE
					(
						Customer_Order_Block IS NULL
						OR Customer_Order_Block = ''
					)
					AND(
						Customer_Order_Block_all IS NULL
						OR Customer_Order_Block_all = ''
					)
			) a
		WHERE
			a.noid = 1) sc
	where Customer_ID in(
		select Customer_ID from @Temp);

	insert into @Insert
	SELECT
		concat('insert into BOSNET1.dbo.TMS_CustAtr values(''',
			customer_id,'''',',',(select case when service_time is null then 0 else service_time end),
			',''',deliv_start,'''',',''',
			deliv_end,'''',',''',vehicle_type_list,
			'''',',''',DayWinStart,'''',
			',''',DayWinEnd,'''',
			',''',DeliveryDeadline,
			''');'
		)	as sc 
	FROM
		BOSNET1.dbo.TMS_CustAtr
	where customer_id in (select Customer_ID from @Temp);
	
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
	where CustId in (select Customer_ID from @Temp);

	insert into @Insert
	SELECT
		concat('insert into BOSNET1.dbo.TMS_ShipmentPlan values(''',
			DO_Number,'''',',''',Customer_ID,'''',',',Total_KG,',',
			Total_Amount,',''',(select FORMAT(Request_Delivery_Date,'yyyy-MM-dd')),
			''',',(select case when Route is null then 'null' else '''' + Route + '''' end),
			',',(select case when Route_Description is null then 'null' else '''' + Route_Description + '''' end),
			',''',Item_Number,'''',',''',Plant,'''',',''',
			Product_ID,'''',',''',Product_Description,'''',',',
			Total_KG_Item,',',Total_Cubication,',',
			Gross_Amount,',',Net_Amount,',''',Already_Shipment,'''',',''',
			ItemCategory,'''',',',DOQty,',''',
			DOQtyUOM,'''',',''',Batch,'''',',''',
			HighLevelBatch,'''',',',Shift,',',
			(select case when Expired_Date_Batch is null or LEN(Expired_Date_Batch) < 7 then 'null' else Expired_Date_Batch end),',''',
			(select FORMAT(DOCreationDate,'yyyy-MM-dd')),''',''',
			(select FORMAT(CONVERT(datetime, DOCreationTime,108),'HH:mm:ss')),''',',
			(case when DOUpdatedDate is null then 'null' else '''' + (select FORMAT(DOUpdatedDate,'yyyy-MM-dd')) + '''' end),',''',
			(select FORMAT(CONVERT(datetime, DOUpdatedTime,108),'HH:mm:ss')),''',''',
			(select FORMAT(Create_Date,'yyyy-MM-dd')),''',''',
			(select FORMAT(CONVERT(datetime, Create_Time,108),'HH:mm:ss')),''',',
			Flag,',',(select case when NotUsed_Flag is null then 'null' else '''' + NotUsed_Flag + '''' end),',''',Order_Type,'''',',''',
			Incoterm,''');'
		)	as sc 
	FROM
		BOSNET1.dbo.TMS_ShipmentPlan
	where DO_Number in (select DO_Number from @Temp);
	
	insert into @Insert
	  SELECT
		concat('insert into BOSNET1.dbo.TMS_VehicleAtr values(''',
			vehicle_code,'''',',''',branch,'''',',''',
			startLon,'''',',''',startLat,'''',',''',
			endLon,'''',',''',endLat,'''',',''',
			startTime,'''',',''',endTime,'''',',''',
			source1,'''',',''',vehicle_type,'''',',''',
			weight,'''',',''',volume,'''',',',included,
			',',costPerM,',',fixedCost,',''',Channel,''',',
			(select case when IdDriver is null then 'NULL' else '''' + IdDriver + '''' end),',',
			(select case when NamaDriver is null then 'NULL' else '''' + NamaDriver + '''' end),',',
			(select case when DriverDates is null then 'NULL' else '''' + DriverDates + '''' end),
			',',agent_priority,',',max_cust,');'
		)	as sc 
	FROM
		BOSNET1.dbo.TMS_VehicleAtr
	where branch = @BrId;

	select * from @Insert;
END