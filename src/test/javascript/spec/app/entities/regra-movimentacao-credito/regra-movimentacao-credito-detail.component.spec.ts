import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RegraMovimentacaoCreditoDetailComponent } from 'app/entities/regra-movimentacao-credito/regra-movimentacao-credito-detail.component';
import { RegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';

describe('Component Tests', () => {
  describe('RegraMovimentacaoCredito Management Detail Component', () => {
    let comp: RegraMovimentacaoCreditoDetailComponent;
    let fixture: ComponentFixture<RegraMovimentacaoCreditoDetailComponent>;
    const route = ({ data: of({ regraMovimentacaoCredito: new RegraMovimentacaoCredito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RegraMovimentacaoCreditoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegraMovimentacaoCreditoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegraMovimentacaoCreditoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.regraMovimentacaoCredito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
