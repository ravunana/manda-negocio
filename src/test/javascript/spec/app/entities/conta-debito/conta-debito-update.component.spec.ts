import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContaDebitoUpdateComponent } from 'app/entities/conta-debito/conta-debito-update.component';
import { ContaDebitoService } from 'app/entities/conta-debito/conta-debito.service';
import { ContaDebito } from 'app/shared/model/conta-debito.model';

describe('Component Tests', () => {
  describe('ContaDebito Management Update Component', () => {
    let comp: ContaDebitoUpdateComponent;
    let fixture: ComponentFixture<ContaDebitoUpdateComponent>;
    let service: ContaDebitoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContaDebitoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContaDebitoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContaDebitoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContaDebitoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContaDebito(123);
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
        const entity = new ContaDebito();
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
