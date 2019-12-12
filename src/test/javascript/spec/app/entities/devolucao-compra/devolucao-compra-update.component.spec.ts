import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DevolucaoCompraUpdateComponent } from 'app/entities/devolucao-compra/devolucao-compra-update.component';
import { DevolucaoCompraService } from 'app/entities/devolucao-compra/devolucao-compra.service';
import { DevolucaoCompra } from 'app/shared/model/devolucao-compra.model';

describe('Component Tests', () => {
  describe('DevolucaoCompra Management Update Component', () => {
    let comp: DevolucaoCompraUpdateComponent;
    let fixture: ComponentFixture<DevolucaoCompraUpdateComponent>;
    let service: DevolucaoCompraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DevolucaoCompraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DevolucaoCompraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DevolucaoCompraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DevolucaoCompraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DevolucaoCompra(123);
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
        const entity = new DevolucaoCompra();
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
