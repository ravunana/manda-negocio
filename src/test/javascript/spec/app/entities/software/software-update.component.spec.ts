import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { SoftwareUpdateComponent } from 'app/entities/software/software-update.component';
import { SoftwareService } from 'app/entities/software/software.service';
import { Software } from 'app/shared/model/software.model';

describe('Component Tests', () => {
  describe('Software Management Update Component', () => {
    let comp: SoftwareUpdateComponent;
    let fixture: ComponentFixture<SoftwareUpdateComponent>;
    let service: SoftwareService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [SoftwareUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SoftwareUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SoftwareUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SoftwareService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Software(123);
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
        const entity = new Software();
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
