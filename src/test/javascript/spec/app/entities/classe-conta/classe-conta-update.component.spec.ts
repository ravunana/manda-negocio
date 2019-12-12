import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ClasseContaUpdateComponent } from 'app/entities/classe-conta/classe-conta-update.component';
import { ClasseContaService } from 'app/entities/classe-conta/classe-conta.service';
import { ClasseConta } from 'app/shared/model/classe-conta.model';

describe('Component Tests', () => {
  describe('ClasseConta Management Update Component', () => {
    let comp: ClasseContaUpdateComponent;
    let fixture: ComponentFixture<ClasseContaUpdateComponent>;
    let service: ClasseContaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ClasseContaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClasseContaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClasseContaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClasseContaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClasseConta(123);
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
        const entity = new ClasseConta();
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
