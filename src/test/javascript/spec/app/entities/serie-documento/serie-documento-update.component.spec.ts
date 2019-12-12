import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { SerieDocumentoUpdateComponent } from 'app/entities/serie-documento/serie-documento-update.component';
import { SerieDocumentoService } from 'app/entities/serie-documento/serie-documento.service';
import { SerieDocumento } from 'app/shared/model/serie-documento.model';

describe('Component Tests', () => {
  describe('SerieDocumento Management Update Component', () => {
    let comp: SerieDocumentoUpdateComponent;
    let fixture: ComponentFixture<SerieDocumentoUpdateComponent>;
    let service: SerieDocumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [SerieDocumentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SerieDocumentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SerieDocumentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SerieDocumentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SerieDocumento(123);
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
        const entity = new SerieDocumento();
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
