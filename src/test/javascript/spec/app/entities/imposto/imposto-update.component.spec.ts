import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ImpostoUpdateComponent } from 'app/entities/imposto/imposto-update.component';
import { ImpostoService } from 'app/entities/imposto/imposto.service';
import { Imposto } from 'app/shared/model/imposto.model';

describe('Component Tests', () => {
  describe('Imposto Management Update Component', () => {
    let comp: ImpostoUpdateComponent;
    let fixture: ComponentFixture<ImpostoUpdateComponent>;
    let service: ImpostoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ImpostoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImpostoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImpostoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpostoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Imposto(123);
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
        const entity = new Imposto();
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
