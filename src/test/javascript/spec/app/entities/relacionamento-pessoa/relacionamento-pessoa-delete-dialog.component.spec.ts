import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MandaTestModule } from '../../../test.module';
import { RelacionamentoPessoaDeleteDialogComponent } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa-delete-dialog.component';
import { RelacionamentoPessoaService } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa.service';

describe('Component Tests', () => {
  describe('RelacionamentoPessoa Management Delete Component', () => {
    let comp: RelacionamentoPessoaDeleteDialogComponent;
    let fixture: ComponentFixture<RelacionamentoPessoaDeleteDialogComponent>;
    let service: RelacionamentoPessoaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RelacionamentoPessoaDeleteDialogComponent]
      })
        .overrideTemplate(RelacionamentoPessoaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RelacionamentoPessoaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelacionamentoPessoaService);
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
