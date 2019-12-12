import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RetencaoFonteDetailComponent } from 'app/entities/retencao-fonte/retencao-fonte-detail.component';
import { RetencaoFonte } from 'app/shared/model/retencao-fonte.model';

describe('Component Tests', () => {
  describe('RetencaoFonte Management Detail Component', () => {
    let comp: RetencaoFonteDetailComponent;
    let fixture: ComponentFixture<RetencaoFonteDetailComponent>;
    const route = ({ data: of({ retencaoFonte: new RetencaoFonte(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RetencaoFonteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RetencaoFonteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RetencaoFonteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.retencaoFonte).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
