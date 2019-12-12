import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { RegraMovimentacaoCreditoDeleteDialogComponent } from 'app/entities/regra-movimentacao-credito/regra-movimentacao-credito-delete-dialog.component';
import { RegraMovimentacaoCreditoService } from 'app/entities/regra-movimentacao-credito/regra-movimentacao-credito.service';

describe('Component Tests', () => {
  describe('RegraMovimentacaoCredito Management Delete Component', () => {
    let comp: RegraMovimentacaoCreditoDeleteDialogComponent;
    let fixture: ComponentFixture<RegraMovimentacaoCreditoDeleteDialogComponent>;
    let service: RegraMovimentacaoCreditoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RegraMovimentacaoCreditoDeleteDialogComponent]
      })
        .overrideTemplate(RegraMovimentacaoCreditoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegraMovimentacaoCreditoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegraMovimentacaoCreditoService);
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
