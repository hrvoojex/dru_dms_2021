-- ==============================================================
-- Opis: Stora za brisanje dokumenta
-- Autor: Hrvoje Topić
-- Prva verzija: 09.06.2021.
-- Promjene:  
--
-- ==============================================================

CREATE PROCEDURE [dbo].[sp_deleteDocument] 
    @id_Document INT
AS
BEGIN
    SET NOCOUNT ON;

    IF @id_Document IS NULL
        BEGIN
            RAISERROR('Parametri ne smiju biti prazni!',16,1);
            GOTO greska;
		END;

	BEGIN TRANSACTION;

	DELETE FROM dbo.Document WHERE id_Document = @id_Document;

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

