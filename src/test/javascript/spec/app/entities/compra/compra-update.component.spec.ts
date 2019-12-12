import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { CompraUpdateComponent } from 'app/entities/compra/compra-update.component';
import { CompraService } from 'app/entities/compra/compra.service';
import { Compra } from 'app/shared/model/compra.model';

describe('Component Tests', () => {
  describe('Compra Management Update Component', () => {
    let comp: CompraUpdateComponent;
    let fixture: ComponentFixture<CompraUpdateComponent>;
    let service: CompraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [CompraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Compra(123);
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
        const entity = new Compra();
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
