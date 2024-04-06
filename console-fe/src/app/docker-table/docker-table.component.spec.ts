import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DockerTableComponent } from './docker-table.component';

describe('DockerTableComponent', () => {
  let component: DockerTableComponent;
  let fixture: ComponentFixture<DockerTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DockerTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DockerTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
