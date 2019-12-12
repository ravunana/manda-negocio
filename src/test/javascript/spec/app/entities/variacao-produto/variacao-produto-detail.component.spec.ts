import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { VariacaoProdutoDetailComponent } from 'app/entities/variacao-produto/variacao-produto-detail.component';
import { VariacaoProduto } from 'app/shared/model/variacao-produto.model';

describe('Component Tests', () => {
  describe('VariacaoProduto Management Detail Component', () => {
    let comp: VariacaoProdutoDetailComponent;
    let fixture: ComponentFixture<VariacaoProdutoDetailComponent>;
    const route = ({ data: of({ variacaoProduto: new VariacaoProduto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [VariacaoProdutoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VariacaoProdutoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VariacaoProdutoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.variacaoProduto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
