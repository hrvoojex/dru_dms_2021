package com.documentsrus.dms.service;

import com.documentsrus.dms.model.Document;

import java.util.List;

public interface DmsServiceInterface {
    public Document getDocument(int id) throws Exception;

    public void deleteDocument(int id) throws Exception;

    void insertDocument(String name, String type, String description, String path) throws Exception;

    Document updateDocument(int id, String name, String type, String description, String path) throws Exception;

    List<Document> getAllDocuments() throws Exception;

    void insertBytesDocument(String name, String type, String description, byte[] bytes) throws Exception;

}
