import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DetalheLancamentoUpdateComponent } from 'app/entities/detalhe-lancamento/detalhe-lancamento-update.component';
import { DetalheLancamentoService } from 'app/entities/detalhe-lancamento/detalhe-lancamento.service';
import { DetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

describe('Component Tests', () => {
  describe('DetalheLancamento Management Update Component', () => {
    let comp: DetalheLancamentoUpdateComponent;
    let fixture: ComponentFixture<DetalheLancamentoUpdateComponent>;
    let service: DetalheLancamentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DetalheLancamentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DetalheLancamentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetalheLancamentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalheLancamentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetalheLancamento(123);
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
        const entity = new DetalheLancamento();
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
