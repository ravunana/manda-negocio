import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { EstoqueConfigUpdateComponent } from 'app/entities/estoque-config/estoque-config-update.component';
import { EstoqueConfigService } from 'app/entities/estoque-config/estoque-config.service';
import { EstoqueConfig } from 'app/shared/model/estoque-config.model';

describe('Component Tests', () => {
  describe('EstoqueConfig Management Update Component', () => {
    let comp: EstoqueConfigUpdateComponent;
    let fixture: ComponentFixture<EstoqueConfigUpdateComponent>;
    let service: EstoqueConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [EstoqueConfigUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstoqueConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstoqueConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstoqueConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstoqueConfig(123);
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
        const entity = new EstoqueConfig();
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
