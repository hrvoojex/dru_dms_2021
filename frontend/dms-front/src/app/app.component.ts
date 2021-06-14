import { IResultSet } from './data/IDocument';
import { DmsService } from './service/dms-service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DMS Front';
  resultSetArray: IResultSet[];

  constructor(public restService: DmsService) { }

  ngOnInit() {
    
  }

  getAllDocuments() {
    this.restService.getAllDocuments().subscribe(response => {
      this.errorMessage = '';
      this.documentArray = [];
      this.countDocument = 1;

      this.resultSetArray = [];
      this.resultSetArray.push({
          name: response.name,
          type: response.type
        });
    }
  };
  

}
