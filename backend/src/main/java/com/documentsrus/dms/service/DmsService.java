package com.documentsrus.dms.service;

import com.documentsrus.dms.model.Document;
import com.documentsrus.dms.repository.DmsRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsService implements DmsServiceInterface {

    @Autowired
    //DmsRepositoryInterface dmsRepository;
    DmsRepositoryInterface dmsTestRepository;


    @Override
    public Document getDocument(int id) throws Exception {
        return dmsTestRepository.getDocument(id);
    }
}
