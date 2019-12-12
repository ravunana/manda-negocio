import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { AuditoriaCompraUpdateComponent } from 'app/entities/auditoria-compra/auditoria-compra-update.component';
import { AuditoriaCompraService } from 'app/entities/auditoria-compra/auditoria-compra.service';
import { AuditoriaCompra } from 'app/shared/model/auditoria-compra.model';

describe('Component Tests', () => {
  describe('AuditoriaCompra Management Update Component', () => {
    let comp: AuditoriaCompraUpdateComponent;
    let fixture: ComponentFixture<AuditoriaCompraUpdateComponent>;
    let service: AuditoriaCompraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [AuditoriaCompraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AuditoriaCompraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditoriaCompraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditoriaCompraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditoriaCompra(123);
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
        const entity = new AuditoriaCompra();
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
