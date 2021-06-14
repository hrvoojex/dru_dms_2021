package com.documentsrus.dms.controller;

import com.documentsrus.dms.model.Document;
import com.documentsrus.dms.model.ServiceResult;
import com.documentsrus.dms.service.DmsServiceInterface;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DmsController {

    @Autowired
    DmsServiceInterface dmsService;

    @GetMapping("/get-document")
    public ResponseEntity getDocument(@RequestParam("id") int id) {
        ServiceResult result = new ServiceResult();
        Document document = new Document();
        try {
            document = dmsService.getDocument(id);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            result.setMessage("Could not get document with id: " + id + " Resultset list is empty");
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error getting document with id: " + id);
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.setMessage("Document available");
        result.setResult(document);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/delete-document")
    public ResponseEntity deleteDocument(@RequestParam("id") int id) {
        ServiceResult result = new ServiceResult();

        try {
            dmsService.deleteDocument(id);
        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error deleting document with id: " + id);
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.setMessage("Document deleted");
        result.setResult(null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
