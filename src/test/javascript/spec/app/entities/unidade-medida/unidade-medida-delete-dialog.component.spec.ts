import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { UnidadeMedidaDeleteDialogComponent } from 'app/entities/unidade-medida/unidade-medida-delete-dialog.component';
import { UnidadeMedidaService } from 'app/entities/unidade-medida/unidade-medida.service';

describe('Component Tests', () => {
  describe('UnidadeMedida Management Delete Component', () => {
    let comp: UnidadeMedidaDeleteDialogComponent;
    let fixture: ComponentFixture<UnidadeMedidaDeleteDialogComponent>;
    let service: UnidadeMedidaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [UnidadeMedidaDeleteDialogComponent]
      })
        .overrideTemplate(UnidadeMedidaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnidadeMedidaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UnidadeMedidaService);
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
