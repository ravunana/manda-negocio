import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LocalArmazenamentoUpdateComponent } from 'app/entities/local-armazenamento/local-armazenamento-update.component';
import { LocalArmazenamentoService } from 'app/entities/local-armazenamento/local-armazenamento.service';
import { LocalArmazenamento } from 'app/shared/model/local-armazenamento.model';

describe('Component Tests', () => {
  describe('LocalArmazenamento Management Update Component', () => {
    let comp: LocalArmazenamentoUpdateComponent;
    let fixture: ComponentFixture<LocalArmazenamentoUpdateComponent>;
    let service: LocalArmazenamentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LocalArmazenamentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocalArmazenamentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalArmazenamentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalArmazenamentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LocalArmazenamento(123);
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
        const entity = new LocalArmazenamento();
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
