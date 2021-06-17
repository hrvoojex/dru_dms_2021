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
  displayedColumns: string[] = ['index','name', 'description', 'download', 'delete'];
  dataSource: MatTableDataSource<IDocumentResultSet>;
  countDocuments: number = 0;
  message = '';
  fileName = '';

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

  downloadDocument(doc: IDocumentResultSet) {
    let documentBase64 = doc.document;
    let documentDecoded = atob(documentBase64);
    console.log(documentDecoded);
    
    let encodedByteArray = new TextEncoder().encode(documentDecoded)
    //let encodedByteArray = new TextEncoder().encode(doc.document)

    //var encoded = new TextEncoder().encode("Γεια σου κόσμε");
    //var decoded = new TextDecoder("utf-8").decode(encoded);
    //console.log(encoded, decoded);

    //let file = new Blob([response], { type: 'application/pdf' });  
    /* let file = new Blob([encodedByteArray.buffer], { type: 'text/plain' });          
    let fileURL = URL.createObjectURL(file);
    window.open(fileURL); */

    const link = document.createElement( 'a' );
    link.style.display = 'none';
    document.body.appendChild( link );

    const blob = new Blob([encodedByteArray.buffer] );	
    const objectURL = URL.createObjectURL(blob);
    
    link.href = objectURL;
    link.href = URL.createObjectURL(blob);
    link.download =  doc.name;
    link.click();
  }

  uploadDocument(event: any) {
    console.log(event.target.files);
    let file: File = event.target.files[0]
    this.fileName = file.name;
    let description: string = file.type;  //TODO Take description from a user
    this.restService.insertBytesDocument(file, description).subscribe(resp => {
      console.log("Upload succesful...");
      this.getAllDocuments();
    })
  }

  deleteDocument(id: number) {
    this.restService.deleteDocument(id).subscribe(resp => {
      console.log("Document deleted ...");
      this.getAllDocuments();
    })
  }

}
