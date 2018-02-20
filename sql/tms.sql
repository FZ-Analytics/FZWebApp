--update BOSNET1.dbo.TMS_ShipmentPlan set Create_Date = CAST(GETDATE() AS DATE), Request_Delivery_Date = CAST(GETDATE() AS DATE)

IF OBJECT_ID('bosnet1.dbo.Customer', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.Customer;
CREATE TABLE BOSNET1.dbo.Customer (
	Customer_ID varchar(10) NOT NULL,
	Account_Group varchar(4) NOT NULL,
	Distribution_Channel varchar(2) NOT NULL,
	Division varchar(2) NOT NULL,
	Name1 varchar(40) NOT NULL DEFAULT ('n/a'),
	Name2 varchar(40) NOT NULL DEFAULT ('n/a'),
	Street varchar(40) NOT NULL DEFAULT ('n/a'),
	Distric varchar(35) NOT NULL DEFAULT ('n/a'),
	Postal_Code varchar(10) NOT NULL DEFAULT ('n/a'),
	City varchar(40) NOT NULL DEFAULT ('n/a'),
	Transportation_Zone varchar(10) NOT NULL DEFAULT ('n/a'),
	Longitude varchar(10) NOT NULL DEFAULT ('n/a'),
	Latitude varchar(25) NOT NULL DEFAULT ('n/a'),
	Industry_Code varchar(10) NOT NULL DEFAULT ('n/a'),
	Contact_Person varchar(35) NOT NULL DEFAULT ('n/a'),
	Contact_Person_Number varchar(16) NOT NULL DEFAULT ('n/a'),
	Terms_of_Payment varchar(4) NOT NULL DEFAULT ('n/a'),
	Sales_Distric varchar(6) NOT NULL DEFAULT ('n/a'),
	Sales_Office varchar(4) NOT NULL DEFAULT ('n/a'),
	Customer_Group varchar(8) NOT NULL DEFAULT ('na'),
	Delivering_Plant varchar(4) NOT NULL DEFAULT ('n/a'),
	Incoterms varchar(3) NOT NULL DEFAULT ('n/a'),
	Customer_Group1 varchar(7) NOT NULL DEFAULT ('n/a_cg1'),
	Customer_Group2 varchar(7) NOT NULL DEFAULT ('n/a_cg2'),
	Customer_Group3 varchar(7) NOT NULL DEFAULT ('n/a_cg3'),
	Customer_Group4 varchar(7) NOT NULL DEFAULT ('n/a_cg4'),
	Sold_To varchar(10) NOT NULL DEFAULT ('n/a'),
	Ship_To varchar(10) NOT NULL DEFAULT ('n/a'),
	Payer varchar(10) NOT NULL DEFAULT ('n/a'),
	Bill_To varchar(10) NOT NULL DEFAULT ('n/a'),
	Salesman varchar(10) NOT NULL DEFAULT ('n/a'),
	Tax_Indicator varchar(1) NOT NULL,
	Credit_Limit bigint NOT NULL DEFAULT ((0)),
	Customer_Order_Block_all varchar(2),
	Customer_Order_Block varchar(2),
	Deletion_Flag_all varchar(1),
	Deletion_Flag varchar(1),
	NOO_Create_Date date,
	NOO_Approved_Date date,
	NOO_Update_Date date,
	Rt_Rw varchar(40),
	Desa_Kelurahan varchar(40),
	Kecamatan varchar(40),
	Kodya_Kabupaten varchar(40),
	Province_Code char(3),
	Create_Date date,
	Create_Time time,
	Flag smallint,
	Customer_Priority int,
	Tax_Class varchar(1),
	CONSTRAINT PK_Customer PRIMARY KEY (Customer_ID,Account_Group,Distribution_Channel,Division)
) ;

IF OBJECT_ID('bosnet1.dbo.Driver', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.Driver; 
CREATE TABLE BOSNET1.dbo.Driver (
	Driver_ID varchar(10) NOT NULL,
	Driver_Name varchar(35) NOT NULL DEFAULT ('n/a'),
	Workplace varchar(4) NOT NULL DEFAULT ('n/a'),
	NIK varchar(20),
	Create_Date date,
	Create_Time time,
	Flag smallint,
	CONSTRAINT PK_Driver PRIMARY KEY (Driver_ID)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_CostDist', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_CostDist; 
CREATE TABLE BOSNET1.dbo.TMS_CostDist (
	lon1 varchar(100),
	lat1 varchar(100),
	lon2 varchar(100),
	lat2 varchar(100),
	dist decimal(38,3),
	dur decimal(38,3),
	branch varchar(10),
	from1 varchar(100),
	to1 varchar(100)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_CustAtr', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_CustAtr; 
CREATE TABLE BOSNET1.dbo.TMS_CustAtr (
	customer_id varchar(50),
	service_time int,
	deliv_start varchar(5),
	deliv_end varchar(5),
	vehicle_type_list varchar(255),
	DayWinStart varchar(5),
	DayWinEnd varchar(5),
	DeliveryDeadline varchar(5)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_CustLongLat', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_CustLongLat; 
CREATE TABLE BOSNET1.dbo.TMS_CustLongLat (
	BranchId varchar(4) NOT NULL,
	BranchName varchar(50),
	CustId varchar(10) NOT NULL,
	CustName varchar,
	Address varchar(MAX),
	MarketId varchar(20),
	SubDistrict varchar(254),
	District varchar(254),
	City varchar(254),
	Province varchar(254),
	PostalCode varchar(10),
	Long varchar(100),
	Lat varchar(100),
	[Source] varchar(18) NOT NULL
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_ForwadingAgent', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_ForwadingAgent; 
CREATE TABLE BOSNET1.dbo.TMS_ForwadingAgent (
	Service_agent_id varchar(50) NOT NULL,
	Driver_Name varchar(100) NOT NULL,
	Branch varchar(10) NOT NULL,
	Status varchar(50) NOT NULL,
	dates varchar(20) NOT NULL,
	inc varchar(1)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_LogError', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_LogError; 
CREATE TABLE BOSNET1.dbo.TMS_LogError (
	ID varchar(50),
	fileNmethod varchar(50),
	datas varchar(200),
	msg varchar(MAX),
	dates varchar(20)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_Params', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_Params; 
CREATE TABLE BOSNET1.dbo.TMS_Params (
	param varchar(255),
	value varchar(MAX)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_PreRouteJob', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_PreRouteJob; 
CREATE TABLE BOSNET1.dbo.TMS_PreRouteJob (
	RunId varchar(18) NOT NULL,
	Customer_ID varchar(40),
	DO_Number varchar(40) NOT NULL,
	Long varchar(100),
	Lat varchar(100),
	Customer_priority int,
	Service_time int,
	deliv_start varchar(5),
	deliv_end varchar(5),
	vehicle_type_list varchar(255),
	total_kg decimal(38,3),
	total_cubication decimal(38,3),
	DeliveryDeadline varchar(MAX),
	DayWinStart varchar(MAX),
	DayWinEnd varchar(MAX),
	UpdatevDate varchar(20),
	CreateDate varchar(20),
	isActive varchar(1) NOT NULL,
	Is_Exclude varchar(5),
	Is_Edit varchar(5),
	Product_Description varchar(40),
	Gross_Amount decimal(18,2),
	DOQty decimal(17,3),
	DOQtyUOM varchar(5),
	Name1 varchar(40),
	Street varchar(40),
	Distribution_Channel varchar(35),
	Customer_Order_Block_all varchar(2),
	Customer_Order_Block varchar(2),
	Request_Delivery_Date varchar(25),
	MarketId varchar(20),
	Desa_Kelurahan varchar(40),
	Kecamatan varchar(40),
	Kodya_Kabupaten varchar(40),
	Batch varchar(10),
	Ket_DO varchar(200)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_PreRouteVehicle', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_PreRouteVehicle; 
CREATE TABLE BOSNET1.dbo.TMS_PreRouteVehicle (
	RunId varchar(18),
	vehicle_code varchar(50),
	weight varchar(50),
	volume varchar(50),
	vehicle_type varchar(50),
	branch varchar(50),
	startLon varchar(50),
	startLat varchar(50),
	endLon varchar(50),
	endLat varchar(50),
	startTime varchar(5),
	endTime varchar(5),
	source1 varchar(4),
	UpdatevDate varchar(10),
	CreateDate varchar(10),
	isActive varchar(1),
	fixedCost decimal(18,2),
	costPerM decimal(18,2),
	costPerServiceMin decimal(18,2),
	costPerTravelMin decimal(18,2),
	IdDriver varchar(20),
	NamaDriver varchar(50)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_Progress', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_Progress; 
CREATE TABLE BOSNET1.dbo.TMS_Progress (
	runID varchar(50),
	status varchar(4),
	msg varchar(MAX),
	pct tinyint,
	mustFinish int,
	branch varchar(20),
	shift varchar(20),
	tripcalc varchar(20),
	lastUpd datetime,
	created datetime,
	maxIter int,
	DelivDate varchar(10),
	Re_RunId varchar(50),
	OriRunId varchar(50),
	Channel varchar(5)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_Result_Shipment', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_Result_Shipment; 
CREATE TABLE BOSNET1.dbo.TMS_Result_Shipment (
	Shipment_Type varchar(4),
	Plant varchar(4),
	Shipping_Type varchar(2),
	Shipment_Route varchar(6),
	Shipment_Number_Dummy varchar(30) NOT NULL,
	Description varchar(20),
	Status_Plan datetime,
	Status_Check_In datetime,
	Status_Load_Start datetime,
	Status_Load_End datetime,
	Status_Complete datetime,
	Status_Shipment_Start datetime,
	Status_Shipment_End datetime,
	Service_Agent_Id varchar(10),
	No_Pol varchar(20),
	Driver_Name varchar(30),
	Delivery_Number varchar(10) NOT NULL,
	Delivery_Item varchar(6) NOT NULL,
	Delivery_Quantity_Split decimal(17,3),
	Delivery_Quantity decimal(17,3),
	Delivery_Flag_Split varchar(1),
	Material varchar(18),
	Batch varchar(10),
	Vehicle_Number varchar(18),
	Vehicle_Type varchar(4),
	Time_Stamp datetime,
	Shipment_Number_SAP varchar(20),
	I_Status varchar(1),
	Shipment_Flag varchar(1),
	Distance decimal(18,0),
	Distance_Unit varchar(3),
	CONSTRAINT PK_TMS_Result_Shipment PRIMARY KEY (Shipment_Number_Dummy,Delivery_Number,Delivery_Item)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_RouteJob', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_RouteJob; 
CREATE TABLE BOSNET1.dbo.TMS_RouteJob (
	job_id varchar(50),
	customer_id varchar(50),
	do_number varchar(50),
	vehicle_code varchar(50),
	activity varchar(50),
	routeNb int,
	jobNb int,
	arrive varchar(5),
	depart varchar(5),
	runID varchar(50),
	create_dtm datetime,
	branch varchar(50),
	shift varchar(50),
	lon varchar(50),
	lat varchar(50),
	weight varchar(50),
	volume varchar(50),
	transportCost decimal(18,2),
	activityCost decimal(18,2),
	Dist decimal(18,2),
	isFix varchar(2)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_SALESOFFICE', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_SALESOFFICE; 
CREATE TABLE BOSNET1.dbo.TMS_SALESOFFICE (
	SalOffCode char(4),
	SalOffName varchar(50),
	SFA bit,
	RollOutDate smalldatetime,
	Address varchar(150),
	ProvinceID varchar(10),
	CityID varchar(10),
	ZipCode varchar(5),
	Telp varchar(50),
	Latitude varchar(20),
	Longitude varchar(20),
	RBMName varchar(150),
	RBMEmailAddress varchar(150),
	BMName varchar(150),
	BMEmailAddress varchar(150),
	SupportID varchar(10),
	RegionID varchar(10),
	Active bit,
	UpdatedDate smalldatetime,
	UpdatedBy varchar(25)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_ShipmentPlan', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_ShipmentPlan; 
CREATE TABLE BOSNET1.dbo.TMS_ShipmentPlan (
	DO_Number varchar(40) NOT NULL,
	Customer_ID varchar(40),
	Total_KG decimal(17,3),
	Total_Amount decimal(18,2),
	Request_Delivery_Date date,
	Route varchar(10),
	Route_Description varchar(40),
	Item_Number varchar(40) NOT NULL,
	Plant varchar(4),
	Product_ID varchar(40),
	Product_Description varchar(40),
	Total_KG_Item decimal(17,3),
	Total_Cubication decimal(17,3),
	Gross_Amount decimal(18,2),
	Net_Amount decimal(18,2),
	Already_Shipment varchar(1),
	ItemCategory varchar(6),
	DOQty decimal(17,3),
	DOQtyUOM varchar(5),
	Batch varchar(10),
	HighLevelBatch varchar(40),
	Shift smallint,
	Expired_Date_Batch varchar(10),
	DOCreationDate date,
	DOCreationTime time,
	DOUpdatedDate date,
	DOUpdatedTime time,
	Create_Date date,
	Create_Time time,
	Flag smallint,
	NotUsed_Flag char(1),
	Order_Type varchar(4),
	Incoterm varchar(3),
	Good_Movement_Status varchar(1),
	CONSTRAINT PK_TMS_ShipmentPlan PRIMARY KEY (DO_Number,Item_Number)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_Status_Shipment', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_Status_Shipment; 
CREATE TABLE BOSNET1.dbo.TMS_Status_Shipment (
	Shipment_Type varchar(4),
	Plant varchar(4),
	Shipment_Number_Dummy varchar(30) NOT NULL,
	Delivery_Number varchar(10) NOT NULL,
	Delivery_Item varchar(6) NOT NULL,
	Delivery_Split varchar(10),
	Delivery_Item_Split varchar(6),
	Delivery_Quantity_Split decimal(17,3),
	Delivery_Quantity decimal(17,3),
	Delivery_Flag_Split varchar(1),
	[TimeStamp] datetime,
	SAP_Status varchar(1),
	SAP_Message varchar(60),
	Ship_No_SAP varchar(20),
	Flag varchar(1),
	GoodMovementStatus varchar(1),
	CONSTRAINT PK_TMS_Status_Shipment PRIMARY KEY (Shipment_Number_Dummy,Delivery_Number,Delivery_Item)
) ;

IF OBJECT_ID('bosnet1.dbo.TMS_VehicleAtr', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.TMS_VehicleAtr; 
CREATE TABLE BOSNET1.dbo.TMS_VehicleAtr (
	vehicle_code varchar(50),
	branch varchar(50),
	startLon varchar(50),
	startLat varchar(50),
	endLon varchar(50),
	endLat varchar(50),
	startTime varchar(5),
	endTime varchar(5),
	source1 varchar(4),
	vehicle_type varchar(50),
	weight varchar(50),
	volume varchar(50),
	included int,
	costPerM decimal(18,2),
	fixedCost decimal(18,2),
	Channel varchar(50),
	IdDriver varchar(20),
	NamaDriver varchar(50),
	DriverDates varchar(20)
) ;

IF OBJECT_ID('bosnet1.dbo.Vehicle', 'U') IS NOT NULL DROP TABLE bosnet1.dbo.Vehicle; 
CREATE TABLE BOSNET1.dbo.Vehicle (
	Vehicle_Code varchar(50) NOT NULL,
	Vehicle_Type varchar(4),
	Total_KG_Vehicle numeric(18,3),
	Total_KG_Vehicle_Unit varchar(3),
	Total_Volume_Vehicle numeric(18,3),
	Total_Volume_Vehicle_Unit varchar(3),
	Volume numeric(18,3) NOT NULL DEFAULT ((0)),
	Volume_Unit varchar(3) NOT NULL DEFAULT ('n/a'),
	Weight numeric(18,3) NOT NULL DEFAULT ((0)),
	Weight_Unit varchar(3) NOT NULL DEFAULT ('n/a'),
	Plant varchar(4) NOT NULL DEFAULT (N'n/a'),
	Create_Date date,
	Create_Time time,
	Flag smallint,
	CONSTRAINT PK_Vehicle PRIMARY KEY (Vehicle_Code)
) ;


/*
create Procedure dbo.GetCustLongLat --'D312'
@Branch Varchar(10)
as 
	select 1
go

CREATE TABLE bosnet1.dbo.Tms_CustLongLat (
	BranchId varchar(4) NOT NULL,
	BranchName varchar(50),
	CustId varchar(10) NOT NULL,
	CustName varchar(100),
	Address varchar(MAX),
	SubDistrict varchar(254),
	District varchar(254),
	City varchar(254),
	Province varchar(254),
	PostalCode varchar(10),
	Long varchar(100),
	Lat varchar(100),
	[Source] varchar(18) NOT NULL
);
*/

INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'firstPriorityUnassignedPenalty','9999999');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'secondPriorityUnassignedPenalty','0.2');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'maxDestInAPI','1');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'SpeedKmPHour','25');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'TrafficFactor','2.6');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'MaxLoadFactor','0.95');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'CapacityInWeightOrVolume','Weight');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultCustStartTime','07:00');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultCustEndTime','15:00');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultCustPriority','5');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultCustVehicleTypes','CDE4|L300|VAN4');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultVehicleStartTime','07:00');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultVehicleEndTime','16:00');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'MaxIteration','500');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'UpdateToDbFreq','10');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'WorkingFolder','c:\\fza\log\\');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultCustServiceTime','10');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DayWinStart','2');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DayWinEnd','7');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DeliveryDeadline','AFTR');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'HargaSolar','5150');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultKonsumsiBBm','5');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'MTDefault','BFOR');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'BufferEndDefault','30');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'SatDelivDefault','12:00');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'ChannelNullDefault','GT');
INSERT INTO BOSNET1.dbo.TMS_Params (param,value) VALUES (
'DefaultDistance','10');
