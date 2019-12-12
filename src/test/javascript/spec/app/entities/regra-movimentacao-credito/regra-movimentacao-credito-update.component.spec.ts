import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RegraMovimentacaoCreditoUpdateComponent } from 'app/entities/regra-movimentacao-credito/regra-movimentacao-credito-update.component';
import { RegraMovimentacaoCreditoService } from 'app/entities/regra-movimentacao-credito/regra-movimentacao-credito.service';
import { RegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';

describe('Component Tests', () => {
  describe('RegraMovimentacaoCredito Management Update Component', () => {
    let comp: RegraMovimentacaoCreditoUpdateComponent;
    let fixture: ComponentFixture<RegraMovimentacaoCreditoUpdateComponent>;
    let service: RegraMovimentacaoCreditoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RegraMovimentacaoCreditoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegraMovimentacaoCreditoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegraMovimentacaoCreditoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegraMovimentacaoCreditoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegraMovimentacaoCredito(123);
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
        const entity = new RegraMovimentacaoCredito();
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
