import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { EstruturaCalculoUpdateComponent } from 'app/entities/estrutura-calculo/estrutura-calculo-update.component';
import { EstruturaCalculoService } from 'app/entities/estrutura-calculo/estrutura-calculo.service';
import { EstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';

describe('Component Tests', () => {
  describe('EstruturaCalculo Management Update Component', () => {
    let comp: EstruturaCalculoUpdateComponent;
    let fixture: ComponentFixture<EstruturaCalculoUpdateComponent>;
    let service: EstruturaCalculoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [EstruturaCalculoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstruturaCalculoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstruturaCalculoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstruturaCalculoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstruturaCalculo(123);
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
        const entity = new EstruturaCalculo();
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
