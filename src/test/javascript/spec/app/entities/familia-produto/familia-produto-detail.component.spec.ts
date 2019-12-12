import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { FamiliaProdutoDetailComponent } from 'app/entities/familia-produto/familia-produto-detail.component';
import { FamiliaProduto } from 'app/shared/model/familia-produto.model';

describe('Component Tests', () => {
  describe('FamiliaProduto Management Detail Component', () => {
    let comp: FamiliaProdutoDetailComponent;
    let fixture: ComponentFixture<FamiliaProdutoDetailComponent>;
    const route = ({ data: of({ familiaProduto: new FamiliaProduto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [FamiliaProdutoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FamiliaProdutoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FamiliaProdutoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.familiaProduto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
