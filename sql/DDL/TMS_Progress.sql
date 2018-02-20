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
