import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ItemVendaUpdateComponent } from 'app/entities/item-venda/item-venda-update.component';
import { ItemVendaService } from 'app/entities/item-venda/item-venda.service';
import { ItemVenda } from 'app/shared/model/item-venda.model';

describe('Component Tests', () => {
  describe('ItemVenda Management Update Component', () => {
    let comp: ItemVendaUpdateComponent;
    let fixture: ComponentFixture<ItemVendaUpdateComponent>;
    let service: ItemVendaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ItemVendaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemVendaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemVendaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemVendaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemVenda(123);
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
        const entity = new ItemVenda();
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
