package com.documentsrus.dms.service;

import com.documentsrus.dms.model.Document;

public interface DmsServiceInterface {
    public Document getDocument(int id) throws Exception;

    public void deleteDocument(int id) throws Exception;

    void insertDocument(String name, String type, String description, String path) throws Exception;

    Document updateDocument(int id, String name, String type, String description, String path) throws Exception;

}
