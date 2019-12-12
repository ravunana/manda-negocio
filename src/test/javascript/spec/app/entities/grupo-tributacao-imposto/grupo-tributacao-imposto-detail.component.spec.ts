import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { GrupoTributacaoImpostoDetailComponent } from 'app/entities/grupo-tributacao-imposto/grupo-tributacao-imposto-detail.component';
import { GrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';

describe('Component Tests', () => {
  describe('GrupoTributacaoImposto Management Detail Component', () => {
    let comp: GrupoTributacaoImpostoDetailComponent;
    let fixture: ComponentFixture<GrupoTributacaoImpostoDetailComponent>;
    const route = ({ data: of({ grupoTributacaoImposto: new GrupoTributacaoImposto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [GrupoTributacaoImpostoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GrupoTributacaoImpostoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrupoTributacaoImpostoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grupoTributacaoImposto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
