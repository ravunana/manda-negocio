import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { ItemVendaDeleteDialogComponent } from 'app/entities/item-venda/item-venda-delete-dialog.component';
import { ItemVendaService } from 'app/entities/item-venda/item-venda.service';

describe('Component Tests', () => {
  describe('ItemVenda Management Delete Component', () => {
    let comp: ItemVendaDeleteDialogComponent;
    let fixture: ComponentFixture<ItemVendaDeleteDialogComponent>;
    let service: ItemVendaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ItemVendaDeleteDialogComponent]
      })
        .overrideTemplate(ItemVendaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemVendaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemVendaService);
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
