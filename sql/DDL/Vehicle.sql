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
