--update date
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
	CustName varchar(8000),
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

insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002520','8020093492','106.786','-6.17677','2','10','07:00','14:30','CDE4|L300|VAN4','80.640','129983.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','887134.00','7.000','BOX','SUNDARI TOKO','- JL. DELIMA RAYA NO. 44 TANJUNG DU','GT',' ',' ','2018-02-16','','TANJUNG DUREN SELATAN','GROGOL PETAMBURAN','JAKARTA BARAT','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810018236','8020093542','106.758879738286','-6.20942165008926','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','118464.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','1132800.00','4.000','KAR','BAKMI PROBOLINGGO','- TAMAN BERDIKARI F/19 RAWAMANGUN','GT',' ',' ','2018-02-19','','ADIMULYA','WANAREJA','CILACAP','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042248','8020093695','106.807','-6.13708','2','10','07:00','14:30','CDE4|L300|VAN4','5.000','12620.000','AFTR','2','7','','','1','inc','ori','PALM SUGAR 40 X 250 GR','240290.00','20.000','PC','H. UDIN  TOKO','36 Jl. Muara Angke Gang Damai','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810048846','8020093791','106.864963830119','-6.21631632318145','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','SIMANJUNTAK TOKO','160 JL. JATINEGARA BARAT','GT',' ',' ','2018-02-19','1000','KAMPUNG MELAYU','JATINEGARA','JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810097643','8020093347','106.919102436981','-6.3078890681601','2','10','07:00','14:30','CDE4|L300|VAN4','773.500','908300.000','AFTR','2','7','','','1','inc','ori','Menara MGRN Krim (1116) 1x15kg Ctn','6238625.00','50.000','BOX','MUBAROK, UD','1 JL. BANTARJATI','GT',' ',' ','2018-02-17','0','SETU','CIPAYUNG','JAKARTA TIMUR','190124SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000391','8020093940','106.890523','-6.194205','2','50','07:00','14:30','CDE4|L300|VAN4','9.050','13368.000','BFOR','2','7','','','1','inc','ori','Zoda Lemonade Can 330 ml 1 X 24','125495.00','1.000','KAR','LION SUPER INDO ARION.PT','- ARION PLAZA JL. PEMUDA KAV.3 RAWA','MT',' ',' ','2018-02-21','0','-','-','-','181216KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','4.320','27528.000','BFOR','2','7','','','1','inc','ori','PANCAKE MIX VANILLA 24 X 180 GR','253387.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','10.970','18144.000','BFOR','2','7','','','1','inc','ori','Zoda OWB 250 ml 1 X 24','79800.00','24.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003654','8020093917','106.780063114985','-6.11556138064776','2','10','07:00','14:30','CDE4|L300|VAN4','28.800','51768.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','121365.00','3.000','KAR','ICE,TK','- PASAR MUARA KARANG JL BELAKANG BL','GT',' ',' ','2018-02-19','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810037284','8020093811','106.868366','-6.203419','2','10','07:00','14:30','CDE4|L300|VAN4','10.410','16643.000','AFTR','2','7','','','1','inc','edit','Kunci Mas CO 6x1.8L PCH','116148.00','1.000','BOX','CHANDRA KUARSANI BP.','- JL. KELAPA MAS VI BLOK PA -16 NO.','GT',' ',' ','2018-02-19','','PEGANGSAAN DUA','KELAPA GADING',' JAKARTA UTARA','190705SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045836','8020093652','106.777','-6.11587','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','MIKRO SHOP & CELL','D.7/42 JL. MUARA KARANG','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810079745','8020093794','106.869','-6.21846','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','WEI LING','19 JL. JATINEGARA TIMUR II','GT',' ',' ','2018-02-19','0','RAWA TERATE','CAKUNG','JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000351','8020093641','106.801789','-6.253165','3','50','08:00','14:00','CDE4|L300|VAN4','11.080','17136.000','BFOR','2','7','','','1','inc','edit','KLATU 200 ML','332072.00','48.000','PC','RANCH 99 DHARMAWANGSA','- DHARMAWANGSA SQUARE JL DHARMAWANG','MT',' ',' ','2018-02-25','0','PULO KEBAYORAN BARU','-','-','190620PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','10.000','25240.000','BFOR','2','7','','','1','inc','edit','PALM SUGAR 40 X 250 GR','536087.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','23.040','37138.000','BFOR','2','7','','','1','inc','edit','Filma Signature Cooking Oil 6x2L Pch','366383.00','12.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','180929SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000403','8020093900','106.9030286','-6.2143285','3','50','08:00','14:00','CDE4|L300|VAN4','288.000','464225.000','BFOR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','3821887.00','150.000','PC','RAMAYANA (R38) KLENDER','- JL. I GUSTI NGURAH RAI JAKARTA','MT',' ',' ','2018-02-25','0','-','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000404','8020093897','106.884342','-6.351835','3','50','08:00','14:00','CDE4|L300|VAN4','288.000','464225.000','BFOR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','3821887.00','150.000','PC','RAMAYANA R47 CIBUBUR','- RAMAYANA CIBUBUR II JL. LAPANGAN','MT',' ',' ','2018-02-25','0','CIBUBUR JAKARTA TIMUR','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000406','8020093892','106.902749','-6.214038','3','50','08:00','14:00','CDE4|L300|VAN4','576.000','928450.000','BFOR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','7643775.00','300.000','PC','RAMAYANA S.109 ROBINSON KLENDER','- JL. RAYA TERATAI PUTIH RT.14 RW.0','MT',' ',' ','2018-02-25','0','MALAKA SARI KEC.DUREN SAWIT JAKARTA TIMU','R','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000417','8020093857','106.8143824','-6.1226615','1','160','06:00','14:00','CDD6|CDE4|L300|VAN4','230.400','371380.000','BFOR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','2906469.00','20.000','BOX','INDOMARCO DC JKT 1 SEWA','- JL. ANCOL BARAT VIII NO II ANCOL','MT',' ',' ','2018-02-20','0',' UTARA','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000427','8020093841','106.8958025','-6.1536800','2','50','07:00','14:30','CDE4|L300|VAN4','921.600','1485520.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','12230039.00','80.000','BOX','LOTTE MART KELAPA GADING','- JL.RAYA BOULEVARD BARAT KELAPA GA','MT',' ',' ','2018-02-19','0','KARTA UTARA','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093928','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','180.000','301920.000','BFOR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','654550.00','10.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093928','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','192.000','345120.000','BFOR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','1047280.00','20.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','3.600','18336.000','BFOR','2','7','','','1','inc','ori','COLATTA CHOCO CHIPS 24 X 150 GR','235489.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','190423GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','ori','COLATTA COMPOUND DARK 24 X 250 GR','406493.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','191222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','ori','COLATTA COMPOUND MILK 24 X 250 GR','426457.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','190521GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','3.960','30120.000','BFOR','2','7','','','1','inc','ori','DELIMA PUDING COKELAT 24 X 165 GR','391525.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','190424GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','2.160','12504.000','BFOR','2','7','','','1','inc','ori','BENDICO COCOA 24 X 90 GR','405002.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190516GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','18336.000','BFOR','2','7','','','1','inc','ori','COLATTA CHOCO CHIPS 24 X 150 GR','235489.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190423GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','2.640','6348.000','BFOR','2','7','','','1','inc','ori','COLATTA DARK CHOC. SPREAD 12 X 220 GR','204791.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190111GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','ori','COLATTA COMPOUND DARK 24 X 250 GR','406493.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','191222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','ori','COLATTA COMPOUND MILK 24 X 250 GR','426457.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190521GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.960','30120.000','BFOR','2','7','','','1','inc','ori','DELIMA PUDING COKELAT 24 X 165 GR','391525.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190424GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.480','30120.000','BFOR','2','7','','','1','inc','ori','DELIMA PUDING STROBERI 24 X 145 GR','362227.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','181102GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','10.000','25240.000','BFOR','2','7','','','1','inc','ori','PALM SUGAR 40 X 250 GR','536087.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','27528.000','BFOR','2','7','','','1','inc','ori','HAAN CHEESE PANCAKE 24 X 150 GR','256672.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190118GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','27528.000','BFOR','2','7','','','1','inc','ori','HAAN CHOCOLATE PANCAKE 24 X 150 GR','260339.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190118GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','19128.000','BFOR','2','7','','','1','inc','ori','HAAN MAIZENA 24 X 150 GR','140328.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190626GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','4.800','2400.000','BFOR','2','7','','','1','inc','ori','WIPPY CREAM 24 X 200 GR','649677.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','200111GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000491','8020093949','106.9604016','-6.2307229','2','50','08:00','14:00','CDE4|L300|VAN4','36.000','60384.000','BFOR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','130910.00','2.000','KAR','GIANT SPM BINTARA','- JL. BINTARA RAYA N0.12 BEKASI','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000491','8020093949','106.9604016','-6.2307229','2','50','08:00','14:00','CDE4|L300|VAN4','43.200','64368.000','BFOR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','209454.00','3.000','KAR','GIANT SPM BINTARA','- JL. BINTARA RAYA N0.12 BEKASI','MT',' ',' ','2018-02-21','0','-','-','-','200130SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000507','8020093932','106.93556783','-6.27500797','2','50','07:00','14:30','CDE4|L300|VAN4','10.970','18144.000','BFOR','2','7','','','1','inc','ori','Zoda OWB 250 ml 1 X 24','84000.00','1.000','KAR','LION SUPER INDO JATIMAKMUR','- JL.  RAYA KEMANGSARI RT.004/11 KE','MT',' ',' ','2018-02-21','0','AKMUR KEC. PONDOK GEDE','-','-','190704KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000507','8020093932','106.93556783','-6.27500797','2','50','07:00','14:30','CDE4|L300|VAN4','43.200','64368.000','BFOR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','209454.00','3.000','KAR','LION SUPER INDO JATIMAKMUR','- JL.  RAYA KEMANGSARI RT.004/11 KE','MT',' ',' ','2018-02-21','0','AKMUR KEC. PONDOK GEDE','-','-','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000507','8020093932','106.93556783','-6.27500797','2','50','07:00','14:30','CDE4|L300|VAN4','19.200','34512.000','BFOR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','104728.00','2.000','KAR','LION SUPER INDO JATIMAKMUR','- JL.  RAYA KEMANGSARI RT.004/11 KE','MT',' ',' ','2018-02-21','0','AKMUR KEC. PONDOK GEDE','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001054','8020093946','106.797494','-6.243676','2','50','08:00','14:00','CDE4|L300|VAN4','126.000','211344.000','BFOR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','458185.00','7.000','KAR','GIANT SPM BLOK M','- BULUNGAN NO. 76 BLOK M PLAZA L-GR','MT',' ',' ','2018-02-21','0','KARTA SELATAN','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001054','8020093946','106.797494','-6.243676','2','50','08:00','14:00','CDE4|L300|VAN4','129.600','193104.000','BFOR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','628362.00','9.000','KAR','GIANT SPM BLOK M','- BULUNGAN NO. 76 BLOK M PLAZA L-GR','MT',' ',' ','2018-02-21','0','KARTA SELATAN','-','-','200130SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001130','8020093858','106.836418','-6.521181','1','160','06:00','14:00','CDD6|CDE4|L300|VAN4','230.400','371380.000','BFOR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','2906469.00','20.000','BOX','INDOMARCO GUDANG INDUK SENTUL','- JL.ALTERNATIF SENTUL RT01/10 KAND','MT',' ',' ','2018-02-20','0','A DESA CIJUNJUNG KEC. SUKARAJA KA. BOGOR','BOGOR TENGAH','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001130','8020093858','106.836418','-6.521181','1','160','06:00','14:00','CDD6|CDE4|L300|VAN4','12.750','15978.000','BFOR','2','7','','','1','inc','ori','Filma MGRN TF Salted 60X200G SCH','209924.00','1.000','BOX','INDOMARCO GUDANG INDUK SENTUL','- JL.ALTERNATIF SENTUL RT01/10 KAND','MT',' ',' ','2018-02-20','0','A DESA CIJUNJUNG KEC. SUKARAJA KA. BOGOR','BOGOR TENGAH','-','190122SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','115.200','207072.000','BFOR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','628368.00','288.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','37.584','65592.000','BFOR','2','7','','','1','inc','ori','ITOEN OI OCHA 500 ML (Thai)','458181.00','72.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','181002ITON','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.800','9168.000','BFOR','2','7','','','1','inc','ori','COLATTA CHOCO CHIPS 24 X 150 GR','117744.00','12.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190423GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','12.528','21864.000','BFOR','2','7','','','1','inc','ori','ITOEN JASMINE GREEN TEA 500 ML (Thai)','152727.00','24.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','180930ITON','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','18.000','30192.000','BFOR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','65455.00','12.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','15.360','31440.000','BFOR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO STRAWBERRY','287158.00','48.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190218SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','57.600','92845.000','BFOR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','764377.00','30.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190730SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.500','3786.000','BFOR','2','7','','','1','inc','ori','PALM SUGAR 40 X 250 GR','80413.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','23.040','37138.000','BFOR','2','7','','','1','inc','ori','Filma Signature Cooking Oil 6x2L Pch','366383.00','12.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','180929SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.080','6882.000','BFOR','2','7','','','1','inc','ori','PANCAKE MIX VANILLA 24 X 180 GR','63347.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','0.900','6882.000','BFOR','2','7','','','1','inc','ori','HAAN CHOCOLATE PANCAKE 24 X 150 GR','65085.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190118GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.500','3486.000','BFOR','2','7','','','1','inc','ori','COLATTA COMPOUND DARK 24 X 250 GR','101623.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','191222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820010001','8020093907','106.821410','-6.233389','2','10','07:00','09:30','CDE4|L300|VAN4','44.454','75600.000','AFTR','2','7','','','1','inc','ori','Pristine Starbucks 330 ml 1 X 24','157000.00','5.000','KAR','STARBUCKS COFFEE','KAV. 38 MENARA JAMSOSTEK JL. JENDRA','GT',' ',' ','2018-02-19','0','MAMPANG PRAPATAN','KUNINGAN BARAT','JAKARTA SELATAN','200105SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002520','8020093492','106.786','-6.17677','2','10','07:00','14:30','CDE4|L300|VAN4','80.640','129983.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','887134.00','7.000','BOX','SUNDARI TOKO','- JL. DELIMA RAYA NO. 44 TANJUNG DU','GT',' ',' ','2018-02-16','','TANJUNG DUREN SELATAN','GROGOL PETAMBURAN','JAKARTA BARAT','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002694','8020093526','106.758879738286','-6.20942165008926','2','13','07:00','14:30','CDE4|L300|VAN4','548.500','907200.000','AFTR','2','7','','','1','inc','edit','Zoda OWB 250 ml 1 X 24','3703654.00','50.000','KAR','AA JAYA TOKO','- JL. SRENGSENG RAYA NO.19 MERUYA (','GT',' ',' ','2018-02-17','0','SRENGSENG','KEMBANGAN','JAKARTA BARAT','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002777','8020093866','106.773606','-6.195931','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','850.000','1041950.000','BFOR','2','6','','','1','inc','edit','Filma CO (1016) 1x18L BIB','9450000.00','50.000','PC','ATL HW GRAHA KENCANA','- JL. PEKAPURAN NO. 5 RT. 06 RW. 03','GT',' ',' ','2018-02-21','0','PATIKRAJA','PATIKRAJA','BANYUMAS','190730SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002777','8020093866','106.773606','-6.195931','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','120.000','74260.000','BFOR','2','6','','','1','inc','edit','Kecap OJS Merah 6 Kg','2363640.00','20.000','KAR','ATL HW GRAHA KENCANA','- JL. PEKAPURAN NO. 5 RT. 06 RW. 03','GT',' ',' ','2018-02-21','0','PATIKRAJA','PATIKRAJA','BANYUMAS','190605SSST','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','291.840','597360.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO LYCHEE','5346882.00','38.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','12.160','24890.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO LYCHEE','0.00','38.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','307.200','628800.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO COCONUT','5628297.00','40.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','12.800','26200.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO COCONUT','0.00','40.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','153.600','314400.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO STRAWBERRY','2814148.00','20.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','6.400','13100.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO STRAWBERRY','0.00','20.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','46.080','94320.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO MANGO','844245.00','6.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190329SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','1.920','3930.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO MANGO','0.00','6.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190329SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','46.080','94320.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO GRAPE','844245.00','6.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','1.920','3930.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO GRAPE','0.00','6.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002876','8020093831','106.885315','-6.225855','2','10','07:00','14:30','CDE4|L300|VAN4','5.000','12620.000','AFTR','2','7','','','1','inc','edit','PALM SUGAR 40 X 250 GR','240290.00','20.000','PC','RUSTAMIO KURNIADI','- JALAN A RT 3/07 NO. 16 - KARANG A','GT',' ',' ','2018-02-19','0','KARANG ANYAR','SAWAH BESAR','JAKARTA PUSAT','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002924','8020093923','106.848669235265','-6.19223029512675','2','10','07:00','14:30','CDE4|L300|VAN4','48.000','59232.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','566400.00','2.000','KAR','DEDE TOKO','- JL. PASEBAN E.411  391-04529 BELA','GT',' ',' ','2018-02-19','','PASEBAN','SENEN','JAKARTA PUSAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002924','8020093923','106.848669235265','-6.19223029512675','2','10','07:00','14:30','CDE4|L300|VAN4','48.000','59232.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','566400.00','2.000','KAR','DEDE TOKO','- JL. PASEBAN E.411  391-04529 BELA','GT',' ',' ','2018-02-19','','PASEBAN','SENEN','JAKARTA PUSAT','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003015','8020093742','106.805888','-6.22669478','3','50','08:00','14:00','CDE4|L300|VAN4','4.140','28980.000','BFOR','2','7','','','1','inc','edit','KIYORA MILK TEA INSTANT 23g','370910.00','5.000','KAR','PT. LUCKY STRATEGIS','- KAWASAN NIAGA TERPADU SUDIRMAN','MT',' ',' ','2018-02-23','0','-','-','-','190201ITON','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003015','8020093742','106.805888','-6.22669478','3','50','08:00','14:00','CDE4|L300|VAN4','1.896','6096.000','BFOR','2','7','','','1','inc','edit','BUBUK CABE 24 X 50 GR','200000.00','1.000','KAR','PT. LUCKY STRATEGIS','- KAWASAN NIAGA TERPADU SUDIRMAN','MT',' ',' ','2018-02-23','0','-','-','-','191128BOEM','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003654','8020093917','106.780063114985','-6.11556138064776','2','10','07:00','14:30','CDE4|L300|VAN4','43.200','64368.000','AFTR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','161865.00','3.000','KAR','ICE,TK','- PASAR MUARA KARANG JL BELAKANG BL','GT',' ',' ','2018-02-19','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810018193','8020093314','106.878','-6.21485','2','10','07:00','14:30','CDE4|L300|VAN4','1547.000','1816600.000','AFTR','2','7','','','1','inc','edit','Menara MGRN Krim (1116) 1x15kg Ctn','12477250.00','100.000','BOX','DIAN TOKO','- RUKO PASAR ENJO  PISANGAN LAMA JA','GT',' ',' ','2018-02-17','','PISANGAN TIMUR','PULO GADUNG','JAKARTA TIMUR','190124SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810018236','8020093542','106.758879738286','-6.20942165008926','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','118464.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','1132800.00','4.000','KAR','BAKMI PROBOLINGGO','- TAMAN BERDIKARI F/19 RAWAMANGUN','GT',' ',' ','2018-02-19','','ADIMULYA','WANAREJA','CILACAP','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810018416','8020093793','106.864921375923','-6.21624488063951','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','HIMALAYA TOKO','- GG BANTEN X NO.35 JATINEGARA','GT',' ',' ','2018-02-19','1000','BIDARACINA','JATINEGARA','JAKARTA TIMUR','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810020481','8020093702','106.789','-6.12115','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','ALIM TOKO','- A 94. HP.08121837588 PS.PLUIT','GT',' ',' ','2018-02-17','8308','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810023862','8020093513','106.796019073262','-6.19015011092152','2','10','07:00','14:30','CDE4|L300|VAN4','11.520','18569.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','128014.00','1.000','BOX','TIGA SAUDARA TK','- PASAR SLIPI LOS 15 NO. 49 JAKARTA','GT',' ',' ','2018-02-16','6446','SLIPI','PALMERAH','JAKARTA BARAT','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810027536','8020093429','106.869','-6.29533','1','10','07:00','14:30','CDE4|L300|VAN4','773.500','908300.000','AFTR','2','7','','','1','inc','edit','Menara MGRN Krim (1116) 1x15kg Ctn','6238625.00','50.000','BOX','MEKAR JAYA PLASTIK','- PASAR KRAMAT JATI  LOS AKS 082 08','GT',' ',' ','2018-02-20','0','KAMPUNG TENGAH','KRAMAT JATI','JAKARTA TIMUR','190124SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.500','832.000','AFTR','2','7','','','1','inc','edit','COLATTA FINEZA COMPOUND DARK 24 X 250 GR','32730.00','2.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190307GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.324','804.000','AFTR','2','7','','','1','inc','edit','Kecap OJS Hijau 135 ml','11155.00','2.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200912SSST','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.280','556.000','AFTR','2','7','','','1','inc','edit','Klatu Multipack 1/12/4X70ml','7063.00','4.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','1.427','1050.000','AFTR','2','7','','','1','inc','edit','M-150 ENERGY DRINK BOTTLE 150 ML','14909.00','5.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190601M150','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.340','22944.000','AFTR','2','7','','','1','inc','edit','MEGKEJU SERBAGUNA 170 GRAM','19701.00','2.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190215MEGS','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030473','8020093661','106.798819','-6.123549','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','LARIS TOKO','- APARTEMEN LAGUNA KIOS K1-12 KEL.','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030474','8020093657','106.7992178','-6.1254072','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','REZEKI TOKO','- APARTEMEN LAGUNA KIOS 1-28 JL. PL','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030488','8020093651','106.78286174636','-6.13810292405873','2','10','07:00','14:30','CDE4|L300|VAN4','23.040','33680.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 12x1L Pch','261106.00','2.000','BOX','SUSIANA TOKO','- JL. TELUK GONG RAYA JL. K NO.16','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190116SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030442','8020093863','106.799556','-6.177246','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','850.000','1041950.000','BFOR','2','6','','','1','inc','edit','Filma CO (1016) 1x18L BIB','9450000.00','50.000','PC','TIMHOWAN RESTAURANT','- GD. GRAHA ANTERO JALAN TOMANG RAY','GT',' ',' ','2018-02-21','0','.27 RT. RW. KEL. TOMANG KEC. GROGOL PETA','MBURAN, JAKARTA BARAT, DKI JAKARTA','-','190717SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030442','8020093863','106.799556','-6.177246','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','850.000','1041950.000','BFOR','2','6','','','1','inc','edit','Filma CO (1016) 1x18L BIB','9450000.00','50.000','PC','TIMHOWAN RESTAURANT','- GD. GRAHA ANTERO JALAN TOMANG RAY','GT',' ',' ','2018-02-21','0','.27 RT. RW. KEL. TOMANG KEC. GROGOL PETA','MBURAN, JAKARTA BARAT, DKI JAKARTA','-','190730SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030442','8020093864','106.799556','-6.177246','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','778.500','1000250.000','BFOR','2','6','','','1','inc','edit','Filma Pusaka SHT Wht BKF 1x15Kg Ctn','10659950.00','50.000','BOX','TIMHOWAN RESTAURANT','- GD. GRAHA ANTERO JALAN TOMANG RAY','GT',' ',' ','2018-02-21','0','.27 RT. RW. KEL. TOMANG KEC. GROGOL PETA','MBURAN, JAKARTA BARAT, DKI JAKARTA','-','181001SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810035698','8020093649','106.773','-6.10931','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','KARTINI TOKO','- PS.MUARA ANGKE NO.97 KEL. PLUIT K','GT',' ',' ','2018-02-17','8320','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810037127','8020092799','106.86777418','-6.17983951','2','10','07:00','14:30','CDE4|L300|VAN4','0.280','556.000','AFTR','2','7','','','1','inc','edit','Klatu Multipack 1/12/4X70ml','7063.00','4.000','PC','SAHAT JAYA TOKO','- JL.CEMPAKA PUTIH TENGAH II NO.39','GT',' ',' ','2018-02-13','0','CEMPAKA PUTIH TIMUR','CEMPAKA PUTIH','JAKARTA PUSAT','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810037284','8020093811','106.868366','-6.203419','2','10','07:00','14:30','CDE4|L300|VAN4','1.680','3336.000','AFTR','2','7','','','1','inc','edit','Klatu Multipack 1/12/4X70ml','42546.00','24.000','PC','CHANDRA KUARSANI BP.','- JL. KELAPA MAS VI BLOK PA -16 NO.','GT',' ',' ','2018-02-19','','PEGANGSAAN DUA','KELAPA GADING',' JAKARTA UTARA','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810040902','8020093956','106.8933644','-6.1598625','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','192.000','345120.000','BFOR','2','6','','','1','inc','edit','New Pristine 400 ml 1 X 24','809100.00','20.000','KAR','DELTA GADING1','- JL.BOULEVARD BUKIT GADING RAYA BL','GT',' ',' ','2018-02-21','0','NO.30-34 JAKARTA UTARA','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042247','8020093648','106.775','-6.10659','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','SUKARDI H. TOKO','1 Jl. Muara Angke Blok L3','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042248','8020093695','106.807','-6.13708','2','10','07:00','14:30','CDE4|L300|VAN4','5.000','12620.000','AFTR','2','7','','','1','inc','edit','PALM SUGAR 40 X 250 GR','240290.00','20.000','PC','H. UDIN  TOKO','36 Jl. Muara Angke Gang Damai','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042436','8020093645','106.77832559','-6.11589457','2','10','07:00','14:30','CDE4|L300|VAN4','9.680','22991.000','AFTR','2','7','','','1','inc','edit','Kunci Mas CO 48x200mL PCH','121311.00','1.000','BOX','MODENA TOKO','19 Jl. Pasar Muara Karang Block 22','GT',' ',' ','2018-02-17','6894','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190424SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042448','8020093264','106.804','-6.18552','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','UNTUNG JAYA TOKO','6 Jl. Kota Bambu Utara 6','GT',' ',' ','2018-02-15','0','KOTA BAMBU SELATAN','PALMERAH','JAKARTA BARAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO COCONUT','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO COCONUT','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO GRAPE','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO GRAPE','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO LYCHEE','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO LYCHEE','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO STRAWBERRY','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO STRAWBERRY','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042883','8020093643','106.779164766061','-6.1165472998213','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','FRANKFURTER MUARA KARANG','2A JL. MUARA KARANG BLOK A5 SELATAN','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042884','8020093646','106.772831','-6.110847','2','10','07:00','14:30','CDE4|L300|VAN4','9.680','22991.000','AFTR','2','7','','','1','inc','edit','Kunci Mas CO 48x200mL PCH','121311.00','1.000','BOX','AL KUATSAR TOKO','1 JL. MUARA ANGKE','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190424SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810044717','8020093654','106.777','-6.10836','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','TRAGO JAYA TOKO','31 JL. MANDALA BAHARI','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045218','8020092800','106.8813559354','-6.16508297252579','2','10','07:00','14:30','CDE4|L300|VAN4','28.800','42912.000','AFTR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','107910.00','2.000','KAR','MARON JAYA TOKO','LOS DEPAN PASAR PEDONGKELAN JL. INS','GT',' ',' ','2018-02-13','','KELAPA GADING BARAT','KELAPA GADING','JAKARTA UTARA','200130SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045252','8020093876','106.823412',' -6.144251','3','10','07:00','14:30','CDE4|L300|VAN4','54.850','90720.000','AFTR','2','7','','','1','inc','edit','Zoda OWB 250 ml 1 X 24','381820.00','5.000','KAR','ALVI SNACK','32 JL. MANGGA BESAR IX, PASAR PECAH','GT',' ',' ','2018-02-21','0','TANGKI','TAMAN SARI','JAKARTA BARAT','190704KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','6.830','10800.000','AFTR','2','7','','','1','inc','edit','COCODAY 250 ML','80000.00','1.000','KAR','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','190112PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','1.320','3168.000','AFTR','2','7','','','1','inc','edit','Kecap OJS Hijau 275 ml x 24','38717.00','4.000','PC','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','200606SSST','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','0.560','1112.000','AFTR','2','7','','','1','inc','edit','Klatu Multipack 1/12/4X70ml','14210.00','8.000','PC','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','0.544','8920.000','AFTR','2','7','','','1','inc','edit','MEGCHEDDAR SLICE 8 SLICE 136 GR','42822.00','4.000','PAK','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','180905MEGS','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810047792','8020093496','106.802141263851','-6.15465923893317','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','RIZKI TOKO','10 JL. DURI UTARA 99, LEMBAYUNG ( B','GT',' ',' ','2018-02-16','0','DURI SELATAN','TAMBORA','JAKARTA BARAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810048409','8020093653','106.779355928176','-6.11575564592053','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','MEGA PLASTIK TOKO','PASAR MUARA KARANG LT. 1A 64-66','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810048846','8020093791','106.864963830119','-6.21631632318145','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','SIMANJUNTAK TOKO','160 JL. JATINEGARA BARAT','GT',' ',' ','2018-02-19','1000','KAMPUNG MELAYU','JATINEGARA','JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810049142','8020093650','106.741488471442','-6.10326638621398','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','WONG PIN SHIN TOKO','PASAR PEJAGALAN BLOK AKS 75','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810050049','8020093804','106.8775015','-6.17935764','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','AKIONG TOKO','10 JL.AMPERA 4','GT',' ',' ','2018-02-19','0','PADEMANGAN BARAT','PADEMANGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810050275','8020093789','106.865','-6.21626','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','SURYA PD TOKO','PASAR JATINEGARA BASEMENT LOS AKS 2','GT',' ',' ','2018-02-19','1000','BALI MESTER','JATINEGARA','JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810050276','8020093792','106.869803140828','-6.21604313264148','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','KAPAL BOMBER TOKO','PASAR JATINEGARA LANTAI BASEMENT BL','GT',' ',' ','2018-02-19','','BALI MESTER','JATINEGARA',' JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810052765','8020093795','106.864807460628','-6.21633160338675','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','RATNA TOKO','7 PASAR JATINEGARA PINTU SELATAN','GT',' ',' ','2018-02-19','1000','BALI MESTER','JATINEGARA','JAKARTA TIMUR','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810052769','8020093642','106.776','-6.10423','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','172560.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','404550.00','10.000','KAR','SPBU 2414406','KOMPLEK PELELANGAN IKAN MUARA ANGKE','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810053639','8020093921','106.9348527','-6.19062546','2','10','07:00','14:30','CDE4|L300|VAN4','548.500','907200.000','AFTR','2','7','','','1','inc','edit','Zoda OWB 250 ml 1 X 24','3703654.00','50.000','KAR','AHMI TOKO','32 JL. RAYA PENGGILINGAN ( DEPAN PE','GT',' ',' ','2018-02-19','','PENGGILINGAN','CAKUNG','JAKARTA TIMUR','190704KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810054771','8020093790','106.865','-6.21626','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','RATNA TOKO','7 PASAR JATINEGARA PINTU SELATAN','GT',' ',' ','2018-02-19','0','BALI MESTER','JATINEGARA',' JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810054773','8020093875','106.800776','-6.298731','3','10','07:00','14:30','CDE4|L300|VAN4','1800.000','2682000.000','AFTR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','6272269.00','125.000','KAR','ANISA JAYA TOKO','01 GANG MANDOR AMIN/SAMPING GERBANG','GT',' ',' ','2018-02-21','','KEMBANGAN SELATAN','KEMBANGAN','JAKARTA BARAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810058307','8020093162','106.823','-6.19122','2','10','07:00','14:30','VAN4','900.000','1509600.000','AFTR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','2301890.00','50.000','KAR','GALERI SINARMAS','Sinarmas Land Plaza - Tower 2 Lt. L','GT',' ',' ','2018-02-14','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810058307','8020093162','106.823','-6.19122','2','10','07:00','14:30','VAN4','1920.000','3451200.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','7362810.00','200.000','KAR','GALERI SINARMAS','Sinarmas Land Plaza - Tower 2 Lt. L','GT',' ',' ','2018-02-14','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810058307','8020093162','106.823','-6.19122','2','10','07:00','14:30','VAN4','720.000','1072800.000','AFTR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','2454952.00','50.000','KAR','GALERI SINARMAS','Sinarmas Land Plaza - Tower 2 Lt. L','GT',' ',' ','2018-02-14','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810062471','8020093786','106.878','-6.21485','2','10','07:00','14:30','CDE4|L300|VAN4','11.520','18569.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','128014.00','1.000','BOX','LISNA 2 TOKO','2 JL. PUJANA TIRTA','GT',' ',' ','2018-02-19','','PISANGAN TIMUR','PULO GADUNG','JAKARTA TIMUR','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810062703','8020093696','106.783','-6.13836','2','10','07:00','14:30','CDE4|L300|VAN4','5.000','12620.000','AFTR','2','7','','','1','inc','edit','PALM SUGAR 40 X 250 GR','240290.00','20.000','PC','ASENG TOKO','19 JL. B RAYA TELUK GONG','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810068845','8020092706','106.864743919487','-6.21618306860649','2','10','07:00','14:30','CDE4|L300|VAN4','28.800','51768.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','121365.00','3.000','KAR','BINTANG BARU TOKO','NO.240 PASAR JATI NEGARA BLOK AKS','GT',' ',' ','2018-02-13','1000','KAMPUNG MELAYU','JATINEGARA','JAKARTA TIMUR','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810074686','8020093027','106.858','-6.32174','2','10','07:00','14:30','CDE4|L300|VAN4','21.940','36288.000','AFTR','2','7','','','1','inc','edit','Zoda OWB 250 ml 1 X 24','152728.00','2.000','KAR','SRI MULYO TOKO','10 JL PERTENGAHAN','GT',' ',' ','2018-02-14','','CIJANTUNG','PASAR REBO','JAKARTA TIMUR','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810075424','8020093395','106.837','-6.17657','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','118464.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','1132800.00','4.000','KAR','PRIMKOPAD RSPAD','24 JL.ABDULRAHMAN SALEH','GT',' ',' ','2018-02-17','0','PASAR BARU','SAWAH BESAR','JAKARTA PUSAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810075424','8020093395','106.837','-6.17657','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','118464.000','AFTR','2','7','','','1','inc','edit','GULAKU PREMIUM (PUTIH) 24 X 1KG','1132800.00','4.000','KAR','PRIMKOPAD RSPAD','24 JL.ABDULRAHMAN SALEH','GT',' ',' ','2018-02-17','0','PASAR BARU','SAWAH BESAR','JAKARTA PUSAT','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810075424','8020093395','106.837','-6.17657','2','10','07:00','14:30','CDE4|L300|VAN4','60.000','74160.000','AFTR','2','7','','','1','inc','edit','GULAKU PREM (PTH) 40 X 1/2 KG','708000.00','3.000','KAR','PRIMKOPAD RSPAD','24 JL.ABDULRAHMAN SALEH','GT',' ',' ','2018-02-17','0','PASAR BARU','SAWAH BESAR','JAKARTA PUSAT','190711GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810096761','8020093887','106.783','-6.18179','3','10','07:00','14:30','CDE4|L300|VAN4','288.000','517680.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','1152967.00','30.000','KAR','DANIEL TOKO','11 DUKUH BARAT GANG 2 TANJUNG DUREN','GT',' ',' ','2018-02-21','','TANJUNG DUREN','GROGOL PETAMBURAN','JAKARTA BARAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810096761','8020093887','106.783','-6.18179','3','10','07:00','14:30','CDE4|L300|VAN4','288.000','429120.000','AFTR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','1025145.00','20.000','KAR','DANIEL TOKO','11 DUKUH BARAT GANG 2 TANJUNG DUREN','GT',' ',' ','2018-02-21','','TANJUNG DUREN','GROGOL PETAMBURAN','JAKARTA BARAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810097643','8020093347','106.919102436981','-6.3078890681601','2','10','07:00','14:30','CDE4|L300|VAN4','773.500','908300.000','AFTR','2','7','','','1','inc','edit','Menara MGRN Krim (1116) 1x15kg Ctn','6238625.00','50.000','BOX','MUBAROK, UD','1 JL. BANTARJATI','GT',' ',' ','2018-02-17','0','SETU','CIPAYUNG','JAKARTA TIMUR','190124SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810098709','8020093037','106.872555','-6.345189','2','10','07:00','14:30','CDE4|L300|VAN4','36.000','60384.000','AFTR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','101182.00','2.000','KAR','PUTRA KEMBAR','BKS 48 PASAR CIBUBUR','GT',' ',' ','2018-02-14','1139','CIBUBUR','CIRACAS','JAKARTA TIMUR','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810100481','8020093970','106.758879738286','-6.20942165008926','3','10','07:00','14:30','CDE4|L300|VAN4','69.120','101040.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 12x1L Pch','775483.00','6.000','BOX','AMINAH TOKO','103 PASAR CIKINI BLOK A  RT 001 RW','GT',' ',' ','2018-02-22','','CIKINI','MENTENG','JAKARTA PUSAT','190116SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093888','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','960.000','1725600.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','3762315.00','100.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093889','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','960.000','1725600.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','3762315.00','100.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093890','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','960.000','1725600.000','AFTR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','3762315.00','100.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093890','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','72.000','107280.000','AFTR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','250891.00','5.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','69.120','111414.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','760400.00','6.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','115.200','168400.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 12x1L Pch','1292473.00','10.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190116SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','104.100','166430.000','AFTR','2','7','','','1','inc','edit','Kunci Mas CO 6x1.8L PCH','1149862.00','10.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190705SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','20.800','32162.000','AFTR','2','7','','','1','inc','edit','Kunci Mas CO 12x900mL PCH','237000.00','2.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190130SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','43.880','72576.000','AFTR','2','7','','','1','inc','edit','Zoda OWB 250 ml 1 X 24','305456.00','4.000','KAR','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093272','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','120.000','148080.000','AFTR','2','7','','','1','inc','edit','GULAKU (KUNING) 24 X 1KG','1416000.00','5.000','KAR','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000061','8020093951','106.9228310','-6.2982393','2','50','08:00','14:00','CDE4|L300|VAN4','18.000','30192.000','BFOR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','65455.00','1.000','KAR','GIANT SPM JATI RAHAYU','- JLN RAYA HANKAM NO.199 BEKASI','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000256','8020093936','106.7652930','-6.2165317','2','50','07:00','14:30','CDE4|L300|VAN4','28.800','42912.000','BFOR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','139636.00','2.000','KAR','LION SUPER INDO KLAPA DUA','- JL. POS PENGUMBEN RAYA JAKARTA BA','MT',' ',' ','2018-02-21','0','-','-','-','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000256','8020093936','106.7652930','-6.2165317','2','50','07:00','14:30','CDE4|L300|VAN4','28.800','51768.000','BFOR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','157092.00','3.000','KAR','LION SUPER INDO KLAPA DUA','- JL. POS PENGUMBEN RAYA JAKARTA BA','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000268','8020093637','106.782590','-6.221578','3','50','08:00','14:00','CDE4|L300|VAN4','9.360','36792.000','BFOR','2','7','','','1','inc','edit','HATARI SEE HONG PUFF MARGARINE 36BKS','181818.00','36.000','PC','SOGO BELLEZA SHOPPING','- JL. LETJEN SOEPONO NO.34','MT',' ',' ','2018-02-22','0','PERMATA HIJAU JAKARTA BARAT','-','-','190405ASWF','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000268','8020093637','106.782590','-6.221578','3','50','08:00','14:00','CDE4|L300|VAN4','3.705','20190.000','BFOR','2','7','','','1','inc','edit','HATARI MALKIST CHOCOLATE 30 BKS 120 GR','134182.00','30.000','PC','SOGO BELLEZA SHOPPING','- JL. LETJEN SOEPONO NO.34','MT',' ',' ','2018-02-22','0','PERMATA HIJAU JAKARTA BARAT','-','-','190717ASWF','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000288','8020093893','106.79581211','-6.20838109','3','50','08:00','14:00','CDE4|L300|VAN4','288.000','464225.000','BFOR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','3821887.00','150.000','PC','RAMAYANA (R10). PALMERAH','- RAMAYANA PALMERAH JLN PASAR PALME','MT',' ',' ','2018-02-25','0','ARTA BARAT','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000351','8020093641','106.801789','-6.253165','3','50','08:00','14:00','CDE4|L300|VAN4','3.420','6240.000','BFOR','2','7','','','1','inc','edit','KLATU 70 ML','112582.00','48.000','PC','RANCH 99 DHARMAWANGSA','- DHARMAWANGSA SQUARE JL DHARMAWANG','MT',' ',' ','2018-02-25','0','PULO KEBAYORAN BARU','-','-','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000385','8020093942','106.943395','-6.226001','2','50','08:00','14:00','CDE4|L300|VAN4','54.000','90576.000','BFOR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','196365.00','3.000','KAR','GIANT SPM PONDOK KOPI','- JL ROBUSTA NO 1 PONDOK KOPI JAKAR','MT',' ',' ','2018-02-21','0','R','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000385','8020093942','106.943395','-6.226001','2','50','08:00','14:00','CDE4|L300|VAN4','43.200','64368.000','BFOR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','209454.00','3.000','KAR','GIANT SPM PONDOK KOPI','- JL ROBUSTA NO 1 PONDOK KOPI JAKAR','MT',' ',' ','2018-02-21','0','R','-','-','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000391','8020093940','106.890523','-6.194205','2','50','07:00','14:30','CDE4|L300|VAN4','9.050','13368.000','BFOR','2','7','','','1','inc','edit','Zoda Lemonade Can 330 ml 1 X 24','125495.00','1.000','KAR','LION SUPER INDO ARION.PT','- ARION PLAZA JL. PEMUDA KAV.3 RAWA','MT',' ',' ','2018-02-21','0','-','-','-','181216KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000403','8020093900','106.9030286','-6.2143285','3','50','08:00','14:00','CDE4|L300|VAN4','288.000','464225.000','BFOR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','3821887.00','150.000','PC','RAMAYANA (R38) KLENDER','- JL. I GUSTI NGURAH RAI JAKARTA','MT',' ',' ','2018-02-25','0','-','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000404','8020093897','106.884342','-6.351835','3','50','08:00','14:00','CDE4|L300|VAN4','288.000','464225.000','BFOR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','3821887.00','150.000','PC','RAMAYANA R47 CIBUBUR','- RAMAYANA CIBUBUR II JL. LAPANGAN','MT',' ',' ','2018-02-25','0','CIBUBUR JAKARTA TIMUR','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000406','8020093892','106.902749','-6.214038','3','50','08:00','14:00','CDE4|L300|VAN4','576.000','928450.000','BFOR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','7643775.00','300.000','PC','RAMAYANA S.109 ROBINSON KLENDER','- JL. RAYA TERATAI PUTIH RT.14 RW.0','MT',' ',' ','2018-02-25','0','MALAKA SARI KEC.DUREN SAWIT JAKARTA TIMU','R','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000417','8020093857','106.8143824','-6.1226615','1','160','06:00','14:00','CDD6|CDE4|L300|VAN4','230.400','371380.000','BFOR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','2906469.00','20.000','BOX','INDOMARCO DC JKT 1 SEWA','- JL. ANCOL BARAT VIII NO II ANCOL','MT',' ',' ','2018-02-20','0',' UTARA','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000427','8020093841','106.8958025','-6.1536800','2','50','07:00','14:30','CDE4|L300|VAN4','921.600','1485520.000','AFTR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','12230039.00','80.000','BOX','LOTTE MART KELAPA GADING','- JL.RAYA BOULEVARD BARAT KELAPA GA','MT',' ',' ','2018-02-19','0','KARTA UTARA','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093928','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','180.000','301920.000','BFOR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','654550.00','10.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093928','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','192.000','345120.000','BFOR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','1047280.00','20.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','3.600','18336.000','BFOR','2','7','','','1','inc','edit','COLATTA CHOCO CHIPS 24 X 150 GR','235489.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','190423GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','edit','COLATTA COMPOUND DARK 24 X 250 GR','406493.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','191222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','edit','COLATTA COMPOUND MILK 24 X 250 GR','426457.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','190521GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093717','106.908558','-6.158286','1','50','08:00','14:00','CDE4|L300|VAN4','3.960','30120.000','BFOR','2','7','','','1','inc','edit','DELIMA PUDING COKELAT 24 X 165 GR','391525.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-20','0','L BOULEVARD KELAPA GADING','-','-','190424GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','2.160','12504.000','BFOR','2','7','','','1','inc','edit','BENDICO COCOA 24 X 90 GR','405002.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190516GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','18336.000','BFOR','2','7','','','1','inc','edit','COLATTA CHOCO CHIPS 24 X 150 GR','235489.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190423GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','2.640','6348.000','BFOR','2','7','','','1','inc','edit','COLATTA DARK CHOC. SPREAD 12 X 220 GR','204791.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190111GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','edit','COLATTA COMPOUND DARK 24 X 250 GR','406493.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','191222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','6.000','13944.000','BFOR','2','7','','','1','inc','edit','COLATTA COMPOUND MILK 24 X 250 GR','426457.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190521GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.960','30120.000','BFOR','2','7','','','1','inc','edit','DELIMA PUDING COKELAT 24 X 165 GR','391525.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190424GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.480','30120.000','BFOR','2','7','','','1','inc','edit','DELIMA PUDING STROBERI 24 X 145 GR','362227.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','181102GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','27528.000','BFOR','2','7','','','1','inc','edit','HAAN CHEESE PANCAKE 24 X 150 GR','256672.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190118GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','27528.000','BFOR','2','7','','','1','inc','edit','HAAN CHOCOLATE PANCAKE 24 X 150 GR','260339.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190118GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','4.320','27528.000','BFOR','2','7','','','1','inc','edit','PANCAKE MIX VANILLA 24 X 180 GR','253387.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','3.600','19128.000','BFOR','2','7','','','1','inc','edit','HAAN MAIZENA 24 X 150 GR','140328.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','190626GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000431','8020093775','106.908558','-6.158286','2','50','08:00','14:00','CDE4|L300|VAN4','4.800','2400.000','BFOR','2','7','','','1','inc','edit','WIPPY CREAM 24 X 200 GR','649677.00','1.000','KAR','FARMER KELAPA GADING','- MALL KELAPA GADING 124 LT DASAR B','MT',' ',' ','2018-02-21','0','L BOULEVARD KELAPA GADING','-','-','200111GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000491','8020093949','106.9604016','-6.2307229','2','50','08:00','14:00','CDE4|L300|VAN4','36.000','60384.000','BFOR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','130910.00','2.000','KAR','GIANT SPM BINTARA','- JL. BINTARA RAYA N0.12 BEKASI','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000491','8020093949','106.9604016','-6.2307229','2','50','08:00','14:00','CDE4|L300|VAN4','43.200','64368.000','BFOR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','209454.00','3.000','KAR','GIANT SPM BINTARA','- JL. BINTARA RAYA N0.12 BEKASI','MT',' ',' ','2018-02-21','0','-','-','-','200130SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000507','8020093932','106.93556783','-6.27500797','2','50','07:00','14:30','CDE4|L300|VAN4','10.970','18144.000','BFOR','2','7','','','1','inc','edit','Zoda OWB 250 ml 1 X 24','84000.00','1.000','KAR','LION SUPER INDO JATIMAKMUR','- JL.  RAYA KEMANGSARI RT.004/11 KE','MT',' ',' ','2018-02-21','0','AKMUR KEC. PONDOK GEDE','-','-','190704KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000507','8020093932','106.93556783','-6.27500797','2','50','07:00','14:30','CDE4|L300|VAN4','43.200','64368.000','BFOR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','209454.00','3.000','KAR','LION SUPER INDO JATIMAKMUR','- JL.  RAYA KEMANGSARI RT.004/11 KE','MT',' ',' ','2018-02-21','0','AKMUR KEC. PONDOK GEDE','-','-','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000507','8020093932','106.93556783','-6.27500797','2','50','07:00','14:30','CDE4|L300|VAN4','19.200','34512.000','BFOR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','104728.00','2.000','KAR','LION SUPER INDO JATIMAKMUR','- JL.  RAYA KEMANGSARI RT.004/11 KE','MT',' ',' ','2018-02-21','0','AKMUR KEC. PONDOK GEDE','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001054','8020093946','106.797494','-6.243676','2','50','08:00','14:00','CDE4|L300|VAN4','126.000','211344.000','BFOR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','458185.00','7.000','KAR','GIANT SPM BLOK M','- BULUNGAN NO. 76 BLOK M PLAZA L-GR','MT',' ',' ','2018-02-21','0','KARTA SELATAN','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001054','8020093946','106.797494','-6.243676','2','50','08:00','14:00','CDE4|L300|VAN4','129.600','193104.000','BFOR','2','7','','','1','inc','edit','New Pristine 600 ml 1 X 24','628362.00','9.000','KAR','GIANT SPM BLOK M','- BULUNGAN NO. 76 BLOK M PLAZA L-GR','MT',' ',' ','2018-02-21','0','KARTA SELATAN','-','-','200130SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001130','8020093858','106.836418','-6.521181','1','160','06:00','14:00','CDD6|CDE4|L300|VAN4','230.400','371380.000','BFOR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','2906469.00','20.000','BOX','INDOMARCO GUDANG INDUK SENTUL','- JL.ALTERNATIF SENTUL RT01/10 KAND','MT',' ',' ','2018-02-20','0','A DESA CIJUNJUNG KEC. SUKARAJA KA. BOGOR','BOGOR TENGAH','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001130','8020093858','106.836418','-6.521181','1','160','06:00','14:00','CDD6|CDE4|L300|VAN4','12.750','15978.000','BFOR','2','7','','','1','inc','edit','Filma MGRN TF Salted 60X200G SCH','209924.00','1.000','BOX','INDOMARCO GUDANG INDUK SENTUL','- JL.ALTERNATIF SENTUL RT01/10 KAND','MT',' ',' ','2018-02-20','0','A DESA CIJUNJUNG KEC. SUKARAJA KA. BOGOR','BOGOR TENGAH','-','190122SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','115.200','207072.000','BFOR','2','7','','','1','inc','edit','New Pristine 400 ml 1 X 24','628368.00','288.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','37.584','65592.000','BFOR','2','7','','','1','inc','edit','ITOEN OI OCHA 500 ML (Thai)','458181.00','72.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','181002ITON','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.800','9168.000','BFOR','2','7','','','1','inc','edit','COLATTA CHOCO CHIPS 24 X 150 GR','117744.00','12.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190423GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','12.528','21864.000','BFOR','2','7','','','1','inc','edit','ITOEN JASMINE GREEN TEA 500 ML (Thai)','152727.00','24.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','180930ITON','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','18.000','30192.000','BFOR','2','7','','','1','inc','edit','New Pristine 1500 ml 1 X 12','65455.00','12.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','15.360','31440.000','BFOR','2','7','','','1','inc','edit','MOGU-MOGU NATA DE COCO STRAWBERRY','287158.00','48.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190218SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','57.600','92845.000','BFOR','2','7','','','1','inc','edit','Filma Cooking Oil (0716) 6x2L Pch','764377.00','30.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190730SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.500','3786.000','BFOR','2','7','','','1','inc','edit','PALM SUGAR 40 X 250 GR','80413.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.080','6882.000','BFOR','2','7','','','1','inc','edit','PANCAKE MIX VANILLA 24 X 180 GR','63347.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','0.900','6882.000','BFOR','2','7','','','1','inc','edit','HAAN CHOCOLATE PANCAKE 24 X 150 GR','65085.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190118GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','10.970','18144.000','BFOR','2','7','','','1','inc','edit','Zoda OWB 250 ml 1 X 24','79800.00','24.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820001275','8020093436','106.908626','-6.156832','2','50','08:00','14:00','CDE4|L300|VAN4','1.500','3486.000','BFOR','2','7','','','1','inc','edit','COLATTA COMPOUND DARK 24 X 250 GR','101623.00','6.000','PC','THE FOODHALL KELAPA GADING','- JL. KELAPA GADING  BOULEVAR BIO','MT',' ',' ','2018-02-21','0','-','-','-','191222GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820010001','8020093907','106.821410','-6.233389','2','10','07:00','09:30','CDE4|L300|VAN4','44.454','75600.000','AFTR','2','7','','','1','inc','edit','Pristine Starbucks 330 ml 1 X 24','157000.00','5.000','KAR','STARBUCKS COFFEE','KAV. 38 MENARA JAMSOSTEK JL. JENDRA','GT',' ',' ','2018-02-19','0','MAMPANG PRAPATAN','KUNINGAN BARAT','JAKARTA SELATAN','200105SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002694','8020093526','106.758879738286','-6.20942165008926','2','13','07:00','14:30','CDE4|L300|VAN4','548.500','907200.000','AFTR','2','7','','','1','inc','ori','Zoda OWB 250 ml 1 X 24','3703654.00','50.000','KAR','AA JAYA TOKO','- JL. SRENGSENG RAYA NO.19 MERUYA (','GT',' ',' ','2018-02-17','0','SRENGSENG','KEMBANGAN','JAKARTA BARAT','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002777','8020093866','106.773606','-6.195931','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','850.000','1041950.000','BFOR','2','6','','','1','inc','ori','Filma CO (1016) 1x18L BIB','9450000.00','50.000','PC','ATL HW GRAHA KENCANA','- JL. PEKAPURAN NO. 5 RT. 06 RW. 03','GT',' ',' ','2018-02-21','0','PATIKRAJA','PATIKRAJA','BANYUMAS','190730SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002777','8020093866','106.773606','-6.195931','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','120.000','74260.000','BFOR','2','6','','','1','inc','ori','Kecap OJS Merah 6 Kg','2363640.00','20.000','KAR','ATL HW GRAHA KENCANA','- JL. PEKAPURAN NO. 5 RT. 06 RW. 03','GT',' ',' ','2018-02-21','0','PATIKRAJA','PATIKRAJA','BANYUMAS','190605SSST','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','291.840','597360.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO LYCHEE','5346882.00','38.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','12.160','24890.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO LYCHEE','0.00','38.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','307.200','628800.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO COCONUT','5628297.00','40.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','12.800','26200.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO COCONUT','0.00','40.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','153.600','314400.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO STRAWBERRY','2814148.00','20.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','6.400','13100.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO STRAWBERRY','0.00','20.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','46.080','94320.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO MANGO','844245.00','6.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190329SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','1.920','3930.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO MANGO','0.00','6.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190329SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','46.080','94320.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO GRAPE','844245.00','6.000','KAR','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002862','8020093904','106.865179256961','-6.21644319451683','2','10','07:00','14:30','CDE4|L300|VAN4','1.920','3930.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO GRAPE','0.00','6.000','PC','MUJUR TOKO','- JL. PASAR PINTU SELATAN JATINEGAR','GT',' ',' ','2018-02-19','','JATINEGARA','CAKUNG','JAKARTA TIMUR','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002876','8020093831','106.885315','-6.225855','2','10','07:00','14:30','CDE4|L300|VAN4','5.000','12620.000','AFTR','2','7','','','1','inc','ori','PALM SUGAR 40 X 250 GR','240290.00','20.000','PC','RUSTAMIO KURNIADI','- JALAN A RT 3/07 NO. 16 - KARANG A','GT',' ',' ','2018-02-19','0','KARANG ANYAR','SAWAH BESAR','JAKARTA PUSAT','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002924','8020093923','106.848669235265','-6.19223029512675','2','10','07:00','14:30','CDE4|L300|VAN4','48.000','59232.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','566400.00','2.000','KAR','DEDE TOKO','- JL. PASEBAN E.411  391-04529 BELA','GT',' ',' ','2018-02-19','','PASEBAN','SENEN','JAKARTA PUSAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810002924','8020093923','106.848669235265','-6.19223029512675','2','10','07:00','14:30','CDE4|L300|VAN4','48.000','59232.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','566400.00','2.000','KAR','DEDE TOKO','- JL. PASEBAN E.411  391-04529 BELA','GT',' ',' ','2018-02-19','','PASEBAN','SENEN','JAKARTA PUSAT','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003015','8020093742','106.805888','-6.22669478','3','50','08:00','14:00','CDE4|L300|VAN4','4.140','28980.000','BFOR','2','7','','','1','inc','ori','KIYORA MILK TEA INSTANT 23g','370910.00','5.000','KAR','PT. LUCKY STRATEGIS','- KAWASAN NIAGA TERPADU SUDIRMAN','MT',' ',' ','2018-02-23','0','-','-','-','190201ITON','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003015','8020093742','106.805888','-6.22669478','3','50','08:00','14:00','CDE4|L300|VAN4','1.896','6096.000','BFOR','2','7','','','1','inc','ori','BUBUK CABE 24 X 50 GR','200000.00','1.000','KAR','PT. LUCKY STRATEGIS','- KAWASAN NIAGA TERPADU SUDIRMAN','MT',' ',' ','2018-02-23','0','-','-','-','191128BOEM','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003654','8020093917','106.780063114985','-6.11556138064776','2','10','07:00','14:30','CDE4|L300|VAN4','28.800','51768.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','121365.00','3.000','KAR','ICE,TK','- PASAR MUARA KARANG JL BELAKANG BL','GT',' ',' ','2018-02-19','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810003654','8020093917','106.780063114985','-6.11556138064776','2','10','07:00','14:30','CDE4|L300|VAN4','43.200','64368.000','AFTR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','161865.00','3.000','KAR','ICE,TK','- PASAR MUARA KARANG JL BELAKANG BL','GT',' ',' ','2018-02-19','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810018193','8020093314','106.878','-6.21485','2','10','07:00','14:30','CDE4|L300|VAN4','1547.000','1816600.000','AFTR','2','7','','','1','inc','ori','Menara MGRN Krim (1116) 1x15kg Ctn','12477250.00','100.000','BOX','DIAN TOKO','- RUKO PASAR ENJO  PISANGAN LAMA JA','GT',' ',' ','2018-02-17','','PISANGAN TIMUR','PULO GADUNG','JAKARTA TIMUR','190124SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810018416','8020093793','106.864921375923','-6.21624488063951','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','HIMALAYA TOKO','- GG BANTEN X NO.35 JATINEGARA','GT',' ',' ','2018-02-19','1000','BIDARACINA','JATINEGARA','JAKARTA TIMUR','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810020481','8020093702','106.789','-6.12115','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','ALIM TOKO','- A 94. HP.08121837588 PS.PLUIT','GT',' ',' ','2018-02-17','8308','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810023862','8020093513','106.796019073262','-6.19015011092152','2','10','07:00','14:30','CDE4|L300|VAN4','11.520','18569.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','128014.00','1.000','BOX','TIGA SAUDARA TK','- PASAR SLIPI LOS 15 NO. 49 JAKARTA','GT',' ',' ','2018-02-16','6446','SLIPI','PALMERAH','JAKARTA BARAT','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810027536','8020093429','106.869','-6.29533','1','10','07:00','14:30','CDE4|L300|VAN4','773.500','908300.000','AFTR','2','7','','','1','inc','ori','Menara MGRN Krim (1116) 1x15kg Ctn','6238625.00','50.000','BOX','MEKAR JAYA PLASTIK','- PASAR KRAMAT JATI  LOS AKS 082 08','GT',' ',' ','2018-02-20','0','KAMPUNG TENGAH','KRAMAT JATI','JAKARTA TIMUR','190124SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.500','832.000','AFTR','2','7','','','1','inc','ori','COLATTA FINEZA COMPOUND DARK 24 X 250 GR','32730.00','2.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190307GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.324','804.000','AFTR','2','7','','','1','inc','ori','Kecap OJS Hijau 135 ml','11155.00','2.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200912SSST','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.280','556.000','AFTR','2','7','','','1','inc','ori','Klatu Multipack 1/12/4X70ml','7063.00','4.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','1.427','1050.000','AFTR','2','7','','','1','inc','ori','M-150 ENERGY DRINK BOTTLE 150 ML','14909.00','5.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190601M150','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030429','8020093694','106.777502','-6.136809','2','10','07:00','14:30','CDE4|L300|VAN4','0.340','22944.000','AFTR','2','7','','','1','inc','ori','MEGKEJU SERBAGUNA 170 GRAM','19701.00','2.000','PC','ADI TOKO','- PS.TELUK GONG NO.22 RT.009/RW.010','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190215MEGS','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030442','8020093863','106.799556','-6.177246','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','850.000','1041950.000','BFOR','2','6','','','1','inc','ori','Filma CO (1016) 1x18L BIB','9450000.00','50.000','PC','TIMHOWAN RESTAURANT','- GD. GRAHA ANTERO JALAN TOMANG RAY','GT',' ',' ','2018-02-21','0','.27 RT. RW. KEL. TOMANG KEC. GROGOL PETA','MBURAN, JAKARTA BARAT, DKI JAKARTA','-','190717SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030442','8020093863','106.799556','-6.177246','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','850.000','1041950.000','BFOR','2','6','','','1','inc','ori','Filma CO (1016) 1x18L BIB','9450000.00','50.000','PC','TIMHOWAN RESTAURANT','- GD. GRAHA ANTERO JALAN TOMANG RAY','GT',' ',' ','2018-02-21','0','.27 RT. RW. KEL. TOMANG KEC. GROGOL PETA','MBURAN, JAKARTA BARAT, DKI JAKARTA','-','190730SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030442','8020093864','106.799556','-6.177246','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','778.500','1000250.000','BFOR','2','6','','','1','inc','ori','Filma Pusaka SHT Wht BKF 1x15Kg Ctn','10659950.00','50.000','BOX','TIMHOWAN RESTAURANT','- GD. GRAHA ANTERO JALAN TOMANG RAY','GT',' ',' ','2018-02-21','0','.27 RT. RW. KEL. TOMANG KEC. GROGOL PETA','MBURAN, JAKARTA BARAT, DKI JAKARTA','-','181001SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030473','8020093661','106.798819','-6.123549','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','LARIS TOKO','- APARTEMEN LAGUNA KIOS K1-12 KEL.','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030474','8020093657','106.7992178','-6.1254072','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','REZEKI TOKO','- APARTEMEN LAGUNA KIOS 1-28 JL. PL','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810030488','8020093651','106.78286174636','-6.13810292405873','2','10','07:00','14:30','CDE4|L300|VAN4','23.040','33680.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 12x1L Pch','261106.00','2.000','BOX','SUSIANA TOKO','- JL. TELUK GONG RAYA JL. K NO.16','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190116SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810035698','8020093649','106.773','-6.10931','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','KARTINI TOKO','- PS.MUARA ANGKE NO.97 KEL. PLUIT K','GT',' ',' ','2018-02-17','8320','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810037127','8020092799','106.86777418','-6.17983951','2','10','07:00','14:30','CDE4|L300|VAN4','0.280','556.000','AFTR','2','7','','','1','inc','ori','Klatu Multipack 1/12/4X70ml','7063.00','4.000','PC','SAHAT JAYA TOKO','- JL.CEMPAKA PUTIH TENGAH II NO.39','GT',' ',' ','2018-02-13','0','CEMPAKA PUTIH TIMUR','CEMPAKA PUTIH','JAKARTA PUSAT','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810037284','8020093811','106.868366','-6.203419','2','10','07:00','14:30','CDE4|L300|VAN4','1.680','3336.000','AFTR','2','7','','','1','inc','ori','Klatu Multipack 1/12/4X70ml','42546.00','24.000','PC','CHANDRA KUARSANI BP.','- JL. KELAPA MAS VI BLOK PA -16 NO.','GT',' ',' ','2018-02-19','','PEGANGSAAN DUA','KELAPA GADING',' JAKARTA UTARA','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810037284','8020093811','106.868366','-6.203419','2','10','07:00','14:30','CDE4|L300|VAN4','10.410','16643.000','AFTR','2','7','','','1','inc','ori','Kunci Mas CO 6x1.8L PCH','116148.00','1.000','BOX','CHANDRA KUARSANI BP.','- JL. KELAPA MAS VI BLOK PA -16 NO.','GT',' ',' ','2018-02-19','','PEGANGSAAN DUA','KELAPA GADING',' JAKARTA UTARA','190705SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810040902','8020093956','106.8933644','-6.1598625','3','30','09:00','15:30','CDD6|CDE4|L300|VAN4','192.000','345120.000','BFOR','2','6','','','1','inc','ori','New Pristine 400 ml 1 X 24','809100.00','20.000','KAR','DELTA GADING1','- JL.BOULEVARD BUKIT GADING RAYA BL','GT',' ',' ','2018-02-21','0','NO.30-34 JAKARTA UTARA','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042247','8020093648','106.775','-6.10659','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','SUKARDI H. TOKO','1 Jl. Muara Angke Blok L3','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042436','8020093645','106.77832559','-6.11589457','2','10','07:00','14:30','CDE4|L300|VAN4','9.680','22991.000','AFTR','2','7','','','1','inc','ori','Kunci Mas CO 48x200mL PCH','121311.00','1.000','BOX','MODENA TOKO','19 Jl. Pasar Muara Karang Block 22','GT',' ',' ','2018-02-17','6894','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190424SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042448','8020093264','106.804','-6.18552','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','UNTUNG JAYA TOKO','6 Jl. Kota Bambu Utara 6','GT',' ',' ','2018-02-15','0','KOTA BAMBU SELATAN','PALMERAH','JAKARTA BARAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO COCONUT','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO COCONUT','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO GRAPE','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO GRAPE','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO LYCHEE','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO LYCHEE','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190121SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','15.360','31440.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO STRAWBERRY','287158.00','2.000','KAR','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042602','8020093493','106.771','-6.21869','2','10','07:00','14:30','CDE4|L300|VAN4','0.640','1310.000','AFTR','2','7','','','1','inc','ori','MOGU-MOGU NATA DE COCO STRAWBERRY','0.00','2.000','PC','BINTANG JAYA TOKO','6 JL. SUKABUMI SELATAN  RT. 008/03','GT',' ',' ','2018-02-16','0','SUKABUMI SELATAN UDIK','KEBON JERUK','JAKARTA BARAT','190225SAPE','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042883','8020093643','106.779164766061','-6.1165472998213','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','FRANKFURTER MUARA KARANG','2A JL. MUARA KARANG BLOK A5 SELATAN','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810042884','8020093646','106.772831','-6.110847','2','10','07:00','14:30','CDE4|L300|VAN4','9.680','22991.000','AFTR','2','7','','','1','inc','ori','Kunci Mas CO 48x200mL PCH','121311.00','1.000','BOX','AL KUATSAR TOKO','1 JL. MUARA ANGKE','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190424SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810044717','8020093654','106.777','-6.10836','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','TRAGO JAYA TOKO','31 JL. MANDALA BAHARI','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045218','8020092800','106.8813559354','-6.16508297252579','2','10','07:00','14:30','CDE4|L300|VAN4','28.800','42912.000','AFTR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','107910.00','2.000','KAR','MARON JAYA TOKO','LOS DEPAN PASAR PEDONGKELAN JL. INS','GT',' ',' ','2018-02-13','','KELAPA GADING BARAT','KELAPA GADING','JAKARTA UTARA','200130SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045252','8020093876','106.823412',' -6.144251','3','10','07:00','14:30','CDE4|L300|VAN4','54.850','90720.000','AFTR','2','7','','','1','inc','ori','Zoda OWB 250 ml 1 X 24','381820.00','5.000','KAR','ALVI SNACK','32 JL. MANGGA BESAR IX, PASAR PECAH','GT',' ',' ','2018-02-21','0','TANGKI','TAMAN SARI','JAKARTA BARAT','190704KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','6.830','10800.000','AFTR','2','7','','','1','inc','ori','COCODAY 250 ML','80000.00','1.000','KAR','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','190112PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','1.320','3168.000','AFTR','2','7','','','1','inc','ori','Kecap OJS Hijau 275 ml x 24','38717.00','4.000','PC','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','200606SSST','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','0.560','1112.000','AFTR','2','7','','','1','inc','ori','Klatu Multipack 1/12/4X70ml','14210.00','8.000','PC','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045694','8020093502','106.912','-6.30597','2','10','07:00','14:30','CDE4|L300|VAN4','0.544','8920.000','AFTR','2','7','','','1','inc','ori','MEGCHEDDAR SLICE 8 SLICE 136 GR','42822.00','4.000','PAK','BAROKAH TOKO','60 JL. BAMBU APUS','GT',' ',' ','2018-02-16','0','BAMBU APUS','CIPAYUNG','JAKARTA TIMUR','180905MEGS','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810045836','8020093652','106.777','-6.11587','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','MIKRO SHOP & CELL','D.7/42 JL. MUARA KARANG','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810047792','8020093496','106.802141263851','-6.15465923893317','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','RIZKI TOKO','10 JL. DURI UTARA 99, LEMBAYUNG ( B','GT',' ',' ','2018-02-16','0','DURI SELATAN','TAMBORA','JAKARTA BARAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810048409','8020093653','106.779355928176','-6.11575564592053','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','MEGA PLASTIK TOKO','PASAR MUARA KARANG LT. 1A 64-66','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810049142','8020093650','106.741488471442','-6.10326638621398','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','WONG PIN SHIN TOKO','PASAR PEJAGALAN BLOK AKS 75','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810050049','8020093804','106.8775015','-6.17935764','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','AKIONG TOKO','10 JL.AMPERA 4','GT',' ',' ','2018-02-19','0','PADEMANGAN BARAT','PADEMANGAN','JAKARTA UTARA','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810050275','8020093789','106.865','-6.21626','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','SURYA PD TOKO','PASAR JATINEGARA BASEMENT LOS AKS 2','GT',' ',' ','2018-02-19','1000','BALI MESTER','JATINEGARA','JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810050276','8020093792','106.869803140828','-6.21604313264148','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','KAPAL BOMBER TOKO','PASAR JATINEGARA LANTAI BASEMENT BL','GT',' ',' ','2018-02-19','','BALI MESTER','JATINEGARA',' JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810052765','8020093795','106.864807460628','-6.21633160338675','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','283200.00','1.000','KAR','RATNA TOKO','7 PASAR JATINEGARA PINTU SELATAN','GT',' ',' ','2018-02-19','1000','BALI MESTER','JATINEGARA','JAKARTA TIMUR','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810052769','8020093642','106.776','-6.10423','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','172560.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','404550.00','10.000','KAR','SPBU 2414406','KOMPLEK PELELANGAN IKAN MUARA ANGKE','GT',' ',' ','2018-02-17','','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810053639','8020093921','106.9348527','-6.19062546','2','10','07:00','14:30','CDE4|L300|VAN4','548.500','907200.000','AFTR','2','7','','','1','inc','ori','Zoda OWB 250 ml 1 X 24','3703654.00','50.000','KAR','AHMI TOKO','32 JL. RAYA PENGGILINGAN ( DEPAN PE','GT',' ',' ','2018-02-19','','PENGGILINGAN','CAKUNG','JAKARTA TIMUR','190704KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810054771','8020093790','106.865','-6.21626','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','RATNA TOKO','7 PASAR JATINEGARA PINTU SELATAN','GT',' ',' ','2018-02-19','0','BALI MESTER','JATINEGARA',' JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810054773','8020093875','106.800776','-6.298731','3','10','07:00','14:30','CDE4|L300|VAN4','1800.000','2682000.000','AFTR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','6272269.00','125.000','KAR','ANISA JAYA TOKO','01 GANG MANDOR AMIN/SAMPING GERBANG','GT',' ',' ','2018-02-21','','KEMBANGAN SELATAN','KEMBANGAN','JAKARTA BARAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810058307','8020093162','106.823','-6.19122','2','10','07:00','14:30','VAN4','900.000','1509600.000','AFTR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','2301890.00','50.000','KAR','GALERI SINARMAS','Sinarmas Land Plaza - Tower 2 Lt. L','GT',' ',' ','2018-02-14','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810058307','8020093162','106.823','-6.19122','2','10','07:00','14:30','VAN4','1920.000','3451200.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','7362810.00','200.000','KAR','GALERI SINARMAS','Sinarmas Land Plaza - Tower 2 Lt. L','GT',' ',' ','2018-02-14','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810058307','8020093162','106.823','-6.19122','2','10','07:00','14:30','VAN4','720.000','1072800.000','AFTR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','2454952.00','50.000','KAR','GALERI SINARMAS','Sinarmas Land Plaza - Tower 2 Lt. L','GT',' ',' ','2018-02-14','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810062471','8020093786','106.878','-6.21485','2','10','07:00','14:30','CDE4|L300|VAN4','11.520','18569.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','128014.00','1.000','BOX','LISNA 2 TOKO','2 JL. PUJANA TIRTA','GT',' ',' ','2018-02-19','','PISANGAN TIMUR','PULO GADUNG','JAKARTA TIMUR','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810062703','8020093696','106.783','-6.13836','2','10','07:00','14:30','CDE4|L300|VAN4','5.000','12620.000','AFTR','2','7','','','1','inc','ori','PALM SUGAR 40 X 250 GR','240290.00','20.000','PC','ASENG TOKO','19 JL. B RAYA TELUK GONG','GT',' ',' ','2018-02-17','0','PEJAGALAN','PENJARINGAN','JAKARTA UTARA','190530GMKC','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810068845','8020092706','106.864743919487','-6.21618306860649','2','10','07:00','14:30','CDE4|L300|VAN4','28.800','51768.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','121365.00','3.000','KAR','BINTANG BARU TOKO','NO.240 PASAR JATI NEGARA BLOK AKS','GT',' ',' ','2018-02-13','1000','KAMPUNG MELAYU','JATINEGARA','JAKARTA TIMUR','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810074686','8020093027','106.858','-6.32174','2','10','07:00','14:30','CDE4|L300|VAN4','21.940','36288.000','AFTR','2','7','','','1','inc','ori','Zoda OWB 250 ml 1 X 24','152728.00','2.000','KAR','SRI MULYO TOKO','10 JL PERTENGAHAN','GT',' ',' ','2018-02-14','','CIJANTUNG','PASAR REBO','JAKARTA TIMUR','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810075424','8020093395','106.837','-6.17657','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','118464.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','1132800.00','4.000','KAR','PRIMKOPAD RSPAD','24 JL.ABDULRAHMAN SALEH','GT',' ',' ','2018-02-17','0','PASAR BARU','SAWAH BESAR','JAKARTA PUSAT','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810075424','8020093395','106.837','-6.17657','2','10','07:00','14:30','CDE4|L300|VAN4','96.000','118464.000','AFTR','2','7','','','1','inc','ori','GULAKU PREMIUM (PUTIH) 24 X 1KG','1132800.00','4.000','KAR','PRIMKOPAD RSPAD','24 JL.ABDULRAHMAN SALEH','GT',' ',' ','2018-02-17','0','PASAR BARU','SAWAH BESAR','JAKARTA PUSAT','190713GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810075424','8020093395','106.837','-6.17657','2','10','07:00','14:30','CDE4|L300|VAN4','60.000','74160.000','AFTR','2','7','','','1','inc','ori','GULAKU PREM (PTH) 40 X 1/2 KG','708000.00','3.000','KAR','PRIMKOPAD RSPAD','24 JL.ABDULRAHMAN SALEH','GT',' ',' ','2018-02-17','0','PASAR BARU','SAWAH BESAR','JAKARTA PUSAT','190711GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810079745','8020093794','106.869','-6.21846','2','10','07:00','14:30','CDE4|L300|VAN4','24.000','29616.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','283200.00','1.000','KAR','WEI LING','19 JL. JATINEGARA TIMUR II','GT',' ',' ','2018-02-19','0','RAWA TERATE','CAKUNG','JAKARTA TIMUR','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810096761','8020093887','106.783','-6.18179','3','10','07:00','14:30','CDE4|L300|VAN4','288.000','517680.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','1152967.00','30.000','KAR','DANIEL TOKO','11 DUKUH BARAT GANG 2 TANJUNG DUREN','GT',' ',' ','2018-02-21','','TANJUNG DUREN','GROGOL PETAMBURAN','JAKARTA BARAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810096761','8020093887','106.783','-6.18179','3','10','07:00','14:30','CDE4|L300|VAN4','288.000','429120.000','AFTR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','1025145.00','20.000','KAR','DANIEL TOKO','11 DUKUH BARAT GANG 2 TANJUNG DUREN','GT',' ',' ','2018-02-21','','TANJUNG DUREN','GROGOL PETAMBURAN','JAKARTA BARAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810098709','8020093037','106.872555','-6.345189','2','10','07:00','14:30','CDE4|L300|VAN4','36.000','60384.000','AFTR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','101182.00','2.000','KAR','PUTRA KEMBAR','BKS 48 PASAR CIBUBUR','GT',' ',' ','2018-02-14','1139','CIBUBUR','CIRACAS','JAKARTA TIMUR','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810100481','8020093970','106.758879738286','-6.20942165008926','3','10','07:00','14:30','CDE4|L300|VAN4','69.120','101040.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 12x1L Pch','775483.00','6.000','BOX','AMINAH TOKO','103 PASAR CIKINI BLOK A  RT 001 RW','GT',' ',' ','2018-02-22','','CIKINI','MENTENG','JAKARTA PUSAT','190116SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093888','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','960.000','1725600.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','3762315.00','100.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093889','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','960.000','1725600.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','3762315.00','100.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093890','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','960.000','1725600.000','AFTR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','3762315.00','100.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200103SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810101406','8020093890','106.823504','-6.189894','3','10','07:00','14:30','CDE4|L300|VAN4','72.000','107280.000','AFTR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','250891.00','5.000','KAR','GOLDEN ENERGY MINES TBK.PT','51 JL.MH.THAMRIN NO 51 SINARMAS LAN','GT',' ',' ','2018-02-21','0','GONDANGDIA','MENTENG','JAKARTA PUSAT','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','69.120','111414.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','760400.00','6.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190731SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','115.200','168400.000','AFTR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 12x1L Pch','1292473.00','10.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190116SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','104.100','166430.000','AFTR','2','7','','','1','inc','ori','Kunci Mas CO 6x1.8L PCH','1149862.00','10.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190705SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','20.800','32162.000','AFTR','2','7','','','1','inc','ori','Kunci Mas CO 12x900mL PCH','237000.00','2.000','BOX','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190130SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093271','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','43.880','72576.000','AFTR','2','7','','','1','inc','ori','Zoda OWB 250 ml 1 X 24','305456.00','4.000','KAR','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190703KMI','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5810103666','8020093272','106.820097','-6.213759','2','10','07:00','14:30','CDE4|L300|VAN4','120.000','148080.000','AFTR','2','7','','','1','inc','ori','GULAKU (KUNING) 24 X 1KG','1416000.00','5.000','KAR','ALGOLAB SOLUTION.PT','11 JL JEND SOEDIRMAN KAV.28 GEDUNG','GT',' ',' ','2018-02-16','','KARET','SETIA BUDI','JAKARTA SELATAN','190723GULA','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000061','8020093951','106.9228310','-6.2982393','2','50','08:00','14:00','CDE4|L300|VAN4','18.000','30192.000','BFOR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','65455.00','1.000','KAR','GIANT SPM JATI RAHAYU','- JLN RAYA HANKAM NO.199 BEKASI','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000256','8020093936','106.7652930','-6.2165317','2','50','07:00','14:30','CDE4|L300|VAN4','28.800','42912.000','BFOR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','139636.00','2.000','KAR','LION SUPER INDO KLAPA DUA','- JL. POS PENGUMBEN RAYA JAKARTA BA','MT',' ',' ','2018-02-21','0','-','-','-','200216SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000256','8020093936','106.7652930','-6.2165317','2','50','07:00','14:30','CDE4|L300|VAN4','28.800','51768.000','BFOR','2','7','','','1','inc','ori','New Pristine 400 ml 1 X 24','157092.00','3.000','KAR','LION SUPER INDO KLAPA DUA','- JL. POS PENGUMBEN RAYA JAKARTA BA','MT',' ',' ','2018-02-21','0','-','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000268','8020093637','106.782590','-6.221578','3','50','08:00','14:00','CDE4|L300|VAN4','9.360','36792.000','BFOR','2','7','','','1','inc','ori','HATARI SEE HONG PUFF MARGARINE 36BKS','181818.00','36.000','PC','SOGO BELLEZA SHOPPING','- JL. LETJEN SOEPONO NO.34','MT',' ',' ','2018-02-22','0','PERMATA HIJAU JAKARTA BARAT','-','-','190405ASWF','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000268','8020093637','106.782590','-6.221578','3','50','08:00','14:00','CDE4|L300|VAN4','3.705','20190.000','BFOR','2','7','','','1','inc','ori','HATARI MALKIST CHOCOLATE 30 BKS 120 GR','134182.00','30.000','PC','SOGO BELLEZA SHOPPING','- JL. LETJEN SOEPONO NO.34','MT',' ',' ','2018-02-22','0','PERMATA HIJAU JAKARTA BARAT','-','-','190717ASWF','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000288','8020093893','106.79581211','-6.20838109','3','50','08:00','14:00','CDE4|L300|VAN4','288.000','464225.000','BFOR','2','7','','','1','inc','ori','Filma Cooking Oil (0716) 6x2L Pch','3821887.00','150.000','PC','RAMAYANA (R10). PALMERAH','- RAMAYANA PALMERAH JLN PASAR PALME','MT',' ',' ','2018-02-25','0','ARTA BARAT','-','-','190813SMRT','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000351','8020093641','106.801789','-6.253165','3','50','08:00','14:00','CDE4|L300|VAN4','3.420','6240.000','BFOR','2','7','','','1','inc','ori','KLATU 70 ML','112582.00','48.000','PC','RANCH 99 DHARMAWANGSA','- DHARMAWANGSA SQUARE JL DHARMAWANG','MT',' ',' ','2018-02-25','0','PULO KEBAYORAN BARU','-','-','190412PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000351','8020093641','106.801789','-6.253165','3','50','08:00','14:00','CDE4|L300|VAN4','11.080','17136.000','BFOR','2','7','','','1','inc','ori','KLATU 200 ML','332072.00','48.000','PC','RANCH 99 DHARMAWANGSA','- DHARMAWANGSA SQUARE JL DHARMAWANG','MT',' ',' ','2018-02-25','0','PULO KEBAYORAN BARU','-','-','190620PECU','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000385','8020093942','106.943395','-6.226001','2','50','08:00','14:00','CDE4|L300|VAN4','54.000','90576.000','BFOR','2','7','','','1','inc','ori','New Pristine 1500 ml 1 X 12','196365.00','3.000','KAR','GIANT SPM PONDOK KOPI','- JL ROBUSTA NO 1 PONDOK KOPI JAKAR','MT',' ',' ','2018-02-21','0','R','-','-','200122SWTO','-');
insert into BOSNET1.dbo.TMS_PreRouteJob values('20180220_143237504','5820000385','8020093942','106.943395','-6.226001','2','50','08:00','14:00','CDE4|L300|VAN4','43.200','64368.000','BFOR','2','7','','','1','inc','ori','New Pristine 600 ml 1 X 24','209454.00','3.000','KAR','GIANT SPM PONDOK KOPI','- JL ROBUSTA NO 1 PONDOK KOPI JAKAR','MT',' ',' ','2018-02-21','0','R','-','-','200216SWTO','-');

insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9603IJ','1500.000','4000000.000','L300','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.54,0.00,0.00,'0D00000055','Adi Santoso');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9310IR','1500.000','4000000.000','L300','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.54,0.00,0.00,'0D00000173','Fajar Ramadhan');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','DelivereeL300-1','1500.000','4000000.000','L300','D312','106.909673','-6.20984','106.909673','-6.20984','10:00','22:59','EXT','2018-02-20','2018-02-20','1',0.00,0.54,0.00,0.00,'Deliveree','Deliveree');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9110TCA','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000016','Ridwan');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9112TCA','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000176','Nanang Subandi');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9557TU','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000297','Rio Aji Prasetyo');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9365TCF','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000058','Adrianus Zalukhu');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9199TCH','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000177','Muhammad Akbar Fadillah');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9604TCJ','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000054','Muharyanta');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9592TCJ','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000171','Abdullah Majid');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9586TCJ','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000298','Dadang');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9366TCF','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000032','Syahrial');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9707TCB','5500.000','12000000.000','CDD6','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,1.03,0.00,0.00,'0D00000030','Dayat Sudrajat');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9367TCF','5500.000','12000000.000','CDD6','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,1.03,0.00,0.00,'0D00000024','Supriyanto');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','B9602TW','2500.000','7000000.000','CDE4','D312','106.909673','-6.20984','106.909673','-6.20984','08:30','22:59','INT','2018-02-20','2018-02-20','1',0.00,0.79,0.00,0.00,'0D00000021','Turmadi');
insert into BOSNET1.dbo.TMS_PreRouteVehicle values('20180220_143237504','DelivereeL300-2','1500.000','4000000.000','L300','D312','106.909673','-6.20984','106.909673','-6.20984','10:00','22:59','EXT','2018-02-20','2018-02-20','1',0.00,0.54,0.00,0.00,'Deliveree','Deliveree');

insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810037127','5810037127-SAHAT JAYA TOKO',' JL.CEMPAKA PUTIH TENGAH II NO.39','0','CEMPAKA PUTIH TIMUR','KOTA JAKARTA PUSAT','CEMPAKA PUTIH','DKI JAKARTA','10520','106.86777418','-6.17983951','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810037284','5810037284-CHANDRA KUARSANI BP.','JL. KELAPA MAS VI BLOK PA -16 NO.16','','UTAN KAYU SELATAN','KOTA JAKARTA TIMUR','MATRAMAN','DKI JAKARTA','13130','106.868366','-6.203419','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810047792','5810047792-RIZKI TOKO','JL. DURI UTARA 99/10 ','0','DURI UTARA','KOTA JAKARTA BARAT','TAMBORA','DKI JAKARTA','11330','106.802141263851','-6.15465923893317','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810053639','5810053639-AHMI TOKO','32 JL. RAYA PENGGILINGAN ( DEPAN JAGAL CDAKUNG )','','PENGGILINGAN','KOTA JAKARTA TIMUR','CAKUNG','DKI JAKARTA','13910','106.9348527','-6.19062546','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810054771','5810054771-RATNA TOKO','PINTU PS. TIMUR NO.7, JATINEGARA','0','BALI MESTER','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.865','-6.21626','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810054773','5810054773-ANISA JAYA TOKO','GANG MANDOR AMIN 01 /SAMPING GERBANG','','CILANDAK BARAT','KOTA JAKARTA SELATAN','CILANDAK','DKI JAKARTA','12430','106.800776','-6.298731','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810074686','5810074686-SRI MULYO TOKO','JL. PERTENGAHAN','','CIJANTUNG','KOTA JAKARTA TIMUR','PASARREBO','DKI JAKARTA','13780','106.858','-6.32174','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820001275','5820001275-THE FOODHALL KELAPA GADING','','0','Gondangdia','Menteng','KotaJakarta Pusat','DKI Jakarta','10350','106.908626','-6.156832','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810018193','5810018193-DIAN TOKO',' RUKO PASAR ENJO  PISANGAN LAMA JAKARTA  JL BEKASI TIMUR RAYA','','CIPINANG BESAR UTARA','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.878','-6.21485','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810018236','5810018236-BAKMI PROBOLINGGO','','','Adimulya','Wanareja','Kab.Cilacap','Jawa Tengah','53265','','','Not Found');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810040902','5810040902-DELTA GADING1','','0','Kelapa Gading Barat','Kelapa Gading','KotaJakarta Utara','DKI Jakarta','14240','106.8933644','-6.1598625','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810042247','5810042247-SUKARDI H. TOKO','JL. MUARA ANGKE BLOK L3 1','','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.775','-6.10659','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810042248','5810042248-H. UDIN  TOKO','36 JL. MUARA ANGKE GANG DAMAI','','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.807','-6.13708','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810048409','5810048409-MEGA PLASTIK TOKO','PASAR MUARA KARANG LT. 1A 64-66','','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.779355928176','-6.11575564592053','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810048846','5810048846-SIMANJUNTAK TOKO','JL. JATINEGARA BARAT','1000','BALI MESTER','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.864963830119','-6.21631632318145','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810018416','5810018416-HIMALAYA TOKO','GG BANTEN X NO.35 JATINEGARA','1000','BALI MESTER','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.864921375923','-6.21624488063951','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810042436','5810042436-MODENA TOKO','19 JL. PASAR MUARA KARANG BLOCK 22','6894','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14450','106.77832559','-6.11589457','LongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810042448','5810042448-UNTUNG JAYA TOKO','6 JL. KOTA BAMBU UTARA 6','0','KOTA BAMBU UTARA','KOTA JAKARTA BARAT','PALMERAH','DKI JAKARTA','11430','106.804','-6.18552','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810042602','5810042602-BINTANG JAYA TOKO','JL. SUKABUMI SELATAN  RT. 008/03','0','SUKABUMI SELATAN','KOTA JAKARTA BARAT','KEBONJERUK','DKI JAKARTA','11510','106.771','-6.21869','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810050049','5810050049-AKIONG TOKO','JL. PULOMAS SELATAN NO.23','0','KAYU PUTIH','KOTA JAKARTA TIMUR','PULOGADUNG','DKI JAKARTA','13240','106.8775015','-6.17935764','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810049142','5810049142-WONG PIN SHIN TOKO','PASAR PEJAGALAN BLOK AKS 75','','PEKOJAN','KOTA JAKARTA BARAT','TAMBORA','DKI JAKARTA','11330','106.741488471442','-6.10326638621398','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000061','5820000061-GIANT SPM JATI RAHAYU','','0','Jatimurni','Pondok Melati','KotaBekasi','Jawa Barat','17431','106.9228310','-6.2982393','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810020481','5810020481-ALIM TOKO','PASAR PLUIT A 94. HP.08121837588','8308','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.789','-6.12115','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810058307','5810058307-GALERI SINARMAS','SINARMAS LAND PLAZA TOWER 2','0','GONDANGDIA','KOTA JAKARTA PUSAT','MENTENG','DKI JAKARTA','10330','106.823','-6.19122','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810079745','5810079745-WEI LING','JL. JATINEGARA TIMUR II','0','RAWA BUNGA','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.869','-6.21846','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810042883','5810042883-FRANKFURTER MUARA KARANG','JL. MUARA KARANG BLOK A5 SELATAN','','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.779164766061','-6.1165472998213','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810042884','5810042884-AL KUATSAR TOKO','JL. MUARA ANGKE 1','','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.772831','-6.110847','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810002520','5810002520-SUNDARI TOKO',' JL. DELIMA RAYA NO. 44 TANJUNG DUREN','','TANJUNG DUREN SELATAN','KOTA JAKARTA BARAT','GROGOLPETAMBURAN','DKI JAKARTA','11450','106.786','-6.17677','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810002694','5810002694-AA JAYA TOKO',' JL. SRENGSENG RAYA NO.19 MERUYA ','0','SRENGSENG','KOTA JAKARTA BARAT','KEMBANGAN','DKI JAKARTA','11640','106.758879738286','-6.20942165008926','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810050275','5810050275-SURYA PD TOKO','PASAR JATINEGARA BASEMENT LOS AKS 2','1000','BALI MESTER','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.865','-6.21626','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810050276','5810050276-KAPAL BOMBER TOKO','PASAR JATINEGARA LANTAI BASEMENT BL','','BALI MESTER','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.869803140828','-6.21604313264148','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000256','5820000256-LION SUPER INDO KLAPA DUA','','0','Sukabumi Selatan','Kebon Jeruk','Jakarta Barat','DKI Jakarta','11560','106.7652930','-6.2165317','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000268','5820000268-SOGO BELLEZA SHOPPING','','0','Kebon Jeruk','Kebon Jeruk','KotaJakarta Barat','DKI Jakarta','11530','106.782590','-6.221578','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000351','5820000351-RANCH 99 DHARMAWANGSA','','0','Melawai','Kebayoran Baru','KotaJakarta Selatan','DKI Jakarta','12160','106.801789','-6.253165','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000385','5820000385-GIANT SPM PONDOK KOPI','','0','Malaka Jaya','Duren Sawit','KotaJakarta Timur','DKI Jakarta','13460','106.943395','-6.226001','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000391','5820000391-LION SUPER INDO ARION.PT','','0','Jati','Pulo Gadung','KotaJakarta Timur','DKI Jakarta','13220','106.890523','-6.194205','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810002777','5810002777-ATL HW GRAHA KENCANA','','0','Karanganyar','Patikraja','Kab.Banyumas','Jawa Tengah','53171','106.773606','-6.195931','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810044717','5810044717-TRAGO JAYA TOKO','JL. MANDALA BAHARI','0','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.777','-6.10836','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D314','JAKARTA SELATAN','5810027536','5810027536-MEKAR JAYA PLASTIK','PASAR KRAMAT JATI  LOS AKS 082 08','0','KRAMAT JATI','KOTA JAKARTA TIMUR','KRAMATJATI','DKI JAKARTA','13530','106.869','-6.29533','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000288','5820000288-RAMAYANA (R10). PALMERAH','','0','Gelora','Tanah Abang','Jakarta Pusat','DKI Jakarta','10270','106.79581211','-6.20838109','LongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810002862','5810002862-MUJUR TOKO',' JL. PASAR PINTU SELATAN JATINEGAR','','BALI MESTER','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.865179256961','-6.21644319451683','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810002876','5810002876-RUSTAMIO KURNIADI','JALAN A RT 3/07 NO. 16 - KARANG ANYAR','0','CIPINANG MUARA','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.885315','-6.225855','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810002924','5810002924-DEDE TOKO',' JL. PASEBAN E.411  391-04529 BELAKANG PASAR','','PASEBAN','KOTA JAKARTA PUSAT','SENEN','DKI JAKARTA','10460','106.848669235265','-6.19223029512675','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810023862','5810023862-TIGA SAUDARA TOKO','PASAR SLIPI LOS 15 NO. 49 JAKARTA','6446','KEMANGGISAN','KOTA JAKARTA BARAT','PALMERAH','DKI JAKARTA','11430','106.796019073262','-6.19015011092152','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810030429','5810030429-ADI TOKO','JLN TELUK GONG NO.22 RT.009/RW.010','0','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.777502','-6.136809','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810030442','5810030442-TIMHOWAN RESTAURANT','','0','Kamal Muara','Penjaringan','KotaJakarta Utara','DKI Jakarta','14470','106.799556','-6.177246','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810045218','5810045218-MARON JAYA TOKO','PASAR PEDONGKELAN LOS DEPAN ','','KELAPA GADING BARAT','KOTA JAKARTA UTARA','KELAPA GADING','DKI JAKARTA','14240','106.8813559354','-6.16508297252579','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810068845','5810068845-BINTANG BARU TOKO','PASAR JATINEGARA BLOK AKS 240','1000','KAMPUNG MELAYU','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.864743919487','-6.21618306860649','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810030473','5810030473-LARIS TOKO',' APARTEMEN LAGUNA KIOS K1-12 KEL.','0','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.798819','-6.123549','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810030474','5810030474-REZEKI TOKO','APARTEMEN LAGUNA KIOS 1-28 JL. PL','','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.7992178','-6.1254072','LongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810030488','5810030488-SUSIANA TOKO',' JL. TELUK GONG RAYA NO.16','','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.78286174636','-6.13810292405873','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810045252','5810045252-ALVI SNACK','JL. MANGGA BESAR IX','0','TANGKI','KOTA JAKARTA BARAT','TAMANSARI','DKI JAKARTA','11120','106.823412',' -6.144251','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810045694','5810045694-BAROKAH TOKO','JL. BAMBU APUS NO.60','0','BAMBU APUS','KOTA JAKARTA TIMUR','CIPAYUNG','DKI JAKARTA','13890','106.912','-6.30597','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810003015','5810003015-PT. LUCKY STRATEGIS','','0','Bendungan Hilir','Tanah Abang','KotaJakarta Pusat','DKI Jakarta','10210','106.805888','-6.22669478','LongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810096761','5810096761-DANIEL TOKO','JL. DUKUH BARAT GANG 2 TANJUNG DUREN','','TANJUNG DUREN UTARA','KOTA JAKARTA BARAT','GROGOLPETAMBURAN','DKI JAKARTA','11450','106.783','-6.18179','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000403','5820000403-RAMAYANA (R38) KLENDER','','0','Malaka Jaya','Duren Sawit','Jakarta Timur','DKI Jakarta','13460','106.9030286','-6.2143285','LongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000404','5820000404-RAMAYANA R47 CIBUBUR','','0','Cibubur','Ciracas','KotaJakarta Timur','DKI Jakarta','13720','106.884342','-6.351835','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000406','5820000406-RAMAYANA S.109 ROBINSON KLENDER','','0','Duren Sawit','Duren Sawit','KotaJakarta Timur','DKI Jakarta','13440','106.902749','-6.214038','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000417','5820000417-INDOMARCO DC JKT 1 SEWA','','0','Ancol','Pademangan','KotaJakarta Utara','DKI Jakarta','14430','106.8143824','-6.1226615','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000427','5820000427-LOTTE MART KELAPA GADING','','0','Kelapa Gading Barat','Kelapa Gading','KotaJakarta Utara','DKI Jakarta','14240','106.8958025','-6.1536800','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000431','5820000431-FARMER KELAPA GADING','','0','Kelapa Gading Barat','Kelapa Gading','KotaJakarta Utara','DKI Jakarta','14240','106.908558','-6.158286','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820010001','5820010001-STARBUCKS COFFEE','','0','Kuningan Barat','Mampang Prapatan','KotaJakarta Selatan','DKI Jakarta','12710','106.821410','-6.233389','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810045836','5810045836-MIKRO SHOP & CELL','JL. MUARA KARANG D.7/42 ','0','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.777','-6.11587','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810097643','5810097643-MUBAROK, UD','1 JL. BANTARJATI','0','PULAU PARI','KAB KEPULAUAN SERIBU','KEPULAUAN SERIBU SELATAN','DKI JAKARTA','14520','106.919102436981','-6.3078890681601','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810098709','5810098709-PUTRA KEMBAR','BKS 48 PASAR CIBUBUR','1139','CIBUBUR','KOTA JAKARTA TIMUR','CIRACAS','DKI JAKARTA','13720','106.872555','-6.345189','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000491','5820000491-GIANT SPM BINTARA','','0','Bintara Jaya','Bekasi Barat','KotaBekasi','Jawa Barat','17136','106.9604016','-6.2307229','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820000507','5820000507-LION SUPER INDO JATIMAKMUR','','0','Jatimakmur','Pondok Gede','KotaBekasi','Jawa Barat','17413','106.93556783','-6.27500797','LongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810003654','5810003654-ICE TOKO','PASAR MUARA KARANG ','','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.780063114985','-6.11556138064776','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810035698','5810035698-KARTINI TOKO',' PS.MUARA ANGKE NO.97 KEL. PLUIT K','8320','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.773','-6.10931','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810101406','5810101406-GOLDEN ENERGY MINES TBK.PT','','0','Gondangdia','Menteng','KotaJakarta Pusat','DKI Jakarta','10350','106.823504','-6.189894','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820001130','5820001130-INDOMARCO GUDANG INDUK SENTUL','','0','Paledang','Bogor Tengah','Bogor','Jawa Barat','16122','106.836418','-6.521181','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810052765','5810052765-RATNA TOKO','PASAR JATINEGARA PINTU SELATAN','1000','BALI MESTER','KOTA JAKARTA TIMUR','JATINEGARA','DKI JAKARTA','13310','106.864807460628','-6.21633160338675','ImgLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810052769','5810052769-SPBU 2414406','KOMPLEK PELELANGAN IKAN MUARA ANGKE','','PLUIT','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.776','-6.10423','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810062471','5810062471-LISNA 2 TOKO','2 JL. BUJANA TIRTA','','PISANGAN TIMUR','KOTA JAKARTA TIMUR','PULOGADUNG','DKI JAKARTA','13240','106.878','-6.21485','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810075424','5810075424-PRIMKOPAD RSPAD','JL KWINI 1 , RSPAD GATOT SOEBROTO','0','SENEN','KOTA JAKARTA PUSAT','SENEN','DKI JAKARTA','10460','106.837','-6.17657','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810100481','5810100481-AMINAH TOKO','','','Cikini','Menteng','KotaJakarta Pusat','DKI Jakarta','10330','','','Not Found');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810062703','5810062703-ASENG TOKO','JL. B RAYA TELUK GONG','0','PEJAGALAN','KOTA JAKARTA UTARA','PENJARINGAN','DKI JAKARTA','14470','106.783','-6.13836','GLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5820001054','5820001054-GIANT SPM BLOK M','','0','Kramat Pela','Kebayoran Baru','KotaJakarta Selatan','DKI Jakarta','12130','106.797494','-6.243676','RLongLat');
insert into [BOSNET1].[dbo].[TMS_CustLongLat] values('D312','JAKARTA 1','5810103666','5810103666-ALGOLAB SOLUTION.PT','','','Karet','Setia Budi','KotaJakarta Selatan','DKI Jakarta','12920','106.820097','-6.213759','SFAUtility LongLat');

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
