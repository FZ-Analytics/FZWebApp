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
