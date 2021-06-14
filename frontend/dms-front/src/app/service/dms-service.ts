import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DmsService {

  constructor(private httpClient: HttpClient) { }

  url = "http://localhost:8080/docs/";

  getData(url: string): Observable<any> {
    return this.httpClient.get<any>(url);
  }

  getAllDocuments(): Observable<any> {
    return this.httpClient.get<any>(this.url + 'get-all-documents');
  }




}