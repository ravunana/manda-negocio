import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { CoordenadaBancariaDeleteDialogComponent } from 'app/entities/coordenada-bancaria/coordenada-bancaria-delete-dialog.component';
import { CoordenadaBancariaService } from 'app/entities/coordenada-bancaria/coordenada-bancaria.service';

describe('Component Tests', () => {
  describe('CoordenadaBancaria Management Delete Component', () => {
    let comp: CoordenadaBancariaDeleteDialogComponent;
    let fixture: ComponentFixture<CoordenadaBancariaDeleteDialogComponent>;
    let service: CoordenadaBancariaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [CoordenadaBancariaDeleteDialogComponent]
      })
        .overrideTemplate(CoordenadaBancariaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoordenadaBancariaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoordenadaBancariaService);
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
