-- ==============================================================
-- Opis: Stora za brisanje dokumenta
-- Autor: Hrvoje Topić
-- Prva verzija: 09.06.2021.
-- Promjene:  
--
-- ==============================================================

CREATE PROCEDURE [dbo].[sp_deleteDocument] 
    @id INT
AS
BEGIN
    SET NOCOUNT ON;

    IF @id IS NULL
        BEGIN
            RAISERROR('Parametri ne smiju biti prazni!',16,1);
            GOTO greska;
		END;

	IF (SELECT id_Document FROM dbo.Document WHERE id_Document = @id) IS NULL
        BEGIN
            RAISERROR('U bazi nema dokumenta s tim id-jem', @id,16,1);
            GOTO greska;
		END;

	BEGIN TRANSACTION;

	DELETE FROM dbo.Document WHERE id_Document = @id;

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

