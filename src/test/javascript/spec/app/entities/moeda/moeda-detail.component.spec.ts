import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { MoedaDetailComponent } from 'app/entities/moeda/moeda-detail.component';
import { Moeda } from 'app/shared/model/moeda.model';

describe('Component Tests', () => {
  describe('Moeda Management Detail Component', () => {
    let comp: MoedaDetailComponent;
    let fixture: ComponentFixture<MoedaDetailComponent>;
    const route = ({ data: of({ moeda: new Moeda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [MoedaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MoedaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MoedaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.moeda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
