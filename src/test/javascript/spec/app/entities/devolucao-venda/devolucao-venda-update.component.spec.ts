import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DevolucaoVendaUpdateComponent } from 'app/entities/devolucao-venda/devolucao-venda-update.component';
import { DevolucaoVendaService } from 'app/entities/devolucao-venda/devolucao-venda.service';
import { DevolucaoVenda } from 'app/shared/model/devolucao-venda.model';

describe('Component Tests', () => {
  describe('DevolucaoVenda Management Update Component', () => {
    let comp: DevolucaoVendaUpdateComponent;
    let fixture: ComponentFixture<DevolucaoVendaUpdateComponent>;
    let service: DevolucaoVendaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DevolucaoVendaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DevolucaoVendaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DevolucaoVendaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DevolucaoVendaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DevolucaoVenda(123);
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
        const entity = new DevolucaoVenda();
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
