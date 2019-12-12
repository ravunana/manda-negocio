import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { CompostoProdutoDeleteDialogComponent } from 'app/entities/composto-produto/composto-produto-delete-dialog.component';
import { CompostoProdutoService } from 'app/entities/composto-produto/composto-produto.service';

describe('Component Tests', () => {
  describe('CompostoProduto Management Delete Component', () => {
    let comp: CompostoProdutoDeleteDialogComponent;
    let fixture: ComponentFixture<CompostoProdutoDeleteDialogComponent>;
    let service: CompostoProdutoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [CompostoProdutoDeleteDialogComponent]
      })
        .overrideTemplate(CompostoProdutoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompostoProdutoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompostoProdutoService);
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
