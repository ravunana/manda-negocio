import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RegraMovimentacaoDebitoDetailComponent } from 'app/entities/regra-movimentacao-debito/regra-movimentacao-debito-detail.component';
import { RegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';

describe('Component Tests', () => {
  describe('RegraMovimentacaoDebito Management Detail Component', () => {
    let comp: RegraMovimentacaoDebitoDetailComponent;
    let fixture: ComponentFixture<RegraMovimentacaoDebitoDetailComponent>;
    const route = ({ data: of({ regraMovimentacaoDebito: new RegraMovimentacaoDebito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RegraMovimentacaoDebitoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegraMovimentacaoDebitoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegraMovimentacaoDebitoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.regraMovimentacaoDebito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
