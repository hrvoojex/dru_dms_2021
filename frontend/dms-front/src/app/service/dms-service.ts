import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DmsService {

  constructor(private httpClient: HttpClient) { }

  url = "http://localhost:8080/docs";

  getData(url: string): Observable<any> {
    return this.httpClient.get<any>(url);
  }

  getAllDocuments(): Observable<any> {
    return this.httpClient.get<any>(this.url + '/get-all-documents');
  }

  insertBytesDocument(file: File, description: string): Observable<any> {
    let formData = new FormData();
    let reqJson: any = {};
    reqJson["description"] = description;

    formData.append("file", file);
    formData.append('info', JSON.stringify(reqJson));

    return this.httpClient.post(this.url + '/insert-bytes-document', formData);
  }

  deleteDocument(id: number): Observable<any> {
    return this.httpClient.get<any>(this.url + '/delete-document?id=' + id);
  }

  updateBytesDocument(file: File, description: string, name: string, id: number, type: string): Observable<any> {
    let formData = new FormData();
    let reqJson: any = {};
    reqJson["description"] = description;
    reqJson["name"] = name;
    reqJson["id"] = id;
    reqJson["type"] = type;

    formData.append("file", file);
    formData.append('info', JSON.stringify(reqJson));

    return this.httpClient.post(this.url + '/update-bytes-document', formData);
  }

}