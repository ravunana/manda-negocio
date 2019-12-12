import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { LocalArmazenamentoDeleteDialogComponent } from 'app/entities/local-armazenamento/local-armazenamento-delete-dialog.component';
import { LocalArmazenamentoService } from 'app/entities/local-armazenamento/local-armazenamento.service';

describe('Component Tests', () => {
  describe('LocalArmazenamento Management Delete Component', () => {
    let comp: LocalArmazenamentoDeleteDialogComponent;
    let fixture: ComponentFixture<LocalArmazenamentoDeleteDialogComponent>;
    let service: LocalArmazenamentoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LocalArmazenamentoDeleteDialogComponent]
      })
        .overrideTemplate(LocalArmazenamentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalArmazenamentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalArmazenamentoService);
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
