## SQL Server Docker Setup
### 1. Image from Microsoft Artifact Registry
```
docker pull mcr.microsoft.com/mssql/server:2022-latest
```
### 2. Running the image <hint></hint>
```
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=<YourStrong@Passw0rd>" \
   -p 1433:1433 --name mssql_container --hostname mssql_container \
   -d mcr.microsoft.com/mssql/server:2022-latest
```

### 3. Check on the new container
```
docker ps -a
```

### 4. Now you can log in with credentials
#### If you are using Azure Data Studio, make sure you have enabled the Preview-Features Option that will be helpful if you are planning to create Database, Schema, Table, User, Login, etc. by using the application.
```
Server: localhost:1433
Authentication Type: SQL Login
Username: SA
Password: <YourStrong@Passw0rd>
```

**NOTE:** It is better to use another user on which the application will access the database.  

### 5. Query for the DevUser
```
CREATE LOGIN DevUser   
    WITH PASSWORD = 'MyStrongPassword(1)';  
GO 

ALTER SERVER ROLE [##MS_DatabaseConnector##] ADD MEMBER [DevUser]
GO

ALTER SERVER ROLE [##MS_DatabaseManager##] ADD MEMBER [DevUser]
GO
```
