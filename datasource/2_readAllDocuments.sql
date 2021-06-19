-- ==============================================================
-- Opis: Stora za čitanje svih dokumenta
-- Autor: Hrvoje Topić
-- Prva verzija: 14.06.2021.
-- Promjene:  
--
-- ==============================================================

CREATE PROCEDURE [dbo].[sp_readAllDocuments] 
AS
BEGIN
    SET NOCOUNT ON;

	SELECT 
		d.id_Document
	   ,d.name_Document
	   ,d.type_Document
	   ,d.description_Document
	   ,d.file_Document
	FROM dbo.Document AS d

	IF @@ERROR <> 0
        BEGIN
            GOTO Greska;
		END;

    RETURN 0;

    Greska:
		RETURN 1;
END;
GO

