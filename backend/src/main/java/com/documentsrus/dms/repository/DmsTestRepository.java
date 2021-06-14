package com.documentsrus.dms.repository;

import com.documentsrus.dms.model.Document;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.List;

@Repository
public class DmsTestRepository implements DmsRepositoryInterface {
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
        if (rs.next() == false) {
            System.out.println(String.format("There is no document with ID: %d", id));
            throw new Exception("There is no document with ID: " + id);
        } else {
            do {
                document = new Document();
                document.setId(rs.getInt("id_Document"));
                document.setName(rs.getString("name_Document"));
                document.setType(rs.getString("type_Document"));
                document.setDescription(rs.getString("description_Document"));
                document.setDocument(rs.getBytes("file_Document"));

                System.out.println("Document name: " + document.getName());
            } while (rs.next());
        }

        rs.close();
        con.close();

        return document;
    }

    @Override
    public void deleteDocument(int id) throws Exception {

    }

    @Override
    public void insertDocument(String name, String type, String description, String path) throws Exception {

    }

    @Override
    public Document updateDocument(int id, String name, String type, String description, String path) throws Exception {
        return null;
    }

    @Override
    public List<Document> getAllDocuments() throws Exception {
        return null;
    }
}
