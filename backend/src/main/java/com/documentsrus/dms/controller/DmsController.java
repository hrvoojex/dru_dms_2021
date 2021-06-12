package com.documentsrus.dms.controller;

import com.documentsrus.dms.model.Document;
import com.documentsrus.dms.service.DmsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/docs")
public class DmsController {

    @Autowired
    DmsServiceInterface dmsService;

    @GetMapping("/get-document")
    public ResponseEntity<Document> getDocument(@RequestParam("id") int id) {
        try {
            Document document = dmsService.getDocument(id);
            return new ResponseEntity<>(
                    document,
                    HttpStatus.OK
            );
        } catch (Exception e){
            return new ResponseEntity<>(
                    new Document(),

                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
