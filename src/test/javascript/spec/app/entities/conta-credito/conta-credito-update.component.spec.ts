import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContaCreditoUpdateComponent } from 'app/entities/conta-credito/conta-credito-update.component';
import { ContaCreditoService } from 'app/entities/conta-credito/conta-credito.service';
import { ContaCredito } from 'app/shared/model/conta-credito.model';

describe('Component Tests', () => {
  describe('ContaCredito Management Update Component', () => {
    let comp: ContaCreditoUpdateComponent;
    let fixture: ComponentFixture<ContaCreditoUpdateComponent>;
    let service: ContaCreditoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContaCreditoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContaCreditoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContaCreditoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContaCreditoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContaCredito(123);
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
        const entity = new ContaCredito();
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
