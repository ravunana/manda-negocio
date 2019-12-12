import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { FluxoDocumentoDeleteDialogComponent } from 'app/entities/fluxo-documento/fluxo-documento-delete-dialog.component';
import { FluxoDocumentoService } from 'app/entities/fluxo-documento/fluxo-documento.service';

describe('Component Tests', () => {
  describe('FluxoDocumento Management Delete Component', () => {
    let comp: FluxoDocumentoDeleteDialogComponent;
    let fixture: ComponentFixture<FluxoDocumentoDeleteDialogComponent>;
    let service: FluxoDocumentoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [FluxoDocumentoDeleteDialogComponent]
      })
        .overrideTemplate(FluxoDocumentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FluxoDocumentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FluxoDocumentoService);
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
