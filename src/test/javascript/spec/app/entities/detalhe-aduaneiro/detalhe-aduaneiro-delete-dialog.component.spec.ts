import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { DetalheAduaneiroDeleteDialogComponent } from 'app/entities/detalhe-aduaneiro/detalhe-aduaneiro-delete-dialog.component';
import { DetalheAduaneiroService } from 'app/entities/detalhe-aduaneiro/detalhe-aduaneiro.service';

describe('Component Tests', () => {
  describe('DetalheAduaneiro Management Delete Component', () => {
    let comp: DetalheAduaneiroDeleteDialogComponent;
    let fixture: ComponentFixture<DetalheAduaneiroDeleteDialogComponent>;
    let service: DetalheAduaneiroService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DetalheAduaneiroDeleteDialogComponent]
      })
        .overrideTemplate(DetalheAduaneiroDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalheAduaneiroDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalheAduaneiroService);
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
