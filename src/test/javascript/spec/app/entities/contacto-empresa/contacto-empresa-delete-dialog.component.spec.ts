import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ContactoEmpresaDeleteDialogComponent } from 'app/entities/contacto-empresa/contacto-empresa-delete-dialog.component';
import { ContactoEmpresaService } from 'app/entities/contacto-empresa/contacto-empresa.service';

describe('Component Tests', () => {
  describe('ContactoEmpresa Management Delete Component', () => {
    let comp: ContactoEmpresaDeleteDialogComponent;
    let fixture: ComponentFixture<ContactoEmpresaDeleteDialogComponent>;
    let service: ContactoEmpresaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContactoEmpresaDeleteDialogComponent]
      })
        .overrideTemplate(ContactoEmpresaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactoEmpresaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactoEmpresaService);
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
