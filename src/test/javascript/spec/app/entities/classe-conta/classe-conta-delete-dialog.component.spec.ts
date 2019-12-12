import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ClasseContaDeleteDialogComponent } from 'app/entities/classe-conta/classe-conta-delete-dialog.component';
import { ClasseContaService } from 'app/entities/classe-conta/classe-conta.service';

describe('Component Tests', () => {
  describe('ClasseConta Management Delete Component', () => {
    let comp: ClasseContaDeleteDialogComponent;
    let fixture: ComponentFixture<ClasseContaDeleteDialogComponent>;
    let service: ClasseContaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ClasseContaDeleteDialogComponent]
      })
        .overrideTemplate(ClasseContaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClasseContaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClasseContaService);
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
