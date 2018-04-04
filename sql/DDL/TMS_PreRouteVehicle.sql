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

//26-02-2018
alter table bosnet1.dbo.TMS_PreRouteVehicle
add agent_priority int;

//27-02-2018
alter table bosnet1.dbo.TMS_PreRouteVehicle
add max_cust int;

