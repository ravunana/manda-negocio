import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DocumentoComercialUpdateComponent } from 'app/entities/documento-comercial/documento-comercial-update.component';
import { DocumentoComercialService } from 'app/entities/documento-comercial/documento-comercial.service';
import { DocumentoComercial } from 'app/shared/model/documento-comercial.model';

describe('Component Tests', () => {
  describe('DocumentoComercial Management Update Component', () => {
    let comp: DocumentoComercialUpdateComponent;
    let fixture: ComponentFixture<DocumentoComercialUpdateComponent>;
    let service: DocumentoComercialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DocumentoComercialUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocumentoComercialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentoComercialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentoComercialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentoComercial(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentoComercial();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
