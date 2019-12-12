import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { FamiliaProdutoDeleteDialogComponent } from 'app/entities/familia-produto/familia-produto-delete-dialog.component';
import { FamiliaProdutoService } from 'app/entities/familia-produto/familia-produto.service';

describe('Component Tests', () => {
  describe('FamiliaProduto Management Delete Component', () => {
    let comp: FamiliaProdutoDeleteDialogComponent;
    let fixture: ComponentFixture<FamiliaProdutoDeleteDialogComponent>;
    let service: FamiliaProdutoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [FamiliaProdutoDeleteDialogComponent]
      })
        .overrideTemplate(FamiliaProdutoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FamiliaProdutoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamiliaProdutoService);
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
