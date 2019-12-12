import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { AuditoriaCompraDeleteDialogComponent } from 'app/entities/auditoria-compra/auditoria-compra-delete-dialog.component';
import { AuditoriaCompraService } from 'app/entities/auditoria-compra/auditoria-compra.service';

describe('Component Tests', () => {
  describe('AuditoriaCompra Management Delete Component', () => {
    let comp: AuditoriaCompraDeleteDialogComponent;
    let fixture: ComponentFixture<AuditoriaCompraDeleteDialogComponent>;
    let service: AuditoriaCompraService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [AuditoriaCompraDeleteDialogComponent]
      })
        .overrideTemplate(AuditoriaCompraDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditoriaCompraDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditoriaCompraService);
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
