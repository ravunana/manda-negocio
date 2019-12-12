import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { LookupItemDeleteDialogComponent } from 'app/entities/lookup-item/lookup-item-delete-dialog.component';
import { LookupItemService } from 'app/entities/lookup-item/lookup-item.service';

describe('Component Tests', () => {
  describe('LookupItem Management Delete Component', () => {
    let comp: LookupItemDeleteDialogComponent;
    let fixture: ComponentFixture<LookupItemDeleteDialogComponent>;
    let service: LookupItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LookupItemDeleteDialogComponent]
      })
        .overrideTemplate(LookupItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LookupItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LookupItemService);
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
