-- ==============================================================
-- Opis: Stora za update dokumenta
-- Autor: Hrvoje Topić
-- Prva verzija: 18.06.2021.
-- Promjene: 
--           
-- ==============================================================

CREATE PROCEDURE [dbo].[sp_updateBytesDocument] 
	@id          INT
   ,@name        NVARCHAR(100) = NULL
   ,@type        NVARCHAR(30) = NULL
   ,@description NVARCHAR(MAX) = NULL
   ,@bytes       VARBINARY(MAX) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @timestamp DATETIME2(3) = SYSDATETIME()
	   
	BEGIN TRANSACTION;
	
	UPDATE dbo.Document
		SET name_Document = ISNULL(@name, name_Document)
		   ,type_Document = ISNULL(@type, type_Document)
		   ,description_Document = ISNULL(@description, description_Document)
		   ,file_Document = ISNULL(@bytes, file_Document)
		   ,timestamp_Document = SYSDATETIME()
		WHERE id_Document = @id


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

