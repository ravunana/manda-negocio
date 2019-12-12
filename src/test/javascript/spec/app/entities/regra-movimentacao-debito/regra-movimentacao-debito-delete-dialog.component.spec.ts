import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { RegraMovimentacaoDebitoDeleteDialogComponent } from 'app/entities/regra-movimentacao-debito/regra-movimentacao-debito-delete-dialog.component';
import { RegraMovimentacaoDebitoService } from 'app/entities/regra-movimentacao-debito/regra-movimentacao-debito.service';

describe('Component Tests', () => {
  describe('RegraMovimentacaoDebito Management Delete Component', () => {
    let comp: RegraMovimentacaoDebitoDeleteDialogComponent;
    let fixture: ComponentFixture<RegraMovimentacaoDebitoDeleteDialogComponent>;
    let service: RegraMovimentacaoDebitoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RegraMovimentacaoDebitoDeleteDialogComponent]
      })
        .overrideTemplate(RegraMovimentacaoDebitoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegraMovimentacaoDebitoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegraMovimentacaoDebitoService);
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
