import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LicencaSoftwareUpdateComponent } from 'app/entities/licenca-software/licenca-software-update.component';
import { LicencaSoftwareService } from 'app/entities/licenca-software/licenca-software.service';
import { LicencaSoftware } from 'app/shared/model/licenca-software.model';

describe('Component Tests', () => {
  describe('LicencaSoftware Management Update Component', () => {
    let comp: LicencaSoftwareUpdateComponent;
    let fixture: ComponentFixture<LicencaSoftwareUpdateComponent>;
    let service: LicencaSoftwareService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LicencaSoftwareUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LicencaSoftwareUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LicencaSoftwareUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LicencaSoftwareService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LicencaSoftware(123);
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
        const entity = new LicencaSoftware();
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
