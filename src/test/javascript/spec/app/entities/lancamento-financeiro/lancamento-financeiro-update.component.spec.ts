import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LancamentoFinanceiroUpdateComponent } from 'app/entities/lancamento-financeiro/lancamento-financeiro-update.component';
import { LancamentoFinanceiroService } from 'app/entities/lancamento-financeiro/lancamento-financeiro.service';
import { LancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';

describe('Component Tests', () => {
  describe('LancamentoFinanceiro Management Update Component', () => {
    let comp: LancamentoFinanceiroUpdateComponent;
    let fixture: ComponentFixture<LancamentoFinanceiroUpdateComponent>;
    let service: LancamentoFinanceiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LancamentoFinanceiroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LancamentoFinanceiroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LancamentoFinanceiroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LancamentoFinanceiroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LancamentoFinanceiro(123);
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
        const entity = new LancamentoFinanceiro();
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
