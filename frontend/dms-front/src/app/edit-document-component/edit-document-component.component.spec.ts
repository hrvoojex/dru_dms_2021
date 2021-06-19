import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDocumentComponentComponent } from './edit-document-component.component';

describe('EditDocumentComponentComponent', () => {
  let component: EditDocumentComponentComponent;
  let fixture: ComponentFixture<EditDocumentComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditDocumentComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditDocumentComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
