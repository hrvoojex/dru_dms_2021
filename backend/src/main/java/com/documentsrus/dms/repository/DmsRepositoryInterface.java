package com.documentsrus.dms.repository;

import com.documentsrus.dms.model.Document;

import java.sql.SQLException;

public interface DmsRepositoryInterface {
    Document getDocument(int id) throws Exception;

    void deleteDocument(int id) throws Exception;

    void insertDocument(String name, String type, String description, String path) throws Exception;

}
