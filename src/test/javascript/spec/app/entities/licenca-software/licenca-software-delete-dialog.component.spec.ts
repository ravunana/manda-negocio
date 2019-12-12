import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { LicencaSoftwareDeleteDialogComponent } from 'app/entities/licenca-software/licenca-software-delete-dialog.component';
import { LicencaSoftwareService } from 'app/entities/licenca-software/licenca-software.service';

describe('Component Tests', () => {
  describe('LicencaSoftware Management Delete Component', () => {
    let comp: LicencaSoftwareDeleteDialogComponent;
    let fixture: ComponentFixture<LicencaSoftwareDeleteDialogComponent>;
    let service: LicencaSoftwareService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LicencaSoftwareDeleteDialogComponent]
      })
        .overrideTemplate(LicencaSoftwareDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LicencaSoftwareDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LicencaSoftwareService);
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
