import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { UnidadeMedidaDetailComponent } from 'app/entities/unidade-medida/unidade-medida-detail.component';
import { UnidadeMedida } from 'app/shared/model/unidade-medida.model';

describe('Component Tests', () => {
  describe('UnidadeMedida Management Detail Component', () => {
    let comp: UnidadeMedidaDetailComponent;
    let fixture: ComponentFixture<UnidadeMedidaDetailComponent>;
    const route = ({ data: of({ unidadeMedida: new UnidadeMedida(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [UnidadeMedidaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UnidadeMedidaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnidadeMedidaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unidadeMedida).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
