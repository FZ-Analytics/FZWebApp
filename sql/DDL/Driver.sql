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