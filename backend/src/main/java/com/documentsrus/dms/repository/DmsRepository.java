package com.documentsrus.dms.repository;

import com.documentsrus.dms.model.Document;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Repository
public class DmsRepository implements DmsRepositoryInterface {
    public Document getDocument(int id) throws Exception {
        Connection con = null;
        ResultSet rs = null;
        Document document = null;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=DRU_DMS_2021;instance=MSSQLSERVER;";
        con = DriverManager.getConnection(url, "dbuser", "dbuser");

        String SQL = "{call dbo.sp_readDocument(?)}";

        CallableStatement cs = con.prepareCall(SQL);
        cs.setInt(1, id);

        rs = cs.executeQuery();
        int myId = rs.getInt("id_Document");
        if (rs.getInt("id_Document") == 0) {
            throw new Exception(String.format("There is no document with ID: %d", id));
        }
        document = new Document();
        while(rs.next()){
            document.setId(rs.getInt("id_Document"));
            document.setName(rs.getString("name_Document"));
            document.setType(rs.getString("type_Document"));
            document.setDescription(rs.getString("description_Document"));
            document.setDocument(rs.getBytes("file_Document"));

            System.out.println(document.toString());
        }

        rs.close();
        con.close();

        return document;
    }
}
