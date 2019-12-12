import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { MoradaPessoaDeleteDialogComponent } from 'app/entities/morada-pessoa/morada-pessoa-delete-dialog.component';
import { MoradaPessoaService } from 'app/entities/morada-pessoa/morada-pessoa.service';

describe('Component Tests', () => {
  describe('MoradaPessoa Management Delete Component', () => {
    let comp: MoradaPessoaDeleteDialogComponent;
    let fixture: ComponentFixture<MoradaPessoaDeleteDialogComponent>;
    let service: MoradaPessoaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [MoradaPessoaDeleteDialogComponent]
      })
        .overrideTemplate(MoradaPessoaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MoradaPessoaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MoradaPessoaService);
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
