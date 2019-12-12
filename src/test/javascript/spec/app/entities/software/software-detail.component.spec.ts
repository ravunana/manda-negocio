import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { SoftwareDetailComponent } from 'app/entities/software/software-detail.component';
import { Software } from 'app/shared/model/software.model';

describe('Component Tests', () => {
  describe('Software Management Detail Component', () => {
    let comp: SoftwareDetailComponent;
    let fixture: ComponentFixture<SoftwareDetailComponent>;
    const route = ({ data: of({ software: new Software(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [SoftwareDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SoftwareDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SoftwareDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.software).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
