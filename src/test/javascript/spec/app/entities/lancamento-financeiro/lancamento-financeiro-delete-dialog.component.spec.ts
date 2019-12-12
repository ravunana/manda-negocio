import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { LancamentoFinanceiroDeleteDialogComponent } from 'app/entities/lancamento-financeiro/lancamento-financeiro-delete-dialog.component';
import { LancamentoFinanceiroService } from 'app/entities/lancamento-financeiro/lancamento-financeiro.service';

describe('Component Tests', () => {
  describe('LancamentoFinanceiro Management Delete Component', () => {
    let comp: LancamentoFinanceiroDeleteDialogComponent;
    let fixture: ComponentFixture<LancamentoFinanceiroDeleteDialogComponent>;
    let service: LancamentoFinanceiroService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LancamentoFinanceiroDeleteDialogComponent]
      })
        .overrideTemplate(LancamentoFinanceiroDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LancamentoFinanceiroDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LancamentoFinanceiroService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
