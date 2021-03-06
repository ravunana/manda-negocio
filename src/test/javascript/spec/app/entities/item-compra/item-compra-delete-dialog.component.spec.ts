import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ItemCompraDeleteDialogComponent } from 'app/entities/item-compra/item-compra-delete-dialog.component';
import { ItemCompraService } from 'app/entities/item-compra/item-compra.service';

describe('Component Tests', () => {
  describe('ItemCompra Management Delete Component', () => {
    let comp: ItemCompraDeleteDialogComponent;
    let fixture: ComponentFixture<ItemCompraDeleteDialogComponent>;
    let service: ItemCompraService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ItemCompraDeleteDialogComponent]
      })
        .overrideTemplate(ItemCompraDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemCompraDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemCompraService);
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
