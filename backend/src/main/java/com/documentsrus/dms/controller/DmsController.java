package com.documentsrus.dms.controller;

import com.documentsrus.dms.model.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@RequestMapping("/docs")
public class DmsController {

    @GetMapping("/get-document")
    public Document getDocument(@RequestParam("id") int id) throws SQLException {
 /*       Connection con = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;";
            con = DriverManager.getConnection(url, "dbuser", "dbuser");

            String SQL = "{exec DRU_DMS_2021.dbo.sp_readDocument(?)}";

            CallableStatement cs = con.prepareCall(SQL);

            cs.setInt(1, 1);

            rs = cs.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
        }*/

        //return null;

        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=DRU_DMS_2021;instance=MSSQLSERVER;";
        String user = "dbuser";
        String pass = "dbuser";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


            Connection myConn = DriverManager.getConnection(dbURL, user, pass);
            try {
                Statement myStmt = myConn.createStatement();

                try {
                    ResultSet myRs = myStmt.executeQuery("Select * from dbo.Document");

                    while (myRs.next())
                    {
                        System.out.println(myRs.getString(1));
                        System.out.println(myRs.getString(2));
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Error with query");
                }
            }

            catch (Exception e)
            {
                System.out.println("Error connecting to database");
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

}
