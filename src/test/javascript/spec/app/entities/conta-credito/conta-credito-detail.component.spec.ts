import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContaCreditoDetailComponent } from 'app/entities/conta-credito/conta-credito-detail.component';
import { ContaCredito } from 'app/shared/model/conta-credito.model';

describe('Component Tests', () => {
  describe('ContaCredito Management Detail Component', () => {
    let comp: ContaCreditoDetailComponent;
    let fixture: ComponentFixture<ContaCreditoDetailComponent>;
    const route = ({ data: of({ contaCredito: new ContaCredito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContaCreditoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContaCreditoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContaCreditoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contaCredito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
