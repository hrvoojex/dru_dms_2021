-- ==============================================================
-- Opis: Stora za ćitanje dokumenta
-- Autor: Hrvoje Topić
-- Prva verzija: 11.06.2021.
-- Promjene:  
--
-- ==============================================================

CREATE PROCEDURE [dbo].[sp_readDocument] 
    @id INT
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
	WHERE d.id_Document = @id

	IF @@ERROR <> 0
        BEGIN
            GOTO Greska;
		END;

    RETURN 0;

    Greska:
		RETURN 1;
END;
GO

