import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { AuditoriaVendaUpdateComponent } from 'app/entities/auditoria-venda/auditoria-venda-update.component';
import { AuditoriaVendaService } from 'app/entities/auditoria-venda/auditoria-venda.service';
import { AuditoriaVenda } from 'app/shared/model/auditoria-venda.model';

describe('Component Tests', () => {
  describe('AuditoriaVenda Management Update Component', () => {
    let comp: AuditoriaVendaUpdateComponent;
    let fixture: ComponentFixture<AuditoriaVendaUpdateComponent>;
    let service: AuditoriaVendaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [AuditoriaVendaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AuditoriaVendaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditoriaVendaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditoriaVendaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditoriaVenda(123);
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
        const entity = new AuditoriaVenda();
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
