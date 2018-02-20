USE [BOSNET1]
GO
/****** Object:  StoredProcedure [dbo].[TMS_GetCustLongLat]    Script Date: 19/02/2018 15:05:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER Procedure [dbo].[TMS_GetCustLongLat] --'D314'

@Branch Varchar(10)

as Begin


DELETE FROM TMS_CustLongLat
WHERE BranchId = CASE WHEN @Branch  = '' THEN BranchId ELSE @Branch END

INSERT INTO TMS_CustLongLat
SELECT 
	BranchId,
	BranchName,
	CustId,
	CustName,
	Address = CASE WHEN LEFT(Address, 3) = ' - ' AND LEN(Address) >= 3 THEN RIGHT(Address, LEN(Address) - 3)
					WHEN LEFT(Address, 2) = '- ' AND LEN(Address) >= 2 THEN RIGHT(Address, LEN(Address) - 2)
					WHEN LEFT(Address, 2) = ' -' AND LEN(Address) >= 2 THEN RIGHT(Address, LEN(Address) - 2)
					WHEN LEFT(Address, 1) = '-' AND LEN(Address) >= 1 THEN RIGHT(Address, LEN(Address) - 1)
					ELSE Address END,
	MarketId,
	SubDistrict,
	District,
	City,
	Province,
	PostalCode,
	Long,
	Lat,
	Source
FROM
(
	SELECT DISTINCT
		BranchId = S.WorkplaceID,
		BranchName = SalOffName,
		CustId = CustId,
		CustName = REPLACE(REPLACE(REPLACE(REPLACE(CustName, char(9), ' '), '"', ' '), char(10), ' '), char(13), ' '), 
		Address = CASE WHEN BranchValidated = 1 AND S.Address IS NOT NULL THEN REPLACE(REPLACE(REPLACE(REPLACE(S.Address, char(9), ' '), '"', ' '), char(10), ' '), char(13), ' ') ELSE '-' END,
		MarketId = M.MarketId,
		SubDistrict = CASE WHEN BranchValidated = 1 AND VI_Name IS NOT NULL THEN VI_Name ELSE Kelurahan END,
		District = CASE WHEN BranchValidated = 1 AND DI_Name IS NOT NULL THEN DI_Name ELSE District END,
		City = CASE WHEN BranchValidated = 1 AND SU_Name IS NOT NULL THEN SU_Name ELSE City END,
		Province = CASE WHEN BranchValidated = 1 AND A4.PR_Name IS NOT NULL THEN A4.PR_Name ELSE S.Province END,
		PostalCode = CASE WHEN BranchValidated = 1 AND mZipCode IS NOT NULL THEN mZipCode ELSE S.ZipCode END,
		Long = 
			CASE WHEN ImgLongLatValidated = 1 THEN CASE WHEN ImgLongitude <> '' THEN ImgLongitude ELSE S.Longitude END
				ELSE CASE WHEN GLongLatValidated = 1 THEN GLongitude
					ELSE CASE WHEN RLongitude IS NOT NULL AND RLongitude <> '' THEN RLongitude
						ELSE CASE WHEN S.Longitude IS NOT NULL AND S.Longitude <> 'n/a' AND S.Longitude <> '0' AND S.Longitude <> '' THEN S.Longitude
							ELSE '' END
						END
					END
				END,
		Lat = CASE WHEN ImgLongLatValidated = 1 THEN CASE WHEN ImgLatitude <> '' THEN ImgLatitude ELSE S.Latitude END 
				ELSE CASE WHEN GLongLatValidated = 1 THEN GLatitude
					ELSE CASE WHEN RLatitude IS NOT NULL AND RLatitude <> '' THEN RLatitude
						ELSE CASE WHEN S.Latitude IS NOT NULL AND S.Latitude <> 'n/a' AND S.Latitude <> '0' AND S.Latitude <> '' THEN S.Latitude
							ELSE '' END
						END
					END
				END,
		Source = CASE WHEN ImgLongLatValidated = 1 THEN CASE WHEN ImgLatitude <> '' THEN 'ImgLongLat' ELSE 'LongLat' END  
				ELSE CASE WHEN GLongLatValidated = 1 THEN 'GLongLat'
					ELSE CASE WHEN RLatitude IS NOT NULL AND RLatitude <> '' THEN 'RLongLat'
						ELSE CASE WHEN S.Latitude IS NOT NULL AND S.Latitude <> 'n/a' AND S.Latitude <> '0' AND S.Latitude <> '' THEN 'SFAUtility LongLat'
							ELSE 'Not Found' END
						END
					END
				END
	FROM SysUtil.SFAUtility.dbo.SysCustomer S
		LEFT JOIN SysUtil.IBACONSOL.dbo.SALES_OFFICE BR
	ON S.WorkplaceId = BR.SalOffCode COLLATE DATABASE_DEFAULT
		Left Join SysUtil.DATA.DBO.Area_Administration A4 
	ON S.PR_ID = A4.PR_ID and S.DI_ID = A4.DI_ID and S.SU_ID = A4.SU_ID and S.VI_ID = A4.VI_ID
		Left Join 
	(SELECT DISTINCT PR_ID, PR_NAME FROM SysUtil.DATA.DBO.Area_Administration) A5 
	ON S.PR_ID = A5.PR_ID
	LEFT JOIN SysUtil.SFAUtility.dbo.SysCustMarket M
	ON S.MarketId = M.MarketId
	WHERE 0=0
		and S.WorkplaceId = CASE WHEN @Branch  = '' THEN S.WorkplaceID ELSE @Branch END
		--AND B.Customer_Order_Block IS NULL
		--and S.Deletion <> 1
) DATA

END
