import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TomcatOverviewComponent } from './tomcat-overview.component';

describe('TomcatOverviewComponent', () => {
  let component: TomcatOverviewComponent;
  let fixture: ComponentFixture<TomcatOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TomcatOverviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TomcatOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
