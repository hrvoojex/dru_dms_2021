-- ==============================================================
-- Opis: Stora za update dokumenta
-- Autor: Hrvoje Topić
-- Prva verzija: 09.06.2021.
-- Promjene: 11.06.2021.
--           Ako nije predan file, ostaje stari
-- ==============================================================

CREATE PROCEDURE [dbo].[sp_updateDocument] 
	@id          INT
   ,@name        NVARCHAR(100) = NULL
   ,@type        NVARCHAR(30) = NULL
   ,@description NVARCHAR(max) = NULL
   ,@path        NVARCHAR(200) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @timestamp DATETIME2(3) = SYSDATETIME()
	   
	BEGIN TRANSACTION;
	
	UPDATE dbo.Document
		SET name_Document = ISNULL(@name, name_Document)
		   ,type_Document = ISNULL(@type, type_Document)
		   ,description_Document = ISNULL(@description, description_Document)
		   ,timestamp_Document = SYSDATETIME()
		WHERE id_Document = @id

	-- update file if it exists, else leave the old one
	Create Table #temp
	(
	   File_Exists  bit,
	   File_is_Directory int,
	   Parent_Directory_Exists bit
	)

	INSERT INTO #temp
	EXEC Master.dbo.xp_fileexist @path

	--1 means exists while 0 means not exists
	IF 1=(SELECT File_Exists FROM #temp)
	BEGIN
	   DECLARE @sql NVARCHAR(1000)
	   SET @sql = 'UPDATE dbo.Document ' +
	   'SET file_Document = (SELECT file_Document.BulkColumn  FROM OPENROWSET(BULK N''' + @path + ''', SINGLE_BLOB) AS file_Document) ' +
	   'WHERE id_Document = ' + CAST(@id AS nvarchar(10));

	   EXEC sp_executesql @sql
	END
	ELSE
	BEGIN
		PRINT 'NO FILE: ' + @path
	END

	DROP table #temp

    IF @@ERROR <> 0
        BEGIN
            GOTO Greska;
		END;

    COMMIT TRANSACTION;
    RETURN 0;

    greska:
    IF @@TRANCOUNT > 0
        BEGIN
			PRINT 'GREŠKA ...ROLBACK TRAN'
            ROLLBACK TRANSACTION;
    END;
    RETURN 1;
END;
GO

