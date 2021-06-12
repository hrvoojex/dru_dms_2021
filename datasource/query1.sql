select
    'data source=' + @@servername +
    ';initial catalog=' + db_name() +
    case type_desc
        when 'WINDOWS_LOGIN' 
            then ';trusted_connection=true'
        else
            ';user id=' + suser_name() + ';password=<<YourPassword>>'
    end
    as ConnectionString
from sys.server_principals
where name = suser_name()

EXEC xp_ReadErrorLog 0, 1, N'Server is listening on', N'any', NULL, NULL, 'DESC'
 
GO

--===
DECLARE @portNumber NVARCHAR(10);
EXEC xp_instance_regread 
     @rootkey = 'HKEY_LOCAL_MACHINE', 
     @key = 'Software\Microsoft\Microsoft SQL Server\MSSQLServer\SuperSocketNetLib\Tcp\IpAll', 
     @value_name = 'TcpPort', 
     @value = @portNumber OUTPUT;
SELECT [Port Number] = @portNumber;
GO