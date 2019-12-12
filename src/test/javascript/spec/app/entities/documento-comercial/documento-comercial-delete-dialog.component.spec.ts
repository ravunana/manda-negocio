import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { DocumentoComercialDeleteDialogComponent } from 'app/entities/documento-comercial/documento-comercial-delete-dialog.component';
import { DocumentoComercialService } from 'app/entities/documento-comercial/documento-comercial.service';

describe('Component Tests', () => {
  describe('DocumentoComercial Management Delete Component', () => {
    let comp: DocumentoComercialDeleteDialogComponent;
    let fixture: ComponentFixture<DocumentoComercialDeleteDialogComponent>;
    let service: DocumentoComercialService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DocumentoComercialDeleteDialogComponent]
      })
        .overrideTemplate(DocumentoComercialDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentoComercialDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentoComercialService);
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
