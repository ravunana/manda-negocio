import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { FluxoDocumentoDetailComponent } from 'app/entities/fluxo-documento/fluxo-documento-detail.component';
import { FluxoDocumento } from 'app/shared/model/fluxo-documento.model';

describe('Component Tests', () => {
  describe('FluxoDocumento Management Detail Component', () => {
    let comp: FluxoDocumentoDetailComponent;
    let fixture: ComponentFixture<FluxoDocumentoDetailComponent>;
    const route = ({ data: of({ fluxoDocumento: new FluxoDocumento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [FluxoDocumentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FluxoDocumentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FluxoDocumentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fluxoDocumento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
