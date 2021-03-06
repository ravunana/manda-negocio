import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContactoEmpresaUpdateComponent } from 'app/entities/contacto-empresa/contacto-empresa-update.component';
import { ContactoEmpresaService } from 'app/entities/contacto-empresa/contacto-empresa.service';
import { ContactoEmpresa } from 'app/shared/model/contacto-empresa.model';

describe('Component Tests', () => {
  describe('ContactoEmpresa Management Update Component', () => {
    let comp: ContactoEmpresaUpdateComponent;
    let fixture: ComponentFixture<ContactoEmpresaUpdateComponent>;
    let service: ContactoEmpresaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContactoEmpresaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContactoEmpresaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactoEmpresaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactoEmpresaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactoEmpresa(123);
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
        const entity = new ContactoEmpresa();
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
