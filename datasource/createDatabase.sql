
USE MASTER
GO 

DROP DATABASE IF EXISTS DRU_DMS_2021

CREATE DATABASE DRU_DMS_2021
GO

USE DRU_DMS_2021
GO

CREATE TABLE dbo.Document (
	id_Document           INT not null PRIMARY KEY IDENTITY(1,1),
	name_Document         NVARCHAR(100) NOT NULL,
	type_Document         NVARCHAR(30) NOT NULL,
	description_Document  NVARCHAR(max) NOT NULL,
	timestamp_Document    DATETIME2(3) NOT NULL,
	file_Document         VARBINARY(max) NOT NULL
)
GO

