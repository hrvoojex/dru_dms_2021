import { IResultSet, IDocument } from './data/IDocument';
import { DmsService } from './service/dms-service';
import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DMS Front';
  resultSetArray: IResultSet[] = [];
  documentArray: IDocument[] = [];
  displayedColumns: string[] = ['name', 'description'];
  dataSource: MatTableDataSource<IResultSet>;
  errorMessage = '';
  countDocument: number;

  constructor(private restService: DmsService) { }

  ngOnInit() {
    this.getAllDocuments();
  }

  getAllDocuments() {
    this.restService.getAllDocuments().subscribe(response => {
      this.errorMessage = '';
      this.documentArray = [];
      this.countDocument = 1;

      this.resultSetArray = [];
      this.resultSetArray.push({
          message: response.message,
          result: response.result
        });
      this.dataSource = new MatTableDataSource<IResultSet>(this.resultSetArray);
      console.log(this.resultSetArray);
    })
  }
  

}
