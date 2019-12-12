import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { AuditoriaVendaDeleteDialogComponent } from 'app/entities/auditoria-venda/auditoria-venda-delete-dialog.component';
import { AuditoriaVendaService } from 'app/entities/auditoria-venda/auditoria-venda.service';

describe('Component Tests', () => {
  describe('AuditoriaVenda Management Delete Component', () => {
    let comp: AuditoriaVendaDeleteDialogComponent;
    let fixture: ComponentFixture<AuditoriaVendaDeleteDialogComponent>;
    let service: AuditoriaVendaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [AuditoriaVendaDeleteDialogComponent]
      })
        .overrideTemplate(AuditoriaVendaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditoriaVendaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditoriaVendaService);
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
