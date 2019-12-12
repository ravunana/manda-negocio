import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { CompostoProdutoDetailComponent } from 'app/entities/composto-produto/composto-produto-detail.component';
import { CompostoProduto } from 'app/shared/model/composto-produto.model';

describe('Component Tests', () => {
  describe('CompostoProduto Management Detail Component', () => {
    let comp: CompostoProdutoDetailComponent;
    let fixture: ComponentFixture<CompostoProdutoDetailComponent>;
    const route = ({ data: of({ compostoProduto: new CompostoProduto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [CompostoProdutoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompostoProdutoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompostoProdutoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compostoProduto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
