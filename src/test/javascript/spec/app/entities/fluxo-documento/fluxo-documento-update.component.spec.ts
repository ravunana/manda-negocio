import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { FluxoDocumentoUpdateComponent } from 'app/entities/fluxo-documento/fluxo-documento-update.component';
import { FluxoDocumentoService } from 'app/entities/fluxo-documento/fluxo-documento.service';
import { FluxoDocumento } from 'app/shared/model/fluxo-documento.model';

describe('Component Tests', () => {
  describe('FluxoDocumento Management Update Component', () => {
    let comp: FluxoDocumentoUpdateComponent;
    let fixture: ComponentFixture<FluxoDocumentoUpdateComponent>;
    let service: FluxoDocumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [FluxoDocumentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FluxoDocumentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FluxoDocumentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FluxoDocumentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FluxoDocumento(123);
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
        const entity = new FluxoDocumento();
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
