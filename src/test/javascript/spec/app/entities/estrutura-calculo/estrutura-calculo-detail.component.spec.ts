import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { EstruturaCalculoDetailComponent } from 'app/entities/estrutura-calculo/estrutura-calculo-detail.component';
import { EstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';

describe('Component Tests', () => {
  describe('EstruturaCalculo Management Detail Component', () => {
    let comp: EstruturaCalculoDetailComponent;
    let fixture: ComponentFixture<EstruturaCalculoDetailComponent>;
    const route = ({ data: of({ estruturaCalculo: new EstruturaCalculo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [EstruturaCalculoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstruturaCalculoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstruturaCalculoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estruturaCalculo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
