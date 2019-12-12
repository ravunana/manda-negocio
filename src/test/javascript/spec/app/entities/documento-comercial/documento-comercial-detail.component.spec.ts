import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DocumentoComercialDetailComponent } from 'app/entities/documento-comercial/documento-comercial-detail.component';
import { DocumentoComercial } from 'app/shared/model/documento-comercial.model';

describe('Component Tests', () => {
  describe('DocumentoComercial Management Detail Component', () => {
    let comp: DocumentoComercialDetailComponent;
    let fixture: ComponentFixture<DocumentoComercialDetailComponent>;
    const route = ({ data: of({ documentoComercial: new DocumentoComercial(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DocumentoComercialDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DocumentoComercialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentoComercialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentoComercial).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
