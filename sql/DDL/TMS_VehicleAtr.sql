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

//26-02-2018
alter table bosnet1.dbo.TMS_VehicleAtr
add agent_priority int;

//27-02-2018
alter table bosnet1.dbo.TMS_VehicleAtr
add max_cust int;