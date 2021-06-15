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
  displayedColumns: string[] = ['index','name', 'description', 'download'];
  dataSource: MatTableDataSource<IDocumentResultSet>;
  countDocuments: number = 0;
  message = '';

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
      console.log("this.documentArray", this.documentArray);
      console.log("this.datasource", this.dataSource);

    })
  }
  
  onRowClicked(document: IDocumentResultSet) {
    //console.log(event.target);
    console.log("Row clicked: ", document);
  }

  downloadDocument(document: IDocumentResultSet) {
    let documentBase64 = document.document;
    let documentDecoded = atob(documentBase64);
    console.log(documentDecoded);
    
    let encodedByteArray = new TextEncoder().encode(documentDecoded)

    //var encoded = new TextEncoder().encode("Γεια σου κόσμε");
    //var decoded = new TextDecoder("utf-8").decode(encoded);
    //console.log(encoded, decoded);


    //let file = new Blob([response], { type: 'application/pdf' });  
    let file = new Blob([encodedByteArray.buffer], { type: 'text/plain' });          
    let fileURL = URL.createObjectURL(file);
    window.open(fileURL);

  }

}
