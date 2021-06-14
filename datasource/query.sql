--=======
USE DRU_DMS_2021
GO;
exec dbo.sp_insertDocument @name = 'ime_moga_filea', @type = 'txt', @description = 'moj opis', @path = 'D:\Text1.txt'
GO

select * from dbo.Document

USE DRU_DMS_2021;
GO

exec dbo.sp_deleteDocument @id_Document = 1;
GO;

select * from dbo.Document
exec dbo.sp_readDocument @id = 1

exec dbo.sp_updateDocument 
	@id = 3, 
	@description = '555',
	@type = '555',
	--@name = '555',
	@path = 'D:\Text2.txt'

exec dbo.sp_deleteDocument @id = 9

select * from dbo.Document

GO;

select * from dbo.Document

DECLARE @name NVARCHAR(100) --= 'updateno 333';
DECLARE @name_Document NVARCHAR(13) = 'name_Document'
DECLARE @sql NVARCHAR(1000)
DECLARE @type NVARCHAR(30) = 'updateno 333'
DECLARE @description NVARCHAR(200) = 'updatano 333'
DECLARE @id INT = 2
DECLARE @path NVARCHAR(200) --= 'D:\Text1.txt'
SET @sql = N'UPDATE dbo.Document
		SET name_Document = ''' + COALESCE(@name, @name_Document) + ''',' +
		'type_Document = ''' + COALESCE(@type, 'type_Document') + ''',' +
		'description_Document = ''' + COALESCE(@description, 'description_Document') + ''',' +
		'timestamp_Document = SYSDATETIME()' +
		' WHERE id_Document = ' + CAST(@id AS nvarchar(30));
PRINT @sql
EXEC sp_executesql @sql;
select * from dbo.Document

UPDATE dbo.Document
		SET name_Document = 'updateno 333' WHERE id_Document = 2

SELECT file_Document.BulkColumn  FROM OPENROWSET(BULK N'D:\Text3.txt', SINGLE_BLOB) AS file_Document

--=================================
Create Table #temp
(
   File_Exists  bit,
   File_is_Directory int,
   Parent_Directory_Exists bit
)

INSERT INTO #temp
EXEC Master.dbo.xp_fileexist N'D:\Text3.txt'

--1 means exists while 0 means not exists
IF 1=(SELECT File_Exists FROM #temp)
BEGIN
   UPDATE dbo.Document
   SET file_Document = (SELECT file_Document.BulkColumn  FROM OPENROWSET(BULK N'D:\Text3.txt', SINGLE_BLOB) AS file_Document) 
   WHERE id_Document = 2

   SELECT * FROM dbo.Document
END
ELSE
BEGIN
   SELECT * FROM dbo.Document
END

DROP table #temp

--=====
DECLARE @path NVARCHAR(200) --= 'D:\Text2.txt'
DECLARE @id INT = 4
Create Table #temp
	(
	   File_Exists  bit,
	   File_is_Directory int,
	   Parent_Directory_Exists bit
	)

	DECLARE @filePath NVARCHAR(200) = 'N''' + @path + ''''
	PRINT @filePath
	INSERT INTO #temp
	EXEC Master.dbo.xp_fileexist @filePath

	--1 means exists while 0 means not exists
	IF 1=(SELECT File_Exists FROM #temp)
	DECLARE @sql NVARCHAR(1000)
	BEGIN
	   SET @sql = 'UPDATE dbo.Document ' +
	   'SET file_Document = (SELECT file_Document.BulkColumn  FROM OPENROWSET(BULK ' + @filePath + ', SINGLE_BLOB) AS file_Document) ' +
	   'WHERE id_Document = ' + CAST(@id AS nvarchar(10));

	   PRINT @sql
	   EXEC sp_executesql @sql
	END

	DROP table #temp

	select * from dbo.Document