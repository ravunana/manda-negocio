import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { CompostoProdutoUpdateComponent } from 'app/entities/composto-produto/composto-produto-update.component';
import { CompostoProdutoService } from 'app/entities/composto-produto/composto-produto.service';
import { CompostoProduto } from 'app/shared/model/composto-produto.model';

describe('Component Tests', () => {
  describe('CompostoProduto Management Update Component', () => {
    let comp: CompostoProdutoUpdateComponent;
    let fixture: ComponentFixture<CompostoProdutoUpdateComponent>;
    let service: CompostoProdutoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [CompostoProdutoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompostoProdutoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompostoProdutoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompostoProdutoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompostoProduto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompostoProduto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
