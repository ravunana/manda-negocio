import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { RetencaoFonteDeleteDialogComponent } from 'app/entities/retencao-fonte/retencao-fonte-delete-dialog.component';
import { RetencaoFonteService } from 'app/entities/retencao-fonte/retencao-fonte.service';

describe('Component Tests', () => {
  describe('RetencaoFonte Management Delete Component', () => {
    let comp: RetencaoFonteDeleteDialogComponent;
    let fixture: ComponentFixture<RetencaoFonteDeleteDialogComponent>;
    let service: RetencaoFonteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RetencaoFonteDeleteDialogComponent]
      })
        .overrideTemplate(RetencaoFonteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RetencaoFonteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RetencaoFonteService);
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
