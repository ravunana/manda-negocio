import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { MoedaUpdateComponent } from 'app/entities/moeda/moeda-update.component';
import { MoedaService } from 'app/entities/moeda/moeda.service';
import { Moeda } from 'app/shared/model/moeda.model';

describe('Component Tests', () => {
  describe('Moeda Management Update Component', () => {
    let comp: MoedaUpdateComponent;
    let fixture: ComponentFixture<MoedaUpdateComponent>;
    let service: MoedaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [MoedaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MoedaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MoedaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MoedaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Moeda(123);
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
        const entity = new Moeda();
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
