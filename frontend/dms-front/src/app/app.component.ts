import { IResultSet, IDocumentResultSet } from './data/IDocument';
import { DmsService } from './service/dms-service';
import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DMS Front';
  resultSet: IResultSet = null;
  documentArray: IDocumentResultSet[] = [];
  displayedColumns: string[] = ['index','name', 'description'];
  dataSource: MatTableDataSource<IDocumentResultSet>;
  countDocuments: number = 0;
  message = '';

  /* mockDataSource: IDocumentResultSet[] = [
    {"id":1,"name":"postman update 1","type":"txt.txt","description":"opis text file novi","document":"VGhpcyBpcyBhIGRlbW8gdGV4dCBmaWxlLg==","path":null},
    {"id":2,"name":"ime_moga_filea","type":"txt","description":"moj opis","document":"VGhpcyBpcyBhIGRlbW8gdGV4dCBmaWxlLg==","path":null},
    {"id":3,"name":"ime_moga_filea","type":"txt","description":"moj opis","document":"VGhpcyBpcyBhIGRlbW8gdGV4dCBmaWxlLg==","path":null},
    {"id":4,"name":"novo novcato ime json","type":"txt","description":"opis text file","document":"VGhpcyBpcyBhIGRlbW8gdGV4dCBmaWxlLg==","path":null},
    {"id":5,"name":"najnovije ime iz postamana za file text2","type":"txt","description":"opis text file","document":"VGhpcyBpcyBhIGRlbW8gdGV4dCBmaWxlIDIu","path":null},
    {"id":6,"name":"najnovije ime iz postamana za file text2","type":"txt","description":"opis text file","document":"VGhpcyBpcyBhIGRlbW8gdGV4dCBmaWxlIDIu","path":null}
  ] */

  constructor(private restService: DmsService) { }

  ngOnInit() {
    this.getAllDocuments();
  }

  // Getting all documents from a database
  getAllDocuments() {
    this.restService.getAllDocuments().subscribe(response => {
      this.documentArray = [];
      this.countDocuments = 0;
      this.resultSet = response;

      this.message = response.message;
      console.log("this.resultSet: ", this.resultSet);

      this.countDocuments = response.result.length;
      this.resultSet.result.forEach(item => {
        this.documentArray.push({
          id: item.id,
          name: item.name,
          type: item.type,
          document: item.document,
          description: item.description,
          path: item.path
        });
      });

      this.dataSource = new MatTableDataSource<IDocumentResultSet>(this.documentArray);
      //this.dataSource = new MatTableDataSource<IDocumentResultSet>(this.mockDataSource);
      console.log("this.documentArray", this.documentArray);
      console.log("this.datasource", this.dataSource);

    })
  }
  
  onRowClicked(document: IDocumentResultSet) {
    //console.log(event.target);
    console.log(document);
  }

}
