import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DevolucaoCompraDetailComponent } from 'app/entities/devolucao-compra/devolucao-compra-detail.component';
import { DevolucaoCompra } from 'app/shared/model/devolucao-compra.model';

describe('Component Tests', () => {
  describe('DevolucaoCompra Management Detail Component', () => {
    let comp: DevolucaoCompraDetailComponent;
    let fixture: ComponentFixture<DevolucaoCompraDetailComponent>;
    const route = ({ data: of({ devolucaoCompra: new DevolucaoCompra(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DevolucaoCompraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DevolucaoCompraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DevolucaoCompraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.devolucaoCompra).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
