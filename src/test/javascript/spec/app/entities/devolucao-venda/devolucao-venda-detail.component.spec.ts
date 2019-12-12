import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DevolucaoVendaDetailComponent } from 'app/entities/devolucao-venda/devolucao-venda-detail.component';
import { DevolucaoVenda } from 'app/shared/model/devolucao-venda.model';

describe('Component Tests', () => {
  describe('DevolucaoVenda Management Detail Component', () => {
    let comp: DevolucaoVendaDetailComponent;
    let fixture: ComponentFixture<DevolucaoVendaDetailComponent>;
    const route = ({ data: of({ devolucaoVenda: new DevolucaoVenda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DevolucaoVendaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DevolucaoVendaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DevolucaoVendaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.devolucaoVenda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
