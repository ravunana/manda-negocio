import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DetalheAduaneiroDetailComponent } from 'app/entities/detalhe-aduaneiro/detalhe-aduaneiro-detail.component';
import { DetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';

describe('Component Tests', () => {
  describe('DetalheAduaneiro Management Detail Component', () => {
    let comp: DetalheAduaneiroDetailComponent;
    let fixture: ComponentFixture<DetalheAduaneiroDetailComponent>;
    const route = ({ data: of({ detalheAduaneiro: new DetalheAduaneiro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DetalheAduaneiroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetalheAduaneiroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalheAduaneiroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detalheAduaneiro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
