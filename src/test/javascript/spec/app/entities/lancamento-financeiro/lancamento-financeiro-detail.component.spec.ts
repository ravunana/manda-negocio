import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LancamentoFinanceiroDetailComponent } from 'app/entities/lancamento-financeiro/lancamento-financeiro-detail.component';
import { LancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';

describe('Component Tests', () => {
  describe('LancamentoFinanceiro Management Detail Component', () => {
    let comp: LancamentoFinanceiroDetailComponent;
    let fixture: ComponentFixture<LancamentoFinanceiroDetailComponent>;
    const route = ({ data: of({ lancamentoFinanceiro: new LancamentoFinanceiro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LancamentoFinanceiroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LancamentoFinanceiroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LancamentoFinanceiroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lancamentoFinanceiro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
