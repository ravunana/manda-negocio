import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ImpostoDeleteDialogComponent } from 'app/entities/imposto/imposto-delete-dialog.component';
import { ImpostoService } from 'app/entities/imposto/imposto.service';

describe('Component Tests', () => {
  describe('Imposto Management Delete Component', () => {
    let comp: ImpostoDeleteDialogComponent;
    let fixture: ComponentFixture<ImpostoDeleteDialogComponent>;
    let service: ImpostoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ImpostoDeleteDialogComponent]
      })
        .overrideTemplate(ImpostoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImpostoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpostoService);
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
