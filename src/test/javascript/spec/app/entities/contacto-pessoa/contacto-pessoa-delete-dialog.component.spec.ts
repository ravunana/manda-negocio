import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ContactoPessoaDeleteDialogComponent } from 'app/entities/contacto-pessoa/contacto-pessoa-delete-dialog.component';
import { ContactoPessoaService } from 'app/entities/contacto-pessoa/contacto-pessoa.service';

describe('Component Tests', () => {
  describe('ContactoPessoa Management Delete Component', () => {
    let comp: ContactoPessoaDeleteDialogComponent;
    let fixture: ComponentFixture<ContactoPessoaDeleteDialogComponent>;
    let service: ContactoPessoaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContactoPessoaDeleteDialogComponent]
      })
        .overrideTemplate(ContactoPessoaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactoPessoaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactoPessoaService);
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
