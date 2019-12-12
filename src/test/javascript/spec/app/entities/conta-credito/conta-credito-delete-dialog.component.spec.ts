import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ContaCreditoDeleteDialogComponent } from 'app/entities/conta-credito/conta-credito-delete-dialog.component';
import { ContaCreditoService } from 'app/entities/conta-credito/conta-credito.service';

describe('Component Tests', () => {
  describe('ContaCredito Management Delete Component', () => {
    let comp: ContaCreditoDeleteDialogComponent;
    let fixture: ComponentFixture<ContaCreditoDeleteDialogComponent>;
    let service: ContaCreditoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContaCreditoDeleteDialogComponent]
      })
        .overrideTemplate(ContaCreditoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContaCreditoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContaCreditoService);
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
