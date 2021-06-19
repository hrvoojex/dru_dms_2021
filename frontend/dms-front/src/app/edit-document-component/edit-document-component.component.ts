import { IDocumentResultSet } from './../data/IDocument';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DmsService } from '../service/dms-service';

@Component({
  selector: 'app-edit-document-component',
  templateUrl: './edit-document-component.component.html',
  styleUrls: ['./edit-document-component.component.css']
})
export class EditDocumentComponentComponent implements OnInit {
  id: number;
  name = '';
  description = '';
  document = '';
  doc: IDocumentResultSet;
  file: File = null;


  constructor(private restService: DmsService,
    private dialogRef: MatDialogRef<EditDocumentComponentComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any) { }

  ngOnInit(): void {
    this.name = this.data.name;
    this.description = this.data.description;
    this.document = this.data.document;
    this.id = this.data.id;
  }

  // Edit document
  uploadEditDocument(event: any) {
    console.log(event.target.files);
    this.file = event.target.files[0]
    this.name = this.file.name;
    
  }

  onClose() {
    this.restService.updateBytesDocument(
      this.file, this.description, this.name, this.id).subscribe(resp => {
      console.log("Upload succesful...");
    })
    this.dialogRef.close();
  }

}
