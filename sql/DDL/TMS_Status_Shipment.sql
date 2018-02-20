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
