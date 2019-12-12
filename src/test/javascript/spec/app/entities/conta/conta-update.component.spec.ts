import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContaUpdateComponent } from 'app/entities/conta/conta-update.component';
import { ContaService } from 'app/entities/conta/conta.service';
import { Conta } from 'app/shared/model/conta.model';

describe('Component Tests', () => {
  describe('Conta Management Update Component', () => {
    let comp: ContaUpdateComponent;
    let fixture: ComponentFixture<ContaUpdateComponent>;
    let service: ContaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Conta(123);
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
        const entity = new Conta();
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
