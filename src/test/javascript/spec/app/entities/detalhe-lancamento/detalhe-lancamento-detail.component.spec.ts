import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DetalheLancamentoDetailComponent } from 'app/entities/detalhe-lancamento/detalhe-lancamento-detail.component';
import { DetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

describe('Component Tests', () => {
  describe('DetalheLancamento Management Detail Component', () => {
    let comp: DetalheLancamentoDetailComponent;
    let fixture: ComponentFixture<DetalheLancamentoDetailComponent>;
    const route = ({ data: of({ detalheLancamento: new DetalheLancamento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DetalheLancamentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetalheLancamentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalheLancamentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detalheLancamento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
