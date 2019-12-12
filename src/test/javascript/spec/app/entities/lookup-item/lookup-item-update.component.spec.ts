import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LookupItemUpdateComponent } from 'app/entities/lookup-item/lookup-item-update.component';
import { LookupItemService } from 'app/entities/lookup-item/lookup-item.service';
import { LookupItem } from 'app/shared/model/lookup-item.model';

describe('Component Tests', () => {
  describe('LookupItem Management Update Component', () => {
    let comp: LookupItemUpdateComponent;
    let fixture: ComponentFixture<LookupItemUpdateComponent>;
    let service: LookupItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LookupItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LookupItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LookupItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LookupItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LookupItem(123);
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
        const entity = new LookupItem();
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
