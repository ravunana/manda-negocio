import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LookupUpdateComponent } from 'app/entities/lookup/lookup-update.component';
import { LookupService } from 'app/entities/lookup/lookup.service';
import { Lookup } from 'app/shared/model/lookup.model';

describe('Component Tests', () => {
  describe('Lookup Management Update Component', () => {
    let comp: LookupUpdateComponent;
    let fixture: ComponentFixture<LookupUpdateComponent>;
    let service: LookupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LookupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LookupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LookupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LookupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Lookup(123);
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
        const entity = new Lookup();
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
