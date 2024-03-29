import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { GrupoTributacaoImpostoDeleteDialogComponent } from 'app/entities/grupo-tributacao-imposto/grupo-tributacao-imposto-delete-dialog.component';
import { GrupoTributacaoImpostoService } from 'app/entities/grupo-tributacao-imposto/grupo-tributacao-imposto.service';

describe('Component Tests', () => {
  describe('GrupoTributacaoImposto Management Delete Component', () => {
    let comp: GrupoTributacaoImpostoDeleteDialogComponent;
    let fixture: ComponentFixture<GrupoTributacaoImpostoDeleteDialogComponent>;
    let service: GrupoTributacaoImpostoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [GrupoTributacaoImpostoDeleteDialogComponent]
      })
        .overrideTemplate(GrupoTributacaoImpostoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrupoTributacaoImpostoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrupoTributacaoImpostoService);
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
