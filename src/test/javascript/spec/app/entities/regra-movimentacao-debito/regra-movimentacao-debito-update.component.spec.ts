import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RegraMovimentacaoDebitoUpdateComponent } from 'app/entities/regra-movimentacao-debito/regra-movimentacao-debito-update.component';
import { RegraMovimentacaoDebitoService } from 'app/entities/regra-movimentacao-debito/regra-movimentacao-debito.service';
import { RegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';

describe('Component Tests', () => {
  describe('RegraMovimentacaoDebito Management Update Component', () => {
    let comp: RegraMovimentacaoDebitoUpdateComponent;
    let fixture: ComponentFixture<RegraMovimentacaoDebitoUpdateComponent>;
    let service: RegraMovimentacaoDebitoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RegraMovimentacaoDebitoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegraMovimentacaoDebitoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegraMovimentacaoDebitoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegraMovimentacaoDebitoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegraMovimentacaoDebito(123);
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
        const entity = new RegraMovimentacaoDebito();
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
