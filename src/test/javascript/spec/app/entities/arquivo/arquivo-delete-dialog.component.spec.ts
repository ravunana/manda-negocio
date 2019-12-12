import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ArquivoDeleteDialogComponent } from 'app/entities/arquivo/arquivo-delete-dialog.component';
import { ArquivoService } from 'app/entities/arquivo/arquivo.service';

describe('Component Tests', () => {
  describe('Arquivo Management Delete Component', () => {
    let comp: ArquivoDeleteDialogComponent;
    let fixture: ComponentFixture<ArquivoDeleteDialogComponent>;
    let service: ArquivoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ArquivoDeleteDialogComponent]
      })
        .overrideTemplate(ArquivoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArquivoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArquivoService);
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
