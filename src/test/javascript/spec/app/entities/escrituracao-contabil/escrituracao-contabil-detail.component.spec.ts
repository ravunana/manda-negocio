import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { EscrituracaoContabilDetailComponent } from 'app/entities/escrituracao-contabil/escrituracao-contabil-detail.component';
import { EscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';

describe('Component Tests', () => {
  describe('EscrituracaoContabil Management Detail Component', () => {
    let comp: EscrituracaoContabilDetailComponent;
    let fixture: ComponentFixture<EscrituracaoContabilDetailComponent>;
    const route = ({ data: of({ escrituracaoContabil: new EscrituracaoContabil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [EscrituracaoContabilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EscrituracaoContabilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EscrituracaoContabilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.escrituracaoContabil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
