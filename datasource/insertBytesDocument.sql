-- ==============================================================
-- Opis: Stora za insert dokumenta
-- Autor: Hrvoje Topić
-- Prva verzija: 15.06.2021.
-- Promjene:  
--
-- ==============================================================

CREATE PROCEDURE [dbo].[sp_insertBytesDocument] 
    @name        NVARCHAR(100)
   ,@type        NVARCHAR(30)
   ,@description NVARCHAR(max)
   ,@bytes       VARBINARY(MAX)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE 
		@timestamp DATETIME2(3) = SYSDATETIME()
	   ,@sql NVARCHAR(1000)

    IF @name IS NULL OR @type IS NULL OR @description IS NULL OR @bytes IS NULL
        BEGIN
            RAISERROR('Parametri ne smiju biti prazni!',16,1);
            GOTO greska;
		END;

	BEGIN TRANSACTION;

	INSERT INTO Document(name_Document, type_Document, description_Document, timestamp_Document, file_Document)
		SELECT @name AS name_Document,
			@type AS type_Document,
			@description AS description_Document,
			SYSDATETIME() AS timestamp_Document,
			@bytes AS file_Document
	
	EXEC sp_executesql @sql;

    IF @@ERROR <> 0
        BEGIN
            GOTO Greska;
		END;

    COMMIT TRANSACTION;
    RETURN 0;

    greska:
    IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION;
    END;
    RETURN 1;
END;
GO

