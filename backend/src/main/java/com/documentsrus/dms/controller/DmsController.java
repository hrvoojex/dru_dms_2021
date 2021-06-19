package com.documentsrus.dms.controller;

import com.documentsrus.dms.model.Document;
import com.documentsrus.dms.model.ServiceResult;
import com.documentsrus.dms.service.DmsServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    @PostMapping(value = "/insert-document", consumes = "application/json")
    public ResponseEntity insertDocument(@RequestBody Document doc) {
        ServiceResult result = new ServiceResult();

        try {
            dmsService.insertDocument(doc.getName(), doc.getType(), doc.getDescription(), doc.getPath());
        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error inserting document");
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.setMessage("Document inserted");
        result.setResult(null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/update-document", consumes = "application/json")
    public ResponseEntity updateDocument(@RequestBody Document doc) {
        ServiceResult result = new ServiceResult();
        Document document = new Document();

        try {
            document = dmsService.updateDocument(doc.getId(), doc.getName(), doc.getType(), doc.getDescription(), doc.getPath());
        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error updating document");
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.setMessage("Document updated");
        result.setResult(document);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-all-documents")
    public ResponseEntity getDocument() {
        ServiceResult result = new ServiceResult();
        List<Document> documents = null;
        try {
            documents = dmsService.getAllDocuments();
        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error getting all documents");
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.setMessage("Documents available");
        result.setResult(documents);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/insert-bytes-document")
    public ResponseEntity insertBytesDocument(@RequestParam("file") MultipartFile file, @RequestParam("info") String info) {
        ServiceResult result = new ServiceResult();

        try {
            if (file.isEmpty()) {
                result.setMessage("No file attached. Error inserting document with bytes.");
                result.setResult(null);
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }

            byte[] bytes = file.getBytes();
            JSONObject json = new JSONObject(info);
            String description = json.getString("description");
            dmsService.insertBytesDocument(
                    file.getOriginalFilename(), file.getContentType(), description, bytes);

        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error inserting document with bytes");
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.setMessage("Document inserted");
        result.setResult(null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/update-bytes-document")
    public ResponseEntity updateBytesDocument(@RequestParam(value="file",  required=false) MultipartFile file,
                                              @RequestParam(value="info",  required=false) String info) {
        ServiceResult result = new ServiceResult();
        int id = 0;
        String description = "";
        String name = "";
        byte[] bytes = null;
        try {
            if (file != null) {
                bytes = file.getBytes();
            }
            if (!info.isEmpty()) {
                JSONObject json = new JSONObject(info);
                description = json.getString("description");
                name = json.getString("name");
                id = json.getInt("id");
            }

            dmsService.updateBytesDocument(
                    id, name, file.getContentType(), description, bytes);

        } catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error inserting document with bytes");
            result.setResult(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.setMessage("Document inserted");
        result.setResult(null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
