package com.documentsrus.dms.repository;

import com.documentsrus.dms.model.Document;

import java.sql.SQLException;

public interface DmsRepositoryInterface {
    Document getDocument(int id) throws Exception;

    void deleteDocument(int id) throws Exception;

}
