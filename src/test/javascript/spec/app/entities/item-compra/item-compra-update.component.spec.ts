import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ItemCompraUpdateComponent } from 'app/entities/item-compra/item-compra-update.component';
import { ItemCompraService } from 'app/entities/item-compra/item-compra.service';
import { ItemCompra } from 'app/shared/model/item-compra.model';

describe('Component Tests', () => {
  describe('ItemCompra Management Update Component', () => {
    let comp: ItemCompraUpdateComponent;
    let fixture: ComponentFixture<ItemCompraUpdateComponent>;
    let service: ItemCompraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ItemCompraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemCompraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemCompraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemCompraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemCompra(123);
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
        const entity = new ItemCompra();
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
